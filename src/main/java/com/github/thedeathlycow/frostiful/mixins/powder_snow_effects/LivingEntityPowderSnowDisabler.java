package com.github.thedeathlycow.frostiful.mixins.powder_snow_effects;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityPowderSnowDisabler implements TemperatureAware {

    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V",
                    ordinal = 0
            )
    )
    private void tickPowderSnowFreezing(CallbackInfo ci) {
        // applies own config version of powder snow temperature change
        int freezing = Frostiful.getConfig().freezingConfig.getPowderSnowFreezeRate();
        this.thermoo$addTemperature(-freezing, HeatingModes.ACTIVE);
    }

    @ModifyArg(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V",
                    ordinal = 0
            )
    )
    private int disableTicksFreezingIncreaseInPowderSnow(int par1) {
        return 0;
    }

    @ModifyArg(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V",
                    ordinal = 1
            )
    )
    private int disablePowderSnowThawing(int par1) {
        return 0;
    }

    @Inject(
            method = "addPowderSnowSlowIfNeeded",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void blockPowderSnowSlow(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "removePowderSnowSlow",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void dontNeedToRemovePowderSnowSlow(CallbackInfo ci) {
        ci.cancel();
    }


    @ModifyArg(
            method = "tickMovement",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/LivingEntity;addPowderSnowSlowIfNeeded()V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
                    ordinal = 0
            ),
            index = 1
    )
    private float blockPowderSnowFreezeDamage(float amount) {
        return 0f;
    }
}
