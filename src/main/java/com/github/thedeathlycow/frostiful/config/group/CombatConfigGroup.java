package com.github.thedeathlycow.frostiful.config.group;

import com.google.common.collect.ImmutableList;
import com.oroarmor.config.ConfigItemGroup;
import com.oroarmor.config.IntegerConfigItem;

public class CombatConfigGroup extends ConfigItemGroup {

    private static final String TRANSLATE_BASE_STRING = "config.frostiful.combat_config.";
    public static final IntegerConfigItem HEAT_DRAIN_PER_LEVEL = new IntegerConfigItem("heat_drain_per_level", 100, TRANSLATE_BASE_STRING + "heat_drain_per_level");

    public CombatConfigGroup() {
        super(ImmutableList.of(HEAT_DRAIN_PER_LEVEL), "combat_config");
    }
}
