package com.github.thedeathlycow.frostiful.config.group;

import com.google.common.collect.ImmutableList;
import com.oroarmor.config.ConfigItemGroup;
import com.oroarmor.config.DoubleConfigItem;
import com.oroarmor.config.IntegerConfigItem;

public class FreezingConfigGroup extends ConfigItemGroup {

    private static final String TRANSLATE_BASE_STRING = "config.frostiful.freezing.";

    public static final DoubleConfigItem BIOME_TEMPERATURE_MULTIPLIER = new DoubleConfigItem("biome_temperature_multiplier", 4.0, TRANSLATE_BASE_STRING + "biome_temperature_multiplier", null, 0.0, Double.POSITIVE_INFINITY);
    public static final DoubleConfigItem PASSIVE_FREEZING_START_TEMP = new DoubleConfigItem("passive_freezing_start_temp", 0.25, TRANSLATE_BASE_STRING + "passive_freezing_start_temp", null, -2.0, 2.0);
    public static final IntegerConfigItem WET_FREEZE_RATE = new IntegerConfigItem("wet_freeze_rate", 2, TRANSLATE_BASE_STRING + "wet_freeze_rate", null, 0, Integer.MAX_VALUE);
    public static final IntegerConfigItem CANNOT_FREEZE_WARM_RATE = new IntegerConfigItem("cannot_freeze_thaw_rate", 10, TRANSLATE_BASE_STRING + "cannot_freeze_thaw_rate", null, 0, Integer.MAX_VALUE);
    public static final IntegerConfigItem ON_FIRE_THAW_RATE = new IntegerConfigItem("on_fire_thaw_rate", 10, TRANSLATE_BASE_STRING + "on_fire_thaw_rate", null, 0, Integer.MAX_VALUE);
    public static final IntegerConfigItem WARMTH_PER_LIGHT_LEVEL = new IntegerConfigItem("warmth_per_light_level", 2, TRANSLATE_BASE_STRING + "warmth_per_light_level", null, 0, Integer.MAX_VALUE);
    public static final IntegerConfigItem MIN_WARMTH_LIGHT_LEVEL_DAY = new IntegerConfigItem("min_warmth_light_level_day", 7, TRANSLATE_BASE_STRING + "min_warmth_light_level_day", null, 0, Integer.MAX_VALUE);
    public static final IntegerConfigItem MIN_WARMTH_LIGHT_LEVEL_NIGHT = new IntegerConfigItem("min_warmth_light_level_night", 9, TRANSLATE_BASE_STRING + "min_warmth_light_level_night", null, 0, Integer.MAX_VALUE);
    public static final IntegerConfigItem FREEZE_DAMAGE_AMOUNT = new IntegerConfigItem("freeze_damage_amount", 2, TRANSLATE_BASE_STRING + "freeze_damage_amount", null, 0, Integer.MAX_VALUE);
    public static final IntegerConfigItem FREEZE_EXTRA_DAMAGE_AMOUNT = new IntegerConfigItem("freeze_extra_damage_amount", 5, TRANSLATE_BASE_STRING + "freeze_extra_damage_amount", null, 0, Integer.MAX_VALUE);
    public static final IntegerConfigItem POWDER_SNOW_FREEZE_RATE = new IntegerConfigItem("powder_snow_freeze_rate", 30, TRANSLATE_BASE_STRING + "powder_snow_freeze_rate", null, 0, Integer.MAX_VALUE);

    /**
     * Creates a new {@link ConfigItemGroup} with the list of configs and the name
     */
    public FreezingConfigGroup() {
        super(ImmutableList.of(
                        BIOME_TEMPERATURE_MULTIPLIER,
                        PASSIVE_FREEZING_START_TEMP,
                        WET_FREEZE_RATE,
                        CANNOT_FREEZE_WARM_RATE,
                        ON_FIRE_THAW_RATE,
                        WARMTH_PER_LIGHT_LEVEL,
                        MIN_WARMTH_LIGHT_LEVEL_DAY,
                        MIN_WARMTH_LIGHT_LEVEL_NIGHT,
                        FREEZE_DAMAGE_AMOUNT,
                        FREEZE_EXTRA_DAMAGE_AMOUNT,
                        POWDER_SNOW_FREEZE_RATE
                ),
                "freezing");
    }
}
