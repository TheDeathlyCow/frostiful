package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ThawRateConfig {


    public ThawRateConfig(int warmBiomeFreezeRate, int onFireFreezeRate, int warmthPerLightLevel, int minWarmthLightLevel) {
        this.warmBiomeFreezeRate = warmBiomeFreezeRate;
        this.onFireFreezeRate = onFireFreezeRate;
        this.warmthPerLightLevel = warmthPerLightLevel;
        this.minWarmthLightLevel = minWarmthLightLevel;
    }

    public static ThawRateConfig deserialize(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        int warmBiomeFreezeRate = object.get("warm_biome_freeze_rate").getAsInt();
        int onFireFreezeRate = object.get("on_fire_freeze_rate").getAsInt();
        int warmthPerLightLevel = object.get("warmth_per_light_level").getAsInt();
        int minWarmthLightLevel = object.get("min_warmth_light_level").getAsInt();
        return new ThawRateConfig(warmBiomeFreezeRate, onFireFreezeRate, warmthPerLightLevel, minWarmthLightLevel);
    }

    public int getWarmBiomeFreezeRate() {
        return warmBiomeFreezeRate;
    }

    public int getOnFireFreezeRate() {
        return onFireFreezeRate;
    }

    public int getWarmthPerLightLevel() {
        return warmthPerLightLevel;
    }

    public int getMinWarmthLightLevel() {
        return minWarmthLightLevel;
    }

    private final int warmBiomeFreezeRate;
    private final int onFireFreezeRate;
    private final int warmthPerLightLevel;
    private final int minWarmthLightLevel;

}
