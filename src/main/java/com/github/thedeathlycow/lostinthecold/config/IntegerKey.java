package com.github.thedeathlycow.lostinthecold.config;

import org.jetbrains.annotations.NotNull;

public class IntegerKey extends BoundedKey<Integer> {

    public IntegerKey(@NotNull String name, @NotNull Integer defaultValue) {
        this(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerKey(@NotNull String name, @NotNull Integer defaultValue, Integer min) {
        this(name, defaultValue, min, Integer.MAX_VALUE);
    }

    public IntegerKey(@NotNull String name, @NotNull Integer defaultValue, Integer min, Integer max) {
        super(name, defaultValue, Integer.class, min, max);
    }
}
