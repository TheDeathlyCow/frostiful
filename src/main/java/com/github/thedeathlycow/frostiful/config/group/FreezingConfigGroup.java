package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.google.common.collect.ImmutableList;
import com.oroarmor.config.ConfigItemGroup;
import com.oroarmor.config.DoubleConfigItem;
import com.oroarmor.config.IntegerConfigItem;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Frostiful.MODID + ".freezing")
public class FreezingConfigGroup implements ConfigData {

    double biomeTemperatureMultiplier = 4.0;
    double passiveFreezingStartTemp = 0.25;
    int wetFreezeRate = 2;
    int cannotFreezeThawRate = 100;
    int onFireThawRate = 10;
    int warmthPerLightLevel = 4;
    int minWarmthForLightDay = 7;
    int minWarmthForLightNight = 9;
    int freezeDamageRate = 20;
    int freezeDamageAmount = 2;
    int freezeDamageExtraAmount = 5;
    int powderSnowFreezeRate = 30;
    int sunLichenHeatPerLevel = 100;
    double campfireWarmthSearchRadius = 10;
    int campfireWarmthTime = 1200;

}
