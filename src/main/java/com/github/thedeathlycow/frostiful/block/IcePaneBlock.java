package com.github.thedeathlycow.frostiful.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;

@SuppressWarnings("deprecation")
public class IcePaneBlock extends PaneBlock {

    public IcePaneBlock(Settings settings) {
        super(settings);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos)) {
            world.removeBlock(pos, false);
        }
    }
}
