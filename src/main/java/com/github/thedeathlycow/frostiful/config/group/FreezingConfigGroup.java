package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.math.MathHelper;

@Config(name = Frostiful.MODID + ".freezing")
public class FreezingConfigGroup implements ConfigData {

    boolean doPassiveFreezing = true;
    boolean doWindSpawning = true;

    boolean spawnWindInAir = true;
    boolean windDestroysTorches = true;
    int windSpawnCap = 750;
    int windSpawnCapPerPlayer = 60;
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

    public int getWindSpawnCap() {
        return windSpawnCap;
    }

    public int getWindSpawnCapPerPlayer() {
        return windSpawnCapPerPlayer;
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
}
