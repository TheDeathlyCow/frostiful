package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Frostiful.MODID + ".icicle_config")
public class IcicleConfigGroup implements ConfigData {

    double becomeUnstableChance = 0.05;
    double growChance = 0.02;
    double growChanceDuringRain = 0.09;
    double growChanceDuringThunder = 0.15;
    int frostArrowFreezeAmount = 1000;
    int icicleCollisionFreezeAmount = 3000;

    public double getBecomeUnstableChance() {
        return becomeUnstableChance;
    }

    public double getGrowChance() {
        return growChance;
    }

    public double getGrowChanceDuringRain() {
        return growChanceDuringRain;
    }

    public double getGrowChanceDuringThunder() {
        return growChanceDuringThunder;
    }

    public int getFrostArrowFreezeAmount() {
        return frostArrowFreezeAmount;
    }

    public int getIcicleCollisionFreezeAmount() {
        return icicleCollisionFreezeAmount;
    }
}
