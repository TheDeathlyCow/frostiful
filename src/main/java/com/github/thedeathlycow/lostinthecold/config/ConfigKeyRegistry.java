package com.github.thedeathlycow.lostinthecold.config;

import java.util.*;

public class ConfigKeyRegistry {

    public Collection<ConfigKey<?>> getEntries() {
        return Collections.unmodifiableCollection(keys.values());
    }

    public ConfigKey<?> getEntry(String name) {
        if (keys.containsKey(name)) {
            return keys.get(name);
        } else {
            throw new IllegalArgumentException("Cannot find config key " + name);
        }
    }

    public <T extends Comparable<T>> ConfigKey<T> register(ConfigKey<T> configKey) {
        if (!keys.containsKey(configKey.getName())) {
            keys.put(configKey.getName(), configKey);
            return configKey;
        } else {
            throw new IllegalArgumentException("Config key " + configKey.getName() + " already registered!");
        }
    }

    private final Map<String, ConfigKey<?>> keys = new HashMap<>();
}
