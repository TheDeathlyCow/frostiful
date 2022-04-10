package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public enum ConfigKeys implements ConfigKey{
    TICKS_PER_FROST_RESISTANCE(1200),
    BASE_ENTITY_FROST_RESISTANCE(0.11666666666667),
    BASE_PLAYER_FROST_RESITANCE(3.0D),
    CHILLY_BIOME_FREEZE_RATE(1),
    COLD_BIOME_FREEZE_RATE(2),
    FREEZING_BIOME_FREEZE_RATE(5),
    POWDER_SNOW_FREEZE_RATE_MULTIPLIER(3.0D),
    WET_FREEZE_RATE_MULTIPLIER(1.5D),
    WARM_BIOME_THAW_RATE(10),
    ON_FIRE_THAW_RATE(100),
    WARMTH_PER_LIGHT_LEVEL(2),
    MIN_WARMTH_LIGHT_LEVEL(7),
    FREEZE_DAMAGE_AMOUNT(1),
    FREEZE_EXTRA_DAMAGE_AMOUNT(5);

    @Override
    @NotNull
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public Object deserialize(JsonElement jsonElement) {
        return GSON.fromJson(jsonElement, defaultValue.getClass());
    }

    private static final Gson GSON = new GsonBuilder()
            .create();

    final Object defaultValue;

    ConfigKeys(@NotNull Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
