package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public static Config constructDefaultConfig() {
        Config config = new Config();
        config.addEntry(ConfigKeys.TICKS_PER_FROST_RESISTANCE, 1200);
        config.addEntry(ConfigKeys.BASE_ENTITY_FROST_RESISTANCE, 0.11666666666667);
        config.addEntry(ConfigKeys.BASE_PLAYER_FROST_RESITANCE, 3.0D);
        config.addEntry(ConfigKeys.CHILLY_BIOME_FREEZE_RATE, 1);
        config.addEntry(ConfigKeys.COLD_BIOME_FREEZE_RATE, 5);
        config.addEntry(ConfigKeys.FREEZING_BIOME_FREEZE_RATE, 15);
        config.addEntry(ConfigKeys.POWDER_SNOW_FREEZE_RATE_MULTIPLIER, 3.0);
        config.addEntry(ConfigKeys.WET_FREEZE_RATE_MULTIPLIER, 1.5);
        config.addEntry(ConfigKeys.WARM_BIOME_THAW_RATE, 10);
        config.addEntry(ConfigKeys.ON_FIRE_THAW_RATE, 100);
        config.addEntry(ConfigKeys.WARMTH_PER_LIGHT_LEVEL, 2);
        config.addEntry(ConfigKeys.MIN_WARMTH_LIGHT_LEVEL, 7);
        return config;
    }

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
        if (!this.defaultValues.containsKey(key)) {
            throw new IllegalArgumentException("Config does not contain entry: " + key);
        }

        Object value = this.values.get(key);
        if (value == null) {
            value = this.defaultValues.get(key);
        }
        return value;
    }

    public void addEntry(ConfigKey key, @NotNull Object defaultValue) {
        this.defaultValues.put(key, defaultValue);
        this.setValue(key, defaultValue);
    }

    public void setValue(ConfigKey key, Object value) {
        if (this.defaultValues.containsKey(key)) {
            Object defaultValue = this.defaultValues.get(key);
            if (defaultValue.getClass().equals(value.getClass())) {
                this.values.put(key, value);
            } else {
                String msg = String.format("Attempted to update config key %s with type %s, but was expecting %s",
                        key, value.getClass().getName(), defaultValue.getClass().getName());
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
        values.clear();
        values.putAll(defaultValues);
    }

    private final Map<ConfigKey, Object> values = new HashMap<>();
    private final Map<ConfigKey, Object> defaultValues = new HashMap<>();
}
