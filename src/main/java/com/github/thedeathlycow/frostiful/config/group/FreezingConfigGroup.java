package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Frostiful.MODID + ".freezing")
public class FreezingConfigGroup implements ConfigData {

    boolean doPassiveFreezing = true;
    double biomeTemperatureMultiplier = 4.0;
    double passiveFreezingStartTemp = 0.25;
    float passiveFreezingWetnessScaleMultiplier = 2.1f;
    int rainWetnessIncrease = 1;
    int touchingWaterWetnessIncrease = 5;
    int dryRate = 1;
    int onFireDryDate = 50;
    int cannotFreezeThawRate = 100;
    int onFireThawRate = 50;
    int conduitPowerWarmthPerTick = 12;
    int warmthPerLightLevel = 4;
    int minLightForWarmthDay = 7;
    int minLightForWarmthNight = 9;
    int freezeDamageRate = 20;
    int freezeDamageAmount = 2;
    int freezeDamageExtraAmount = 5;
    int powderSnowFreezeRate = 30;
    int sunLichenHeatPerLevel = 100;
    double campfireWarmthSearchRadius = 10;
    int campfireWarmthTime = 1200;

    public boolean doPassiveFreezing() {
        return doPassiveFreezing;
    }

    public double getBiomeTemperatureMultiplier() {
        return biomeTemperatureMultiplier;
    }

    public double getPassiveFreezingStartTemp() {
        return passiveFreezingStartTemp;
    }

    public float getPassiveFreezingWetnessScaleMultiplier() {
        return passiveFreezingWetnessScaleMultiplier;
    }

    public int getRainWetnessIncrease() {
        return rainWetnessIncrease;
    }

    public int getTouchingWaterWetnessIncrease() {
        return touchingWaterWetnessIncrease;
    }

    public int getDryRate() {
        return dryRate;
    }

    public int getOnFireDryRate() {
        return onFireDryDate;
    }

    public int getCannotFreezeThawRate() {
        return cannotFreezeThawRate;
    }

    public int getOnFireThawRate() {
        return onFireThawRate;
    }

    public int getConduitPowerWarmthPerTick() {
        return conduitPowerWarmthPerTick;
    }

    public int getWarmthPerLightLevel() {
        return warmthPerLightLevel;
    }

    public int getMinLightForWarmthDay() {
        return minLightForWarmthDay;
    }

    public int getMinLightForWarmthNight() {
        return minLightForWarmthNight;
    }

    public int getFreezeDamageRate() {
        return freezeDamageRate;
    }

    public int getFreezeDamageAmount() {
        return freezeDamageAmount;
    }

    public int getFreezeDamageExtraAmount() {
        return freezeDamageExtraAmount;
    }

    public int getPowderSnowFreezeRate() {
        return powderSnowFreezeRate;
    }

    public int getSunLichenHeatPerLevel() {
        return sunLichenHeatPerLevel;
    }

    public double getCampfireWarmthSearchRadius() {
        return campfireWarmthSearchRadius;
    }

    public int getCampfireWarmthTime() {
        return campfireWarmthTime;
    }

}
