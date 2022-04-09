package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public double getDouble(String key) {
        return (Double) getObject(key);
    }

    public String getString(String key) {
        return (String) getObject(key);
    }

    public int getInt(String key) {
        return (Integer) getObject(key);
    }

    public Object getObject(String key) {
        if (!this.defaultValues.containsKey(key)) {
            throw new IllegalArgumentException("Config does not contain entry: " + key);
        }

        Object value = this.values.get(key);
        if (value == null) {
            value = this.defaultValues.get(key);
        }
        return value;
    }

    public void addEntry(String key, @NotNull Object defaultValue) {
        this.defaultValues.put(key, defaultValue);
        this.setValue(key, defaultValue);
    }

    public void setValue(String key, Object value) {
        if (this.defaultValues.containsKey(key)) {
            this.values.put(key, value);
        }
    }

    public void update(Config inConfig) {
        for (Map.Entry<String, Object> entry : inConfig.values.entrySet()) {
            if (entry.getValue() != null) {
                this.setValue(entry.getKey(), entry.getValue());
            }
        }
    }

    private final Map<String, Object> values = new HashMap<>();
    private final Map<String, Object> defaultValues = new HashMap<>();
}
