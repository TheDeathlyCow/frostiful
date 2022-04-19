package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.datapack.config.config.Config;
import com.github.thedeathlycow.lostinthecold.attributes.LostInTheColdEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.ConfigKeys;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.util.survival.FrostHelper;
import com.github.thedeathlycow.lostinthecold.util.survival.PassiveFreezingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.EntityTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected abstract void initDataTracker();

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

        Config config = LostInTheCold.getConfig();

        amount = instance.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ?
                config.get(ConfigKeys.FREEZE_EXTRA_DAMAGE_AMOUNT) :
                config.get(ConfigKeys.FREEZE_DAMAGE_AMOUNT);

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

        if (i < instance.getFrozenTicks() || i == 0) {
            // being out of powder snow should not thaw
            return;
        }

        if (instance instanceof PlayerEntity) {
            int currentTicks = instance.getFrozenTicks();
            if (currentTicks < instance.getMinFreezeDamageTicks()) {
                FrostHelper.addFrozenTicks(instance, PassiveFreezingHelper.getPowderSnowFreezing(instance));
            } // else: stop freezing when at or exceeding freeze damage threshold - but do not clamp down
        } else {
            FrostHelper.setFrozenTicks(instance, i);
        }
    }

    @Inject(
            method = "createLivingAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        Config config = LostInTheCold.getConfig();
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(LostInTheColdEntityAttributes.FROST_RESISTANCE, config.get(ConfigKeys.BASE_ENTITY_FROST_RESISTANCE));
        attributeBuilder.add(LostInTheColdEntityAttributes.MAX_FROST, config.get(ConfigKeys.ENTITY_MAX_FROST));
        cir.setReturnValue(attributeBuilder);
    }

}
