package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.tag.FBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.function.Consumer;

public class IcicleHelper {

    public static boolean canReplace(BlockState state) {
        return (!state.isOf(Blocks.ICE) && state.isIn(FBlockTags.ICICLE_GROWABLE)) || state.isIn(FBlockTags.ICICLE_REPLACEABLE_BLOCKS);
    }

    public static boolean canGenerate(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER);
    }

    public static boolean generateIceBaseBlock(WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isIn(FBlockTags.ICICLE_REPLACEABLE_BLOCKS)) {
            world.setBlockState(pos, Blocks.PACKED_ICE.getDefaultState(), Block.NOTIFY_LISTENERS);
            return true;
        }
        return false;
    }

    public static void generateIcicle(WorldAccess world, BlockPos pos, Direction direction, int height, boolean merge) {
        if (!IcicleHelper.canReplace(world.getBlockState(pos.offset(direction.getOpposite())))) {
            return;
        }
        BlockPos.Mutable mutable = pos.mutableCopy();
        placeWithThickness(
                direction, height, merge,
                state -> {
                    state = state.with(IcicleBlock.WATERLOGGED, world.isWater(mutable));
                    world.setBlockState(mutable, state, Block.NOTIFY_LISTENERS);
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
            placeCallback.accept(getState(direction, Thickness.BASE));
            for (int i = 0; i < height - 3; ++i) {
                placeCallback.accept(getState(direction, Thickness.MIDDLE));
            }
        }

        if (height >= 2) {
            placeCallback.accept(getState(direction, Thickness.FRUSTUM));
        }

        if (height >= 1) {
            placeCallback.accept(getState(direction, merge ? Thickness.TIP_MERGE : Thickness.TIP));
        }
    }

    private static BlockState getState(Direction direction, Thickness thickness) {
        return FBlocks.ICICLE.getDefaultState()
                .with(PointedDripstoneBlock.VERTICAL_DIRECTION, direction)
                .with(PointedDripstoneBlock.THICKNESS, thickness);
    }


    private IcicleHelper() {

    }

}
