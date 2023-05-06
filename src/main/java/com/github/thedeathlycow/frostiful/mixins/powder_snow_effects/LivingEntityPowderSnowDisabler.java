package com.github.thedeathlycow.frostiful.mixins.powder_snow_effects;

import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityPowderSnowDisabler extends Entity implements TemperatureAware {

    public LivingEntityPowderSnowDisabler(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "canFreeze",
            at = @At(
                    value = "TAIL"
            ),
            cancellable = true
    )
    private void overrideLeatherArmourFreezeImmunity(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canFreeze());
    }

    @ModifyArg(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V"
            )
    )
    private int disableTicksFreezingIncreaseInPowderSnow(int par1) {
        return this.getFrozenTicks();
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
