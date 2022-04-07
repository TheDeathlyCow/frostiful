package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FreezeRateConfig {

    public FreezeRateConfig(int chillyBiomeFreezeRate, int coldBiomeFreezeRate, int freezingBiomeFreezeRate, double powderSnowFreezeRateMultiplier, double wetFreezeRateMultiplier) {
        this.chillyBiomeFreezeRate = chillyBiomeFreezeRate;
        this.coldBiomeFreezeRate = coldBiomeFreezeRate;
        this.freezingBiomeFreezeRate = freezingBiomeFreezeRate;
        this.powderSnowFreezeRateMultiplier = powderSnowFreezeRateMultiplier;
        this.wetFreezeRateMultiplier = wetFreezeRateMultiplier;
    }

    public static FreezeRateConfig deserialize(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        int chillyBiomeFreezeRate = object.get("chilly_biome_freeze_rate").getAsInt();
        int coldBiomeFreezeRate = object.get("cold_biome_freeze_rate").getAsInt();
        int freezingBiomeFreezeRate = object.get("freezing_biome_freeze_rate").getAsInt();
        double powderSnowFreezeRateMultiplier = object.get("powder_snow_freeze_rate_multiplier").getAsDouble();
        double wetFreezeRateMultiplier = object.get("wet_freeze_rate_multiplier").getAsDouble();
        return new FreezeRateConfig(chillyBiomeFreezeRate, coldBiomeFreezeRate, freezingBiomeFreezeRate, powderSnowFreezeRateMultiplier, wetFreezeRateMultiplier);
    }

    public int getChillyBiomeFreezeRate() {
        return chillyBiomeFreezeRate;
    }

    public int getColdBiomeFreezeRate() {
        return coldBiomeFreezeRate;
    }

    public int getFreezingBiomeFreezeRate() {
        return freezingBiomeFreezeRate;
    }

    public double getPowderSnowFreezeRateMultiplier() {
        return powderSnowFreezeRateMultiplier;
    }

    public double getWetFreezeRateMultiplier() {
        return wetFreezeRateMultiplier;
    }

    private final int chillyBiomeFreezeRate;
    private final int coldBiomeFreezeRate;
    private final int freezingBiomeFreezeRate;
    private final double powderSnowFreezeRateMultiplier;
    private final double wetFreezeRateMultiplier;
}
