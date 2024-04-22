package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.tag.FBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FrozenTorchBlock extends TorchBlock {
    public FrozenTorchBlock(Settings settings) {
        super(ParticleTypes.SNOWFLAKE, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // frozen torches have no flame
    }

    @Nullable
    public static BlockState freezeTorch(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof TorchBlock && !state.isIn(FBlockTags.FROZEN_TORCHES)) {

            // Some wall torches (like redstone wall torch) don't extend WallTorchBlock, and so the only way to determine
            // if they are a wall torch is to check if they don't have the wall post override tag. It's not nice, but it's
            // the only way to generally determine if a block is a wall torch.

            boolean isWallTorch = block instanceof WallTorchBlock
                    || !state.isIn(BlockTags.WALL_POST_OVERRIDE);

            if (isWallTorch) {
                return FBlocks.FROZEN_WALL_TORCH.getStateWithProperties(state);
            } else {
                return FBlocks.FROZEN_TORCH.getStateWithProperties(state);
            }
        } else {
            return null;
        }
    }
}
