package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FrozenTorchBlock extends TorchBlock {
    public FrozenTorchBlock(Properties settings) {
        super(ParticleTypes.SNOWFLAKE, settings);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        // frozen torches have no flame
    }

    @Nullable
    public static BlockState freezeTorch(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof BaseTorchBlock && !state.is(FBlockTags.FROZEN_TORCHES)) {

            // Some wall torches (like redstone wall torch) don't extend WallTorchBlock, and so the only way to determine
            // if they are a wall torch is to check if they don't have the wall post override tag. It's not nice, but it's
            // the only way to generally determine if a block is a wall torch.

            boolean isWallTorch = block instanceof WallTorchBlock
                    || !state.is(BlockTags.WALL_POST_OVERRIDE);

            if (isWallTorch) {
                return FBlocks.FROZEN_WALL_TORCH.withPropertiesOf(state);
            } else {
                return FBlocks.FROZEN_TORCH.withPropertiesOf(state);
            }
        } else {
            return null;
        }
    }
}
