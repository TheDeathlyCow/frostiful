package com.github.thedeathlycow.frostiful.server.world;


import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.IcicleBlock;
import com.github.thedeathlycow.frostiful.config.group.IcicleConfigGroup;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.WorldChunk;

import java.util.function.Predicate;

public final class IcicleWeatherGenerator {

    public static void tickIciclesForChunk(ServerWorld world, WorldChunk chunk, int randomTickSpeed) {

        final Random random = world.random;

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
        final BlockPos startPos = world.getTopPosition(
                Heightmap.Type.WORLD_SURFACE,
                world.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15)
        );

        if (world.getBiome(startPos).value().doesNotSnow(startPos)) {
            return;
        }

        final BlockState downwardIcicle = FBlocks.ICICLE.getDefaultState()
                .with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN);

        Predicate<BlockPos> validCondition = (testPos) -> {
            BlockState at = world.getBlockState(testPos);

            // testing for sky light helps increase the chance that icicles will only ever form outside
            return at.isAir()
                    && world.getLightLevel(LightType.SKY, testPos) >= icicleConfig.getMinSkylightLevelToForm()
                    && downwardIcicle.canPlaceAt(world, testPos);
        };

        BlockPos.Mutable placePos = startPos.mutableCopy();
        for (int i = 0; i < 5; i++) {

            if (validCondition.test(placePos)) {
                // only place if can place and light is not blocking it
                if (world.getLightLevel(LightType.BLOCK, placePos) < icicleConfig.getMaxLightLevelToForm()) {
                    world.setBlockState(placePos, downwardIcicle, Block.NOTIFY_ALL);
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
