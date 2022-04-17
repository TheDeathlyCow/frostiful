package com.github.thedeathlycow.lostinthecold.config;

import org.jetbrains.annotations.NotNull;

public class BooleanKey extends ConfigKey<Boolean> {

    public BooleanKey(@NotNull String name, @NotNull Boolean defaultValue) {
        super(name, defaultValue, Boolean.class);
    }

    @Override
    public boolean isValid(Boolean value) {
        return true;
    }
}
