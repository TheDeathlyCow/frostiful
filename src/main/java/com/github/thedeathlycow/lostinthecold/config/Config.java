package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public double getDouble(ConfigKey key) {
        return (Double) getObject(key);
    }

    public String getString(ConfigKey key) {
        return (String) getObject(key);
    }

    public int getInt(ConfigKey key) {
        return (Integer) getObject(key);
    }

    public Object getObject(ConfigKey key) {
        return this.values.get(key);
    }

    public void addEntry(ConfigKey key) {
        if (!this.values.containsKey(key)) {
            this.values.put(key, key.getDefaultValue());
        } else {
            LostInTheCold.LOGGER.warn("Attempted to add duplicate value " + key + " to config");
        }
    }

    public void setValue(ConfigKey key, Object value) {
        if (this.values.containsKey(key)) {
            Object currentValue = this.values.get(key);
            if (currentValue.getClass().equals(value.getClass())) {
                this.values.put(key, value);
            } else {
                String msg = String.format("Attempted to update config key %s with type %s, but was expecting %s",
                        key, value.getClass().getName(), currentValue.getClass().getName());
                LostInTheCold.LOGGER.error(msg);
            }
        }
    }

    public void update(Map<ConfigKey, Object> inConfig) {
        for (Map.Entry<ConfigKey, Object> entry : inConfig.entrySet()) {
            this.setValue(entry.getKey(), entry.getValue());
        }
    }

    public void reset() {
        for (Map.Entry<ConfigKey, Object> entry : values.entrySet()) {
            ConfigKey key = entry.getKey();
            values.put(key, key.getDefaultValue());
        }
    }

    private final Map<ConfigKey, Object> values = new HashMap<>();
}
