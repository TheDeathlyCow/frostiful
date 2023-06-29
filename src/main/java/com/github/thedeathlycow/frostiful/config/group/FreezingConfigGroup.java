package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.math.MathHelper;

@Config(name = Frostiful.MODID + ".freezing")
public class FreezingConfigGroup implements ConfigData {

    boolean doPassiveFreezing = true;
    boolean doWindSpawning = true;

    boolean spawnWindInAir = true;
    boolean windDestroysTorches = true;
    int windSpawnCapPerSecond = 15;
    int windSpawnRarity = 750;
    int windSpawnRarityThunder = 500;
    float maxPassiveFreezingPercent = 1.0f;
    float passiveFreezingWetnessScaleMultiplier = 2.1f;
    float soakPercentFromWaterPotion = 0.5f;
    int sunLichenHeatPerLevel = 500;
    int sunLichenBurnTime = 3 * 20;
    double campfireWarmthSearchRadius = 10;
    int campfireWarmthTime = 1200;
    int freezingWindFrost = 160;
    int conduitPowerWarmthPerTick = 12;
    int heatFromHotFloor = 12;
    float iceSkateSlipperiness = 1.075f;
    float iceSkatBrakeSlipperiness = 1.0f;


    public boolean doPassiveFreezing() {
        return doPassiveFreezing;
    }

    public boolean doWindSpawning() {
        return doWindSpawning;
    }

    public boolean spawnWindInAir() {
        return spawnWindInAir;
    }

    public boolean isWindDestroysTorches() {
        return windDestroysTorches;
    }

    public int getWindSpawnCapPerSecond() {
        return windSpawnCapPerSecond;
    }

    public int getWindSpawnRarity() {
        return windSpawnRarity;
    }

    public int getWindSpawnRarityThunder() {
        return windSpawnRarityThunder;
    }

    public float getMaxPassiveFreezingPercent() {
        return maxPassiveFreezingPercent;
    }

    public float getPassiveFreezingWetnessScaleMultiplier() {
        return passiveFreezingWetnessScaleMultiplier;
    }

    public float getSoakPercentFromWaterPotion() {
        return MathHelper.clamp(soakPercentFromWaterPotion, 0.0f, 1.0f);
    }

    public int getSunLichenHeatPerLevel() {
        return sunLichenHeatPerLevel;
    }

    public int getSunLichenBurnTime() {
        return sunLichenBurnTime;
    }

    public double getCampfireWarmthSearchRadius() {
        return campfireWarmthSearchRadius;
    }

    public int getCampfireWarmthTime() {
        return campfireWarmthTime;
    }

    public int getFreezingWindFrost() {
        return freezingWindFrost;
    }

    public int getConduitWarmthPerTick() {
        return conduitPowerWarmthPerTick;
    }

    public int getHeatFromHotFloor() {
        return heatFromHotFloor;
    }

    public float getIceSkateSlipperiness() {
        return iceSkateSlipperiness;
    }

    public float getIceSkatBrakeSlipperiness() {
        return iceSkatBrakeSlipperiness;
    }
}
