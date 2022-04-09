package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public enum ConfigKeys implements ConfigKey{
    TICKS_PER_FROST_RESISTANCE(Integer.class),
    BASE_ENTITY_FROST_RESISTANCE(Double.class),
    BASE_PLAYER_FROST_RESITANCE(Double.class),
    CHILLY_BIOME_FREEZE_RATE(Integer.class),
    COLD_BIOME_FREEZE_RATE(Integer.class),
    FREEZING_BIOME_FREEZE_RATE(Integer.class),
    POWDER_SNOW_FREEZE_RATE_MULTIPLIER(Double.class),
    WET_FREEZE_RATE_MULTIPLIER(Double.class),
    WARM_BIOME_THAW_RATE(Integer.class),
    ON_FIRE_THAW_RATE(Integer.class),
    WARMTH_PER_LIGHT_LEVEL(Integer.class),
    MIN_WARMTH_LIGHT_LEVEL(Integer.class);

    @Override
    public Object deserialize(JsonElement jsonElement) {
        return GSON.fromJson(jsonElement, type);
    }

    private static final Gson GSON = new GsonBuilder()
            .create();

    Class<?> type;
    ConfigKeys(Class<?> type) {
        this.type = type;
    }
}
