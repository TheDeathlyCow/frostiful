package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.google.gson.JsonElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LostInTheColdConfig {

    public LostInTheColdConfig(String name) {
        this.name = name;
    }

    public static LostInTheColdConfig constructDefaultConfig() {
        LostInTheColdConfig config = new LostInTheColdConfig("primary_config");
        for (ConfigKey<?> key : ConfigKeys.REGISTRY.getEntries()) {
            config.addEntry(key);
        }
        return config;
    }

    public <T extends Comparable<T>> T get(ConfigKey<T> key) {
        Comparable<?> value = this.values.get(key);
        if (value != null) {
            return key.getType().cast(value);
        } else {
            throw new IllegalArgumentException("Cannot get value of " + key + " as it does not exist in config " + this.getName());
        }
    }

    public void addEntry(ConfigKey<?> key) {
        if (!this.values.containsKey(key)) {
            this.values.put(key, key.getDefaultValue());
        } else {
            LostInTheCold.LOGGER.warn("Attempted to add duplicate value " + key + " to config " + this.getName());
        }
    }


    public <T extends Comparable<T>> void deserializeAndSet(ConfigKey<T> key, JsonElement jsonElement) {
        T value = key.deserialize(jsonElement);
        this.setValue(key, value);

    }

    public <T extends Comparable<T>, V extends T> void setValue(ConfigKey<T> key, V value) {
        if (this.values.containsKey(key)) {
            if (key.isValid(value)) {
                this.values.put(key, value);
            } else {
                throw new IllegalArgumentException("In valid value of " + value + " for config key " + key.getName());
            }
        }
    }

    public void update(LostInTheColdConfig inConfig) {
        this.values.putAll(inConfig.values);
    }

    public void reset() {
        for (Map.Entry<ConfigKey<?>, Comparable<?>> entry : values.entrySet()) {
            ConfigKey<?> key = entry.getKey();
            values.put(key, key.getDefaultValue());
        }
    }

    public String getName() {
        return name;
    }

    private final Map<ConfigKey<?>, Comparable<?>> values = new HashMap<>();
    private final String name;

}
