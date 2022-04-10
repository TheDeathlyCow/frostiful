package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public double getDouble(ConfigKey<Double> key) {
        return get(key);
    }

    public int getInt(ConfigKey<Integer> key) {
        return get(key);
    }

    public <T> T get(ConfigKey<T> key) {
        T value = null;
        try {
            Class<T> type = key.getType();
            value = type.cast(this.values.get(key).get());
        } catch (ClassCastException e) {
            LostInTheCold.LOGGER.warn("Bad config cast");
        }

        return value;
    }

    public <T> void addEntry(ConfigKey<T> key) {
        values.put(key, key.getDefaultValue());
    }

    public <T> void setValue(ConfigKey<? extends T> key, ConfigValue<? extends T> value) {
        if (this.values.containsKey(key)) {
            this.values.put(key, value);
        }
    }

    public void update(Map<ConfigKey<?>, ConfigValue<?>> inConfig) {
        for (Map.Entry<ConfigKey<?>, ConfigValue<?>> entry : inConfig.entrySet()) {
            this.setValue(entry.getKey(), entry.getValue());
        }
    }

    public void reset() {
        for (Map.Entry<ConfigKey<?>, ConfigValue<?>> entry : values.entrySet()) {
            ConfigKey<?> key = entry.getKey();
            values.put(key, new ConfigValue<>(key.getDefaultValue()));
        }
    }

    private final Map<ConfigKey<Integer>, ConfigValue<Integer>> integers = new HashMap<>();
    private final Map<ConfigKey<Double>, ConfigValue<Double>> doubles = new HashMap<>();
    private final Map<ConfigKey<?>, ConfigValue<?>> values = new HashMap<>();
}
