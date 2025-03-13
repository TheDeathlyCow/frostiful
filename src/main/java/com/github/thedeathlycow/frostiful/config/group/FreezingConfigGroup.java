package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.survival.wind.WindSpawnStrategies;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.math.MathHelper;

@Config(name = Frostiful.MODID + ".freezing")
public class FreezingConfigGroup implements ConfigData {

    boolean doPassiveFreezing = true;
    boolean doWindSpawning = true;
    WindSpawnStrategies windSpawnStrategy = WindSpawnStrategies.POINT;
    boolean spawnWindInAir = true;
    boolean windDestroysTorches = true;
    boolean doSnowPacking = true;
    boolean applyEnvironmentPenaltyWhenWet = true;
    int passiveFreezingTickInterval = 1;
    int windSpawnCapPerSecond = 15;
    int windSpawnRarity = 750;
    int windSpawnRarityThunder = 500;
    float maxPassiveFreezingPercent = 1.0f;
    float soakPercentFromWaterPotion = 0.5f;
    int sunLichenHeatPerLevel = 500;
    int sunLichenBurnTime = 3 * 20;
    double campfireWarmthSearchRadius = 10;
    int campfireWarmthTime = 1200;
    int freezingWindFrost = 160;
    int conduitPowerWarmthPerTick = 12;
    int heatFromHotFloor = 12;
    float shiverBelow = -0.51f;
    int shiverWarmth = 1;
    int stopShiverWarmingBelowFoodLevel = 10;
    int warmFoodWarmthTime = 60 * 20;

    public boolean doPassiveFreezing() {
        return doPassiveFreezing;
    }

    public WindSpawnStrategies getWindSpawnStrategy() {
        if (!doWindSpawning) {
            return WindSpawnStrategies.NONE;
        }

        return windSpawnStrategy;
    }

    public boolean spawnWindInAir() {
        return spawnWindInAir;
    }

    public boolean isWindDestroysTorches() {
        return windDestroysTorches;
    }

    public boolean doSnowPacking() {
        return doSnowPacking;
    }

    public int getPassiveFreezingTickInterval() {
        return passiveFreezingTickInterval;
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

    public float getShiverBelow() {
        return MathHelper.clamp(shiverBelow, -1.0f, 1.0f);
    }

    public int getShiverWarmth() {
        return shiverWarmth;
    }

    public int getStopShiverWarmingBelowFoodLevel() {
        return stopShiverWarmingBelowFoodLevel;
    }

    public int getWarmFoodWarmthTime() {
        return warmFoodWarmthTime;
    }
}
