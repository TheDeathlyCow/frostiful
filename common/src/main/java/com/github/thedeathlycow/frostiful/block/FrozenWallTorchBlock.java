package com.github.thedeathlycow.frostiful.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FrozenWallTorchBlock extends WallTorchBlock {
    public FrozenWallTorchBlock(Properties settings) {
        super(ParticleTypes.SNOWFLAKE, settings);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        // frozen torches have no flame
    }
}
