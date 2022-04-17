package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class ConfigKey<T extends Comparable<T>> {

    public ConfigKey(@NotNull String name, @NotNull T defaultValue, @NotNull Class<T> type) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public T getDefaultValue() {
        return defaultValue;
    }

    @NotNull
    public Class<T> getType() {
        return type;
    }

    public T deserialize(JsonElement jsonElement) {
        return gson.fromJson(jsonElement, getType());
    }

    public abstract boolean isValid(T value);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigKey<?> configKey = (ConfigKey<?>) o;
        return name.equals(configKey.name) && defaultValue.equals(configKey.defaultValue) && type.equals(configKey.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, defaultValue, type);
    }

    @NotNull
    private final String name;

    @NotNull
    private final T defaultValue;

    @NotNull
    private final Class<T> type;

    private final Gson gson = new GsonBuilder()
            .create();

}
