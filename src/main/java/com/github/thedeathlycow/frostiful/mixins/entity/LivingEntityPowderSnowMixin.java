package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

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

    @Redirect(
            method = "addPowderSnowSlowIfNeeded",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isAir()Z"
            )
    )
    private boolean ignoreAirTest(BlockState instance) {
        return false;
    }

    @Redirect(
            method = "addPowderSnowSlowIfNeeded()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getLandingBlockState()Lnet/minecraft/block/BlockState;"
            )
    )
    private BlockState ignoreAirTestBlockStateOptimization(LivingEntity instance) {
        return null;
    }

}
