package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.entity.effect.FrostifulStatusEffects;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezingEffects {


    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;tick()V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void doHeating(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        World world = livingEntity.getWorld();

        if (world.isClient()) {
            return;
        }

        int warmth = PassiveFreezingHelper.getWarmth(livingEntity);
        FrostHelper.removeLivingFrost(livingEntity, warmth);
        FrostHelper.applyEffects(livingEntity);
    }

    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiler/Profiler;pop()V",
                    ordinal = 1
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V"
                    )
            )
    )
    private void tickFrozenEffect(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasStatusEffect(FrostifulStatusEffects.FROZEN)) {
            entity.setJumping(false);
            entity.sidewaysSpeed = 0.0F;
            entity.forwardSpeed = 0.0F;
        }
    }

//    @Redirect(
//            method = "canFreeze",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/item/ItemStack;isIn(Lnet/minecraft/tag/TagKey;)Z"
//            )
//    )
//    private boolean ignoreFreezeImmuneWearables(ItemStack instance, TagKey<Item> tag) {
//        return false;
//    }

    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addPowderSnowSlowIfNeeded()V"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z")
            )
    )
    private boolean applyFrostDamageAccordingToConfig(LivingEntity instance, DamageSource source, float amount) {

        if (!source.equals(DamageSource.FREEZE)) {
            return false;
        }

        amount = instance.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ?
                FreezingConfigGroup.FREEZE_EXTRA_DAMAGE_AMOUNT.getValue() :
                FreezingConfigGroup.FREEZE_DAMAGE_AMOUNT.getValue();

        return instance.damage(source, amount);
    }

//    @Redirect(
//            method = "addPowderSnowSlowIfNeeded",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/block/BlockState;isAir()Z"
//            )
//    )
//    private boolean addPowderSnowSlowInAir(BlockState instance) {
//        return false;
//    }

//    @Redirect(
//            method = "tickMovement",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V"
//            )
//    )
//    private void modPowderSnowFreezing(LivingEntity instance, int i) {
//
//        if (!(instance.inPowderSnow && instance.canFreeze())) {
//            // being out of powder snow should not thaw
//            return;
//        }
//
//        FrostHelper.addLivingFrost(instance, PassiveFreezingHelper.getPowderSnowFreezing(instance));
//    }

    @Inject(
            method = "createLivingAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(FrostifulEntityAttributes.FROST_RESISTANCE, AttributeConfigGroup.BaseFrostResistance.BASE.getValue());
        attributeBuilder.add(FrostifulEntityAttributes.MAX_FROST, AttributeConfigGroup.BaseMaxFrost.BASE.getValue());
        cir.setReturnValue(attributeBuilder);
    }

}
