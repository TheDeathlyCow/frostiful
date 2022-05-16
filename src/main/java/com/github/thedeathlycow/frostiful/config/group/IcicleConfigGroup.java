package com.github.thedeathlycow.frostiful.config.group;

import com.oroarmor.config.ConfigItemGroup;
import com.oroarmor.config.DoubleConfigItem;
import com.oroarmor.config.IntegerConfigItem;

import java.util.List;

public class IcicleConfigGroup extends ConfigItemGroup {

    public static final DoubleConfigItem BECOME_UNSTABLE_CHANCE = new DoubleConfigItem("become_unstable_chance", 0.05, "config.frostiful.icicle_config.become_unstable_chance");
    public static final DoubleConfigItem GROW_CHANCE = new DoubleConfigItem("grow_chance", 0.02, "config.frostiful.icicle_config.grow_chance");
    public static final DoubleConfigItem GROW_CHANGE_DURING_RAIN = new DoubleConfigItem("grow_chance_during_rain", 0.09, "config.frostiful.icicle_config.grow_chance_during_rain");
    public static final DoubleConfigItem GROW_CHANCE_DURING_THUNDER = new DoubleConfigItem("grow_chance_during_thunder", 0.15, "config.frostiful.icicle_config.grow_chance_during_thunder");
    public static final IntegerConfigItem FROST_ARROW_FREEZE_AMOUNT = new IntegerConfigItem("frost_arrow_freeze_amount", 100, "config.frostiful.icicle_config.frost_arrow_freeze_amount");

    /**
     * Creates a new {@link ConfigItemGroup} with the list of configs and the name
     */
    public IcicleConfigGroup() {
        super(List.of(BECOME_UNSTABLE_CHANCE, GROW_CHANCE, GROW_CHANGE_DURING_RAIN, GROW_CHANCE_DURING_THUNDER, FROST_ARROW_FREEZE_AMOUNT), "icicle_config");
    }
}
