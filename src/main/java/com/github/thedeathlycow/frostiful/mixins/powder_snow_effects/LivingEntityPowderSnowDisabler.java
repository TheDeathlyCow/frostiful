package com.github.thedeathlycow.frostiful.mixins.powder_snow_effects;

import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityPowderSnowDisabler extends Entity implements TemperatureAware {

    public LivingEntityPowderSnowDisabler(EntityType<?> type, Level world) {
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
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;setTicksFrozen(I)V"
            )
    )
    private int disableTicksFreezingIncreaseInPowderSnow(int par1) {
        return this.getTicksFrozen();
    }

    @Inject(
            method = "tryAddFrost",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void blockPowderSnowSlow(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "removeFrost",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void dontNeedToRemovePowderSnowSlow(CallbackInfo ci) {
        ci.cancel();
    }


    @ModifyArg(
            method = "aiStep",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/entity/LivingEntity;tryAddFrost()V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z",
                    ordinal = 0
            ),
            index = 2
    )
    private float blockPowderSnowFreezeDamage(float amount) {
        return 0f;
    }
}
