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

package com.github.thedeathlycow.frostiful.config;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;

public enum DifficultySetting implements StringRepresentable {
    AUTOMATIC("automatic"),
    PEACEFUL("peaceful"),
    EASY("easy"),
    NORMAL("normal"),
    HARD("hard");

    private final String name;

    DifficultySetting(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public DifficultySetting getDifficultySetting(Level level) {
        return this == AUTOMATIC ? getAdaptedDifficulty(level.getDifficulty()) : this;
    }

    private static DifficultySetting getAdaptedDifficulty(Difficulty difficulty) {
        return switch (difficulty) {
            case PEACEFUL -> PEACEFUL;
            case EASY -> EASY;
            case NORMAL -> NORMAL;
            case HARD -> HARD;
        };
    }
}