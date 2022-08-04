package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Frostiful.MODID + ".weather")
public class WeatherConfigGroup implements ConfigData {
    @ConfigEntry.BoundedDiscrete(max = 8)
    int maxSnowStep = 2;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 8)
    int maxSnowBuildup = 8;
}
