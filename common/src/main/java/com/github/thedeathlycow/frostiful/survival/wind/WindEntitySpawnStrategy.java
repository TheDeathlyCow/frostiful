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

package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.entity.FreezingWindEntity;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class WindEntitySpawnStrategy implements WindSpawnStrategy {

    @Override
    public boolean spawn(Level world, BlockPos spawnPos, boolean isInAir) {
        WindEntity wind = new FreezingWindEntity(FEntityTypes.FREEZING_WIND, world);

        if (isInAir) {
            wind.setLifeTicks(wind.getLifeTicks() * 3);
        }
        wind.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());

        return world.addFreshEntity(wind);
    }
}
