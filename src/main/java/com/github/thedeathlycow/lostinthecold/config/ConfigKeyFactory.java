package com.github.thedeathlycow.lostinthecold.config;

public class ConfigKeyFactory {

    public static ConfigKey<Integer> createIntegerKey(String name, Integer defaultValue) {
        return new ConfigKey<>(name, defaultValue, Integer.class);
    }

    public static ConfigKey<Double> createDoubleKey(String name, Double defaultValue) {
        return new ConfigKey<>(name, defaultValue, Double.class);
    }

}
