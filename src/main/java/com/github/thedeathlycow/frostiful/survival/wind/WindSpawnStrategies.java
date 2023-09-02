package com.github.thedeathlycow.frostiful.survival.wind;

import org.jetbrains.annotations.Nullable;

public enum WindSpawnStrategies {

    NONE(null),
    ENTITY(new WindEntitySpawnStrategy());

    @Nullable
    private final WindSpawnStrategy strategy;

    WindSpawnStrategies(WindSpawnStrategy strategy) {
        this.strategy = strategy;
    }

    @Nullable
    public WindSpawnStrategy getStrategy() {
        return strategy;
    }

}
