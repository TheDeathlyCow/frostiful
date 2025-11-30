package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.PackedSnowBlock;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public abstract class SnowPackingMixin {

    @Inject(
            method = "entityInside",
            at = @At("TAIL")
    )
    private void smushSnowWhenSteppedOnByHeavyEntity(BlockState state, Level world, BlockPos pos, Entity entity, CallbackInfo ci) {

        boolean maySmushSnow = state.getBlock() == Blocks.SNOW
                && entity.getType().is(FEntityTypeTags.HEAVY_ENTITY_TYPES)
                && !world.isClientSide
                && Frostiful.getConfig().freezingConfig.doSnowPacking()
                && world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
                && isEntityWalkingOn(pos, entity);

        if (maySmushSnow) {

            int layers = state.getValue(SnowLayerBlock.LAYERS);

            BlockState packedSnow = FBlocks.PACKED_SNOW.defaultBlockState()
                    .setValue(PackedSnowBlock.LAYERS, layers);

            world.setBlockAndUpdate(pos, packedSnow);
            entity.playSound(SoundEvents.SNOW_PLACE, 1.0f, 1.0f);
        }
    }

    private static boolean isEntityWalkingOn(BlockPos pos, Entity entity) {
        return entity.onGround() && entity.blockPosition().equals(pos);
    }

}
