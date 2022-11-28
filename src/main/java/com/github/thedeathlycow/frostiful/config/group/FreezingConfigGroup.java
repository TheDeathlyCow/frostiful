package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.math.MathHelper;

@Config(name = Frostiful.MODID + ".freezing")
public class FreezingConfigGroup implements ConfigData {

    double biomeTemperatureMultiplier = 4.0;
    double passiveFreezingStartTemp = 0.25;
    int wetFreezeRate = 2;
    int cannotFreezeThawRate = 100;
    int onFireThawRate = 10;
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

    float shakingStartPercent = 0.5f;

    public double getBiomeTemperatureMultiplier() {
        return biomeTemperatureMultiplier;
    }

    public double getPassiveFreezingStartTemp() {
        return passiveFreezingStartTemp;
    }

    public int getWetFreezeRate() {
        return wetFreezeRate;
    }

    public int getCannotFreezeThawRate() {
        return cannotFreezeThawRate;
    }

    public int getOnFireThawRate() {
        return onFireThawRate;
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

    public float getShakingStartPercent() {
        return MathHelper.clamp(shakingStartPercent, 0.0f, 1.0f);
    }
}
