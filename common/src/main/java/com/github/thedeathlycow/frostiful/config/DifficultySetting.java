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