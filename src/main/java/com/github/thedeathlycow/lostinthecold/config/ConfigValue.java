package com.github.thedeathlycow.lostinthecold.config;

public class ConfigValue<T> {

    public ConfigValue(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    private final T value;

}
