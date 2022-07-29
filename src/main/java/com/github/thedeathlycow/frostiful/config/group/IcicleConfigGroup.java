package com.github.thedeathlycow.frostiful.config.group;

import com.google.common.collect.ImmutableList;
import com.oroarmor.config.ConfigItemGroup;
import com.oroarmor.config.DoubleConfigItem;
import com.oroarmor.config.IntegerConfigItem;

public class IcicleConfigGroup extends ConfigItemGroup {

    private static final String TRANSLATE_BASE_STRING = "config.frostiful.icicle_config.";

    public static final DoubleConfigItem BECOME_UNSTABLE_CHANCE = new DoubleConfigItem("become_unstable_chance", 0.05, TRANSLATE_BASE_STRING + "become_unstable_chance", null, 0.0, 1.0);
    public static final DoubleConfigItem GROW_CHANCE = new DoubleConfigItem("grow_chance", 0.02, TRANSLATE_BASE_STRING + "grow_chance", null, 0.0, 1.0);
    public static final DoubleConfigItem GROW_CHANGE_DURING_RAIN = new DoubleConfigItem("grow_chance_during_rain", 0.09, TRANSLATE_BASE_STRING + "grow_chance_during_rain", null, 0.0, 1.0);
    public static final DoubleConfigItem GROW_CHANCE_DURING_THUNDER = new DoubleConfigItem("grow_chance_during_thunder", 0.15, TRANSLATE_BASE_STRING + "grow_chance_during_thunder", null, 0.0, 1.0);
    public static final IntegerConfigItem FROST_ARROW_FREEZE_AMOUNT = new IntegerConfigItem("frost_arrow_freeze_amount", 1000, TRANSLATE_BASE_STRING + "frost_arrow_freeze_amount", null, 0, Integer.MAX_VALUE);
    public static final IntegerConfigItem ICICLE_COLLISION_FREEZE_AMOUNT = new IntegerConfigItem("icicle_collision_freeze_amount", 1000, TRANSLATE_BASE_STRING + "icicle_collision_freeze_amount", null, 0, Integer.MAX_VALUE);

    /**
     * Creates a new {@link ConfigItemGroup} with the list of configs and the name
     */
    public IcicleConfigGroup() {
        super(
                ImmutableList.of(
                        BECOME_UNSTABLE_CHANCE,
                        GROW_CHANCE,
                        GROW_CHANGE_DURING_RAIN,
                        GROW_CHANCE_DURING_THUNDER,
                        FROST_ARROW_FREEZE_AMOUNT,
                        ICICLE_COLLISION_FREEZE_AMOUNT
                ),
                "icicle_config"
        );
    }

}
