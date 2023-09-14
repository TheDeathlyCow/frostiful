package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;

public class SurvivalUtils {


    public static boolean isShivering(TemperatureAware temperatureAware) {
        FrostifulConfig config = Frostiful.getConfig();
        return temperatureAware.thermoo$getTemperatureScale() <= config.freezingConfig.getShiverBelow();
    }

    private SurvivalUtils() {
    }
}
