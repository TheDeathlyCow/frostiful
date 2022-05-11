package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.AttributeConfig;
import com.github.thedeathlycow.frostiful.config.FreezingConfig;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import com.github.thedeathlycow.simple.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Redirect(
            method = "canFreeze",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isIn(Lnet/minecraft/tag/TagKey;)Z"
            )
    )
    private boolean ignoreFreezeImmuneWearables(ItemStack instance, TagKey<Item> tag) {
        return false;
    }

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

        Config config = FreezingConfig.CONFIG;

        amount = instance.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ?
                config.get(FreezingConfig.FREEZE_EXTRA_DAMAGE_AMOUNT) :
                config.get(FreezingConfig.FREEZE_DAMAGE_AMOUNT);

        return instance.damage(source, amount);
    }

    @Inject(
            method = "canFreeze",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void creativePlayersCannotFreeze(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof PlayerEntity player) {
            if (player.isCreative()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Redirect(
            method = "addPowderSnowSlowIfNeeded",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isAir()Z"
            )
    )
    private boolean addPowderSnowSlowInAir(BlockState instance) {
        return false;
    }

    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V"
            )
    )
    private void modPowderSnowFreezing(LivingEntity instance, int i) {

        if (!(instance.inPowderSnow && instance.canFreeze())) {
            // being out of powder snow should not thaw
            return;
        }

        if (instance instanceof PlayerEntity) {
            FrostHelper.addFrost(instance, PassiveFreezingHelper.getPowderSnowFreezing(instance));
        } else {
            FrostHelper.setFrost(instance, i);
        }
    }

    @Inject(
            method = "createLivingAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        Config config = AttributeConfig.CONFIG;
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(FrostifulEntityAttributes.FROST_RESISTANCE, config.get(AttributeConfig.BASE_ENTITY_FROST_RESISTANCE));
        attributeBuilder.add(FrostifulEntityAttributes.MAX_FROST, config.get(AttributeConfig.ENTITY_MAX_FROST));
        cir.setReturnValue(attributeBuilder);
    }

}
