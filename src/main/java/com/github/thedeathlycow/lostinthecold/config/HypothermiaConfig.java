package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class HypothermiaConfig {

    public HypothermiaConfig(FreezeRateConfig freezeRateConfig, FrostResistanceConfig frostResistanceConfig, ThawRateConfig thawRateConfig) {
        this.freezeRateConfig = freezeRateConfig;
        this.frostResistanceConfig = frostResistanceConfig;
        this.thawRateConfig = thawRateConfig;
    }

    public static HypothermiaConfig deserialize(JsonElement jsonElement) {
        JsonObject object = jsonElement.getAsJsonObject();
        FreezeRateConfig freezeRateConfig = FreezeRateConfig.deserialize(object.get("freeze_rate_config"));
        FrostResistanceConfig frostResistanceConfig = FrostResistanceConfig.deserialize(object.get("frost_resistance_config"));
        ThawRateConfig thawRateConfig = ThawRateConfig.deserialize(object.get("thaw_rate_config"));
        return new HypothermiaConfig(freezeRateConfig, frostResistanceConfig, thawRateConfig);
    }

    public int getSecondsPerFrostResist() {
        return frostResistanceConfig.getSecondsPerFrostResistance();
    }

    public double getBaseEntityFrostResistance() {
        return frostResistanceConfig.getBaseEntityFrostResistance();
    }

    public int getChillyBiomeFreezeRate() {
        return freezeRateConfig.getChillyBiomeFreezeRate();
    }

    public int getColdBiomeFreezeRate() {
        return freezeRateConfig.getColdBiomeFreezeRate();
    }

    public int getFreezingBiomeFreezeRate() {
        return freezeRateConfig.getFreezingBiomeFreezeRate();
    }

    public double getPowderSnowFreezeRateMultiplier() {
        return freezeRateConfig.getPowderSnowFreezeRateMultiplier();
    }

    public double getWetFreezeRateMultiplier() {
        return freezeRateConfig.getWetFreezeRateMultiplier();
    }

    public int getWarmBiomeFreezeRate() {
        return thawRateConfig.getWarmBiomeFreezeRate();
    }

    public int getOnFireFreezeRate() {
        return thawRateConfig.getOnFireFreezeRate();
    }

    public int getWarmthPerLightLevel() {
        return thawRateConfig.getWarmthPerLightLevel();
    }

    public int getMinWarmthLightLevel() {
        return thawRateConfig.getMinWarmthLightLevel();
    }

    private final FreezeRateConfig freezeRateConfig;
    private final FrostResistanceConfig frostResistanceConfig;
    private final ThawRateConfig thawRateConfig;

}
