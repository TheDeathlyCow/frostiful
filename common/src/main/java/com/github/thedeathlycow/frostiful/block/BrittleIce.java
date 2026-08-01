package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.registry.FBlockProperties;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class BrittleIce {

    private static final int MIN_CRACK_DELAY = 5;

    private static final int MAX_CRACK_DELAY = 10;

    public static final int MAX_CRACKING = FBlockProperties.MAX_CRACKING;

    public static boolean canCrackIce(Entity entity) {
        return !entity.is(FEntityTypeTags.DOES_NOT_BREAK_BRITTLE_ICE);
    }

    public static void crack(Block block, BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int nextCrackingLevel = state.getValue(FBlockProperties.CRACKING) + 1;
        if (nextCrackingLevel <= MAX_CRACKING) {
            world.setBlockAndUpdate(pos, state.setValue(FBlockProperties.CRACKING, nextCrackingLevel));
            world.playSound(null, pos, FSoundEvents.BLOCK_BRITTLE_ICE_CRACK, SoundSource.BLOCKS);
            world.scheduleTick(pos, block, getCrackDelay(random));
        } else {
            world.destroyBlock(pos, false, null);
            if (state.getValue(FBlockProperties.FROZEN)) {
                world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            }
        }
    }

    public static int getCrackDelay(RandomSource random) {
        return Mth.nextInt(random, MIN_CRACK_DELAY, MAX_CRACK_DELAY);
    }

    private BrittleIce() {

    }
}