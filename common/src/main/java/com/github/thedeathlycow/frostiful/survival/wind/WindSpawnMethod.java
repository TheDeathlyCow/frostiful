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

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;

public enum WindSpawnMethod implements StringRepresentable {
    NONE(null, "none"),
    ENTITY(new WindEntitySpawnStrategy(), "entity"),
    POINT(new PointWindSpawnStrategy(), "point");

    @Nullable
    private final WindSpawnStrategy strategy;
    private final String name;

    WindSpawnMethod(WindSpawnStrategy strategy, String name) {
        this.strategy = strategy;
        this.name = name;
    }

    @Nullable
    public WindSpawnStrategy getStrategy() {
        return strategy;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
