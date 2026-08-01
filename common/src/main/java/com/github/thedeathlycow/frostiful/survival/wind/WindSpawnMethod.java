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
