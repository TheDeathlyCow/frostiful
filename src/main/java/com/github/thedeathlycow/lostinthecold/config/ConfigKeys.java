package com.github.thedeathlycow.lostinthecold.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigKeys {

    private static final Map<String, ConfigKey<?>> keys = new HashMap<>();
    public static final ConfigKey<Integer> TICKS_PER_FROST_RESISTANCE = register(ConfigKeyFactory.createIntegerKey("ticks_per_frost_resistance", 1200));
    public static final ConfigKey<Double> BASE_ENTITY_FROST_RESISTANCE = register(ConfigKeyFactory.createDoubleKey("base_entity_frost_resistance", 0.11666666666667));
    public static final ConfigKey<Double> BASE_PLAYER_FROST_RESITANCE = register(ConfigKeyFactory.createDoubleKey("base_player_frost_resistance", 3.0D));
    public static final ConfigKey<Integer> CHILLY_BIOME_FREEZE_RATE = register(ConfigKeyFactory.createIntegerKey("chilly_biome_freeze_rate", 1));
    public static final ConfigKey<Integer> COLD_BIOME_FREEZE_RATE = register(ConfigKeyFactory.createIntegerKey("cold_biome_freeze_rate", 5));
    public static final ConfigKey<Integer> FREEZING_BIOME_FREEZE_RATE = register(ConfigKeyFactory.createIntegerKey("freezing_biome_freeze_rate", 15));
    public static final ConfigKey<Double> POWDER_SNOW_FREEZE_RATE_MULTIPLIER = register(ConfigKeyFactory.createDoubleKey("powder_snow_freeze_rate_multiplier", 3.0D));
    public static final ConfigKey<Double> WET_FREEZE_RATE_MULTIPLIER = register(ConfigKeyFactory.createDoubleKey("wet_freeze_rate_multiplier", 1.5D));
    public static final ConfigKey<Integer> WARM_BIOME_THAW_RATE = register(ConfigKeyFactory.createIntegerKey("warm_biome_thaw_rate", 10));
    public static final ConfigKey<Integer> ON_FIRE_THAW_RATE = register(ConfigKeyFactory.createIntegerKey("on_fire_thaw_rate", 100));
    public static final ConfigKey<Integer> WARMTH_PER_LIGHT_LEVEL = register(ConfigKeyFactory.createIntegerKey("warmth_per_light_level", 2));
    public static final ConfigKey<Integer> MIN_WARMTH_LIGHT_LEVEL = register(ConfigKeyFactory.createIntegerKey("min_warmth_light_level", 7));

    public static Config constructDefaultConfig() {
        Config config = new Config();
        for (ConfigKey<?> key : keys.values()) {
            config.addEntry(key);
        }
        return config;
    }

    public static ConfigKey<?> valueOf(String name) {
        return keys.get(name);
    }

    private static <T> ConfigKey<T> register(ConfigKey<T> key) {
        if (!keys.containsKey(key.getName())) {
            keys.put(key.getName(), key);
            return key;
        } else {
            throw new IllegalArgumentException("Config key " + key.getName() + " already registered!");
        }
    }
}
