/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

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