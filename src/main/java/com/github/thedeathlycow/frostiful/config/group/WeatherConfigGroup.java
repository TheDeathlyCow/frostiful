package com.github.thedeathlycow.frostiful.config.group;

import com.google.common.collect.ImmutableList;
import com.oroarmor.config.ConfigItemGroup;
import com.oroarmor.config.IntegerConfigItem;

public class WeatherConfigGroup extends ConfigItemGroup {

    private static final String TRANSLATE_BASE_STRING = "config.frostiful.weather_config.";

    public static final IntegerConfigItem FREEZE_TOP_LAYER_MAX_ACCUMULATION = new IntegerConfigItem("freeze_top_layer_max_accumulation", 2, TRANSLATE_BASE_STRING + "freeze_top_layer_max_accumulation", null, 0, 8);
    public static final IntegerConfigItem MAX_SNOW_BUILDUP_STEP = new IntegerConfigItem("max_snow_buildup_step", 2, TRANSLATE_BASE_STRING + "max_snow_buildup_step", null, 1, 8);
    public static final IntegerConfigItem MAX_SNOW_BUILDUP = new IntegerConfigItem("max_snow_buildup", 8, TRANSLATE_BASE_STRING + "max_snow_buildup", null, 0, 8);

    /**
     * Creates a new {@link ConfigItemGroup} with the list of configs and the name
     */
    public WeatherConfigGroup() {
        super(ImmutableList.of(FREEZE_TOP_LAYER_MAX_ACCUMULATION, MAX_SNOW_BUILDUP_STEP, MAX_SNOW_BUILDUP), "weather_config");
    }
}
