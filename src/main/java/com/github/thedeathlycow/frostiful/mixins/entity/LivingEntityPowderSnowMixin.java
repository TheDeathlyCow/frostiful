package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityPowderSnowMixin extends Entity {

    public LivingEntityPowderSnowMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyArg(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V",
                    ordinal = 0
            )
    )
    private int tickFreezingInPowderSnow(int par1) {
        FrostifulConfig config = Frostiful.getConfig();
        return this.getFrozenTicks() + config.freezingConfig.getPowderSnowFreezeRate();
    }

    @ModifyArg(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V",
                    ordinal = 1
            )
    )
    private int doNotThawNormally(int par1) {
        return this.getFrozenTicks();
    }

    @Inject(
            method = "addPowderSnowSlowIfNeeded",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void blockDefaultPowderSnowSlow(CallbackInfo ci) {
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

}
