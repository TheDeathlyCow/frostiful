package com.github.thedeathlycow.frostiful.config.group;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
public class IcicleConfigGroup implements ConfigData {
    
    double becomeUnstableChance = 0.05;
    double growChance = 0.02;
    double growChanceDuringRain = 0.09;
    double growChanceDuringThunder = 0.15;
    int frostArrowFreezeAmount = 1000;
    int icicleCollisionFreezeAmount = 3000;

}
