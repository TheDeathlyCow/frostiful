package com.github.thedeathlycow.frostiful.server.world;


import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.IcicleBlock;
import com.github.thedeathlycow.frostiful.config.group.IcicleConfigGroup;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Predicate;

public final class IcicleWeatherGenerator {

    public static void tickIciclesForChunk(ServerLevel world, LevelChunk chunk, int randomTickSpeed) {

        final RandomSource random = world.random;

        IcicleConfigGroup icicleConfig = Frostiful.getConfig().icicleConfig;
        if (!icicleConfig.iciclesFormInWeather()) {
            return;
        }

        if (!world.isRaining()) {
            return;
        }

        // slow down icicles appearing in world
        if (random.nextInt(16 * 5) != 0) {
            return;
        }

        final ChunkPos chunkPos = chunk.getPos();
        final BlockPos startPos = world.getHeightmapPos(
                Heightmap.Types.WORLD_SURFACE,
                world.getBlockRandomPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ(), 15)
        );

        if (world.getBiome(startPos).value().warmEnoughToRain(startPos)) {
            return;
        }

        final BlockState downwardIcicle = FBlocks.ICICLE.defaultBlockState()
                .setValue(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN);

        Predicate<BlockPos> validCondition = (testPos) -> {
            BlockState at = world.getBlockState(testPos);

            // testing for sky light helps increase the chance that icicles will only ever form outside
            return at.isAir()
                    && world.getBrightness(LightLayer.SKY, testPos) >= icicleConfig.getMinSkylightLevelToForm()
                    && downwardIcicle.canSurvive(world, testPos);
        };

        BlockPos.MutableBlockPos placePos = startPos.mutable();
        for (int i = 0; i < 5; i++) {

            if (validCondition.test(placePos)) {
                // only place if can place and light is not blocking it
                if (world.getBrightness(LightLayer.BLOCK, placePos) < icicleConfig.getMaxLightLevelToForm()) {
                    world.setBlock(placePos, downwardIcicle, Block.UPDATE_ALL);
                }

                // if can place but there is light blocking - stop looking
                return;
            }
            placePos.move(Direction.DOWN);
        }

    }

    private IcicleWeatherGenerator() {

    }

}
