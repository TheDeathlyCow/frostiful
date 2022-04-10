package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ConfigKey<T> {

    ConfigKey(@NotNull String name, @Nullable T defaultValue, @NotNull Class<T> type) {
        this.name = name;
        this.defaultValue = new ConfigValue<>(defaultValue);
        this.type = type;
    }

    final public String getName() {
        return name;
    }

    final public ConfigValue<T> getDefaultValue() {
        return defaultValue;
    }

    public T deserialize(JsonElement jsonElement) {
        return GSON.fromJson(jsonElement, this.type);
    }

    final public Class<T> getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigKey<?> configKey = (ConfigKey<?>) o;
        return name.equals(configKey.name) && type.equals(configKey.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return String.format("{%s:%s}", name, type.getName());
    }

    private static final Gson GSON = new GsonBuilder()
            .create();

    private final String name;
    private final ConfigValue<T> defaultValue;
    private final Class<T> type;

}
