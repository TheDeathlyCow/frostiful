package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Frostiful.MODID + ".environment_config")
public class EnvironmentConfigGroup implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    double maxTemperatureForColdC = 10.0;
    @ConfigEntry.Gui.Tooltip
    double degreesCPerTemperatureDecrease = 10.0;
    double environmentTemperatureMultiplier = 1.0;
    boolean applyEnvironmentPenaltyWhenWet = true;
    int rainWetnessIncrease = 1;
    int touchingWaterWetnessIncrease = 5;
    int onFireDryDate = 50;
    int onFireWarmRate = 50;
    int powderSnowFreezeRate = 30;
    int warmthPerLightLevel = 2;
    int minLightForWarmth = 5;
    int maxSnowAccumulationTicks = 100;
    float environmentFreezingSoakedMultiplier = 2.0f;

    public double getMaxTemperatureForColdC() {
        return maxTemperatureForColdC;
    }

    public double getDegreesCPerTemperatureDecrease() {
        return degreesCPerTemperatureDecrease;
    }

    public double getEnvironmentTemperatureMultiplier() {
        return environmentTemperatureMultiplier;
    }

    public boolean applyEnvironmentPenaltyWhenWet() {
        return applyEnvironmentPenaltyWhenWet;
    }

    public int getRainWetnessIncrease() {
        return rainWetnessIncrease;
    }

    public int getTouchingWaterWetnessIncrease() {
        return touchingWaterWetnessIncrease;
    }

    public int getOnFireDryDate() {
        return onFireDryDate;
    }

    public int getOnFireWarmRate() {
        return onFireWarmRate;
    }

    public int getPowderSnowFreezeRate() {
        return powderSnowFreezeRate;
    }

    public int getWarmthPerLightLevel() {
        return warmthPerLightLevel;
    }

    public int getMinLightForWarmth() {
        return minLightForWarmth;
    }

    public int getMaxSnowAccumulationTicks() {
        return maxSnowAccumulationTicks;
    }

    public float getEnvironmentFreezingSoakedMultiplier() {
        return environmentFreezingSoakedMultiplier;
    }

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
        this.maxSnowAccumulationTicks = Math.max(0, this.maxSnowAccumulationTicks);

        if (this.maxTemperatureForColdC > 15) {
            this.maxTemperatureForColdC = 15;
        }

        if (this.degreesCPerTemperatureDecrease <= 0) {
            throw new ValidationException("Degrees C Per Temperature Decrease must be positive!");
        }
    }
}
