package com.github.thedeathlycow.frostiful.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FrozenWallTorchBlock extends WallTorchBlock {
    public FrozenWallTorchBlock(Settings settings) {
        super(settings, ParticleTypes.SNOWFLAKE);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // frozen torches have no flame
    }
}
