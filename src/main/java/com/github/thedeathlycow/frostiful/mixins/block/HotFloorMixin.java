package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.blocks.FBlockTags;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class HotFloorMixin {

    @Inject(
            method = "onSteppedOn",
            at = @At("HEAD")
    )
    private void applyHeatFromBurnDamage(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {

        if (world.isClient || !state.isIn(FBlockTags.HOT_FLOOR) || !(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        // dont apply heat if warm
        if (livingEntity.thermoo$isWarm() || livingEntity.isSpectator() || livingEntity.isDead()) {
            return;
        }

        int heat = Frostiful.getConfig().freezingConfig.getHeatFromHotFloor();
        livingEntity.thermoo$addTemperature(heat, HeatingModes.ACTIVE);
    }

}
