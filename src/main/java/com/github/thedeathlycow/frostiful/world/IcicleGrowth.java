package com.github.thedeathlycow.frostiful.world;

import com.github.thedeathlycow.frostiful.block.FBlocks;
import com.github.thedeathlycow.frostiful.block.IcicleBlock;
import com.github.thedeathlycow.frostiful.config.group.IcicleConfigGroup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.function.Predicate;

public class IcicleGrowth {


    public static void growIcicle(WorldChunk chunk, ServerWorld instance, IcicleConfigGroup icicleConfig) {
        final ChunkPos chunkPos = chunk.getPos();
        final BlockPos startPos = instance.getTopPosition(
                Heightmap.Type.WORLD_SURFACE,
                instance.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15)
        );

        if (instance.getBiome(startPos).value().doesNotSnow(startPos)) {
            return;
        }

        final BlockState downwardIcicle = FBlocks.ICICLE.getDefaultState()
                .with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN);

        World world = chunk.getWorld();
        Predicate<BlockPos> validCondition = (testPos) -> {
            BlockState at = instance.getBlockState(testPos);

            // testing for sky light helps increase the chance that icicles will only ever form outside
            return at.isAir()
                    && world.getLightLevel(LightType.SKY, testPos) >= icicleConfig.getMinSkylightLevelToForm()
                    && downwardIcicle.canPlaceAt(instance, testPos);
        };

        BlockPos.Mutable placePos = startPos.mutableCopy();
        for (int i = 0; i < 5; i++) {

            if (validCondition.test(placePos)) {
                // only place if can place and light is not blocking it
                if (world.getLightLevel(LightType.BLOCK, placePos) < icicleConfig.getMaxLightLevelToForm()) {
                    instance.setBlockState(placePos, downwardIcicle, Block.NOTIFY_ALL);
                }

                // if can place but there is light blocking - stop looking
                return;
            }
            placePos.move(Direction.DOWN);
        }
    }


}
