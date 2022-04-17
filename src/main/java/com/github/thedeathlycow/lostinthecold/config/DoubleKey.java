package com.github.thedeathlycow.lostinthecold.config;

import org.jetbrains.annotations.NotNull;

public class DoubleKey extends BoundedKey<Double> {

    public DoubleKey(@NotNull String name, @NotNull Double defaultValue) {
        this(name, defaultValue, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public DoubleKey(@NotNull String name, @NotNull Double defaultValue, Double min) {
        this(name, defaultValue, min, Double.MAX_VALUE);
    }

    public DoubleKey(@NotNull String name, @NotNull Double defaultValue, Double min, Double max) {
        super(name, defaultValue, Double.class, min, max);
    }

}
