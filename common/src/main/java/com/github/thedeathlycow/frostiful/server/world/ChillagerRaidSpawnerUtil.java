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

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public final class ChillagerRaidSpawnerUtil {
    public static EntityType<? extends Entity> replaceRaidersInColdBiomes(EntityType<?> base, boolean isBiomeCold) {
        if (!isBiomeCold) {
            return base;
        } else if (base == EntityType.PILLAGER) {
            return FEntityTypes.CHILLAGER;
        } else if (base == EntityType.EVOKER) {
            return FEntityTypes.FROSTOLOGER;
        } else {
            return base;
        }
    }

    private ChillagerRaidSpawnerUtil() {
    }
}