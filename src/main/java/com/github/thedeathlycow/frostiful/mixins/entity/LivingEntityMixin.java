package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.entity.FrostDataTracker;
import com.github.thedeathlycow.frostiful.entity.effect.FrostifulStatusEffects;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    private static final UUID FROSTIFUL$FROST_SLOW_ID = UUID.fromString("dbfeeec1-aeae-4ce5-9baa-c8491ab36571");

    @Shadow protected abstract void addPowderSnowSlowIfNeeded();

    @Shadow @Nullable public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;tick()V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void tickFrost(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        World world = livingEntity.getWorld();
        if (world.isClient()) {
            return;
        }
        this.doHeating(livingEntity);
        FrostHelper.applyEffects(livingEntity);
    }

    private void doHeating(LivingEntity livingEntity) {
        int warmth = PassiveFreezingHelper.getWarmth(livingEntity);
        FrostHelper.removeLivingFrost(livingEntity, warmth);
    }

    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;removePowderSnowSlow()V",
                    shift = At.Shift.BEFORE
            )
    )
    private void tickFrostSlow(CallbackInfo ci) {
        LivingEntity instance = (LivingEntity) (Object) this;
        this.removeFrostSlow(instance);
        this.addFrostSlowIfNeeded(instance);
    }

    private void removeFrostSlow(LivingEntity livingEntity) {
        EntityAttributeInstance movementSpeed = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (movementSpeed != null) {
            if (movementSpeed.getModifier(FROSTIFUL$FROST_SLOW_ID) != null) {
                movementSpeed.removeModifier(FROSTIFUL$FROST_SLOW_ID);
            }
        }
    }

    private void addFrostSlowIfNeeded(LivingEntity livingEntity) {
        FrostDataTracker tracker = (FrostDataTracker) livingEntity;
        final int currentFrost = tracker.frostiful$getFrost();
        if (currentFrost > 0) {
            EntityAttributeInstance movementSpeed = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (movementSpeed == null) {
                return;
            }

            float slowAmountAdder = -0.05F * tracker.frostiful$getFrostProgress();
            movementSpeed.addTemporaryModifier(new EntityAttributeModifier(FROSTIFUL$FROST_SLOW_ID, "Frost slow", slowAmountAdder, EntityAttributeModifier.Operation.ADDITION));
        }
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
//            method = "tickMovement",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
//            ),
//            slice = @Slice(
//                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addPowderSnowSlowIfNeeded()V"),
//                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z")
//            )
//    )
//    private boolean applyFrostDamageAccordingToConfig(LivingEntity instance, DamageSource source, float amount) {
//
//        if (!source.equals(DamageSource.FREEZE)) {
//            return false;
//        }
//
//        amount = instance.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ?
//                FreezingConfigGroup.FREEZE_EXTRA_DAMAGE_AMOUNT.getValue() :
//                FreezingConfigGroup.FREEZE_DAMAGE_AMOUNT.getValue();
//
//        return instance.damage(source, amount);
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
