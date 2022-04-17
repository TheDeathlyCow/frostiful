package com.github.thedeathlycow.lostinthecold.mixins.server;

import com.github.thedeathlycow.lostinthecold.tag.blocks.LostInTheColdBlockTags;
import com.github.thedeathlycow.lostinthecold.world.ModGameRules;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    /**
     * @param instance
     * @param bound
     * @return
     */
    @Redirect(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I",
                    ordinal = 1
            )
    )
    private int noRandomChanceForSnow(Random instance, int bound) {
        //LostInTheCold.LOGGER.warn("If not in dev environment - delete noRandomChanceForSnow");
        return 0;
    }

    @Redirect(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;canSetSnow(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z")
            )
    )
    private boolean doSnowBuildup(ServerWorld instance, BlockPos blockPos, BlockState blockState) {
        BlockState current = instance.getBlockState(blockPos);
        if (!instance.getGameRules().getBoolean(ModGameRules.DO_SNOW_ACCUMULATION)) {
            if (current.isAir()) {
                return instance.setBlockState(blockPos, blockState);
            } else {
                return false;
            }
        }
        BlockState toSet = blockState;
        if (current.isOf(Blocks.SNOW) && instance.getBlockState(blockPos.down()).isSolidBlock(instance, blockPos)) {
            int layers = getLayersForPlacement(instance, current, blockPos);
            if (layers == SnowBlock.MAX_LAYERS) {
                toSet = Blocks.POWDER_SNOW.getDefaultState();
            } else {
                toSet = current.with(SnowBlock.LAYERS, layers);
            }
        }
        return instance.setBlockState(blockPos, toSet);
    }

    private int getSnowLayers(ServerWorld world, BlockPos pos, Direction direction) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(Blocks.SNOW)) {
            return state.get(SnowBlock.LAYERS);
        } else if (state.isIn(LostInTheColdBlockTags.SNOW_ACCUMULATE_NEXT_TO)) {
            return SnowBlock.MAX_LAYERS;
        } else if (state.isSideSolid(world, pos, direction.getOpposite(), SideShapeType.FULL)) {
            return SnowBlock.MAX_LAYERS;
        } else {
            return 0;
        }
    }

    private int getLowestNeighbouringLayer(ServerWorld instance, BlockPos blockPos) {

        List<Integer> neighbourLayers = List.of(
                getSnowLayers(instance, blockPos.north(), Direction.NORTH),
                getSnowLayers(instance, blockPos.south(), Direction.SOUTH),
                getSnowLayers(instance, blockPos.east(), Direction.EAST),
                getSnowLayers(instance, blockPos.west(), Direction.WEST)
        );

        return Collections.min(neighbourLayers);
    }

    private int getLayersForPlacement(ServerWorld instance, BlockState current, BlockPos blockPos) {

        int lowestNeighbour = getLowestNeighbouringLayer(instance, blockPos);
        int currentLayers = current.get(SnowBlock.LAYERS);
        if (currentLayers - lowestNeighbour < 2) {
            return Math.min(SnowBlock.MAX_LAYERS, currentLayers + 1);
        }
        return currentLayers;
    }

}
