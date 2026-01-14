package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

import java.util.function.Consumer;

public class IcicleHelper {

    public static boolean canReplace(BlockState state) {
        return (!state.is(Blocks.ICE) && state.is(FBlockTags.ICICLE_GROWABLE)) || state.is(FBlockTags.ICICLE_REPLACEABLE_BLOCKS);
    }

    public static boolean canGenerate(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }

    public static boolean generateIceBaseBlock(LevelAccessor world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.is(FBlockTags.ICICLE_REPLACEABLE_BLOCKS)) {
            world.setBlock(pos, Blocks.PACKED_ICE.defaultBlockState(), Block.UPDATE_CLIENTS);
            return true;
        }
        return false;
    }

    public static void generateIcicle(LevelAccessor world, BlockPos pos, Direction direction, int height, boolean merge) {
        if (!IcicleHelper.canReplace(world.getBlockState(pos.relative(direction.getOpposite())))) {
            return;
        }
        BlockPos.MutableBlockPos mutable = pos.mutable();
        placeWithThickness(
                direction, height, merge,
                state -> {
                    state = state.setValue(IcicleBlock.WATERLOGGED, world.isWaterAt(mutable));
                    world.setBlock(mutable, state, Block.UPDATE_CLIENTS);
                    mutable.move(direction);
                }
        );
    }

    private static void placeWithThickness(
            Direction direction,
            int height,
            boolean merge,
            Consumer<BlockState> placeCallback
    ) {

        // sets each part of the icicle in order
        // callback moves one block in the direction for each call

        if (height >= 3) {
            placeCallback.accept(getState(direction, DripstoneThickness.BASE));
            for (int i = 0; i < height - 3; ++i) {
                placeCallback.accept(getState(direction, DripstoneThickness.MIDDLE));
            }
        }

        if (height >= 2) {
            placeCallback.accept(getState(direction, DripstoneThickness.FRUSTUM));
        }

        if (height >= 1) {
            placeCallback.accept(getState(direction, merge ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }
    }

    private static BlockState getState(Direction direction, DripstoneThickness thickness) {
        return FBlocks.ICICLE.defaultBlockState()
                .setValue(PointedDripstoneBlock.TIP_DIRECTION, direction)
                .setValue(PointedDripstoneBlock.THICKNESS, thickness);
    }


    private IcicleHelper() {

    }

}
