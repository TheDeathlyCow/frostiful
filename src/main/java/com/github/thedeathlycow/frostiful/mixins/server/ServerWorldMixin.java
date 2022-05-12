package com.github.thedeathlycow.frostiful.mixins.server;

import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.block.Icicle;
import com.github.thedeathlycow.frostiful.config.WeatherConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.blocks.FrostifulBlockTags;
import com.github.thedeathlycow.simple.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

//    @Redirect(
//            method = "tickChunk",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Ljava/util/Random;nextInt(I)I",
//                    ordinal = 1
//            )
//    )
//    private int noRandomChanceForSnow(Random instance, int bound) {
//        //LostInTheCold.LOGGER.warn("If not in dev environment - delete noRandomChanceForSnow");
//        return 0;
//    }
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
    private boolean applySnowBuildup(ServerWorld instance, BlockPos blockPos, BlockState blockState) {
        BlockState current = instance.getBlockState(blockPos);

        doIcicleGrowth(instance, blockPos, instance.random);

        Config config = WeatherConfig.CONFIG;
        int maxLayers = config.get(WeatherConfig.MAX_SNOW_BUILDUP);

        if (maxLayers == 0) {
            return false;
        }

        int layers = getLayersForPlacement(instance, current, blockPos, maxLayers);
        BlockState toSet = Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, layers);

        return instance.setBlockState(blockPos, toSet);
    }

    private void doIcicleGrowth(ServerWorld instance, BlockPos pos, Random random) {


        Predicate<BlockPos> validCondition = (testPos) -> {
            BlockState anchor = instance.getBlockState(testPos.up());
            BlockState at = instance.getBlockState(testPos);
            return at.isAir() && (anchor.isIn(FrostifulBlockTags.ICICLE_GROWABLE) ||
                    anchor.isSideSolid(instance, testPos, Direction.DOWN, SideShapeType.FULL));
        };

        BlockPos.Mutable placePos = pos.mutableCopy();
        for (int i = 0; i < 10; i++) {
            if (validCondition.test(placePos)) {
                BlockState icicle = FrostifulBlocks.ICICLE.getDefaultState()
                        .with(Icicle.VERTICAL_DIRECTION, Direction.DOWN);
                instance.setBlockState(placePos, icicle);
                return;
            }
            placePos.move(Direction.DOWN);
        }
    }


    private int getSnowLayers(BlockState state) {
        if (state.isOf(Blocks.SNOW)) {
            return state.get(SnowBlock.LAYERS);
        } else if (state.isAir()) {
            return 0;
        } else {
            return SnowBlock.MAX_LAYERS;
        }
    }

    private int getLowestNeighbouringLayer(ServerWorld instance, BlockPos blockPos) {

        List<Integer> neighbourLayers = List.of(
                getSnowLayers(instance.getBlockState(blockPos.north())),
                getSnowLayers(instance.getBlockState(blockPos.south())),
                getSnowLayers(instance.getBlockState(blockPos.east())),
                getSnowLayers(instance.getBlockState(blockPos.west()))
        );

        return Collections.min(neighbourLayers);
    }

    private int getLayersForPlacement(ServerWorld instance, BlockState current, BlockPos blockPos, final int maxLayers) {

        int lowestNeighbour = getLowestNeighbouringLayer(instance, blockPos);
        int currentLayers = getSnowLayers(current);
        byte maxStep = WeatherConfig.CONFIG.get(WeatherConfig.MAX_SNOW_BUILDUP_STEP);
        if (currentLayers - lowestNeighbour < maxStep && currentLayers < maxLayers) {
            return Math.min(maxLayers, currentLayers + 1);
        }
        return currentLayers;
    }

}
