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

package com.github.thedeathlycow.frostiful.server.world;

import com.github.thedeathlycow.frostiful.entity.Chillager;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PatrolSpawner;

public class ChillagerPatrolSpawner {


    /**
     * Essentially a rewrite of {@link PatrolSpawner#spawnPatrolMember(ServerLevel, BlockPos, RandomSource, boolean)}, except
     * that it spawns a {@link Chillager} instead of a Pillager. Called via mixin as method is private.
     *
     * @param world world to spawn chillager in
     * @param pos position to spawn chillager at
     * @param random random instance
     * @param captain whether the chillager should be a captain
     * @return Returns true if the chillager was spawned
     */
    public static boolean spawnChillagerPatrol(ServerLevel world, BlockPos pos, RandomSource random, boolean captain) {
        BlockState state = world.getBlockState(pos);

        if (!NaturalSpawner.isValidEmptySpawnBlock(world, pos, state, state.getFluidState(), FEntityTypes.CHILLAGER)) {
            return false;
        } else if (!PatrollingMonster.checkPatrollingMonsterSpawnRules(FEntityTypes.CHILLAGER, world, EntitySpawnReason.PATROL, pos, random)) {
            return false;
        } else {
            PatrollingMonster patroller = FEntityTypes.CHILLAGER.create(world, EntitySpawnReason.PATROL);
            if (patroller != null) {
                if (captain) {
                    patroller.setPatrolLeader(true);
                    patroller.findPatrolTarget();
                }

                patroller.setPos(pos.getX(), pos.getY(), pos.getZ());
                patroller.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), EntitySpawnReason.PATROL, null);

                world.addFreshEntityWithPassengers(patroller);
                return true;
            } else {
                return false;
            }
        }
    }
}
