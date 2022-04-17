package com.github.thedeathlycow.lostinthecold.config;

import org.jetbrains.annotations.NotNull;

public class BoundedKey<T extends Comparable<T>> extends ConfigKey<T> {

    public BoundedKey(@NotNull String name, @NotNull T defaultValue, Class<T> type, T min, T max) {
        super(name, defaultValue, type);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isValid(T value) {
        return min.compareTo(value) <= 0 && max.compareTo(value) >= 0;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    private final T min;
    private final T max;
}
