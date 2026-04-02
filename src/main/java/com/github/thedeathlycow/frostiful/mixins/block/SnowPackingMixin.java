package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.block.PackedSnowBlock;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
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
    private void smushSnowWhenSteppedOnByHeavyEntity(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler, boolean bl, CallbackInfo ci) {
        boolean maySmushSnow = state.getBlock() == Blocks.SNOW
                && entity.is(FEntityTypeTags.HEAVY_ENTITY_TYPES)
                && !world.isClientSide()
                && FrostifulConfigYACL.entitySettings().enableHeavyMobSnowPacking()
                && ((ServerLevel) world).getGameRules().get(GameRules.MOB_GRIEFING)
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
