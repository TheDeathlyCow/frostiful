package com.github.thedeathlycow.frostiful.config.group;

import com.google.common.collect.ImmutableList;
import com.oroarmor.config.ConfigItemGroup;
import com.oroarmor.config.DoubleConfigItem;
import com.oroarmor.config.IntegerConfigItem;

public class CombatConfigGroup extends ConfigItemGroup {

    private static final String TRANSLATE_BASE_STRING = "config.frostiful.combat_config.";
    public static final IntegerConfigItem HEAT_DRAIN_PER_LEVEL = new IntegerConfigItem("heat_drain_per_level", 630, TRANSLATE_BASE_STRING + "heat_drain_per_level");
    public static final DoubleConfigItem HEAT_DRAIN_EFFICIENCY = new DoubleConfigItem("heat_drain_efficiency", 0.5, TRANSLATE_BASE_STRING + "heat_drain_efficiency", null, 0.0, 1.0);
    public CombatConfigGroup() {
        super(ImmutableList.of(HEAT_DRAIN_PER_LEVEL, HEAT_DRAIN_EFFICIENCY), "combat_config");
    }
}
