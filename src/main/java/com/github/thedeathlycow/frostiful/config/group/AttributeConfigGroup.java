package com.github.thedeathlycow.frostiful.config.group;

import com.google.common.collect.ImmutableList;
import com.oroarmor.config.ConfigItemGroup;
import com.oroarmor.config.DoubleConfigItem;
import com.oroarmor.config.IntegerConfigItem;

public class AttributeConfigGroup extends ConfigItemGroup {

    private static final String TRANSLATE_BASE_STRING = "config.frostiful.attribute_config.";

    public static final DoubleConfigItem PERCENT_FROST_REDUCTION_PER_FROST_RESISTANCE = new DoubleConfigItem("percent_frost_reduction_per_frost_resistance", 10.0D, TRANSLATE_BASE_STRING + "percent_frost_reduction_per_frost_resistance", null, 0.0D, 100.0D);
    public static final IntegerConfigItem MAX_FROST_MULTIPLIER = new IntegerConfigItem("max_frost_multiplier", 140, TRANSLATE_BASE_STRING + "max_frost_multiplier", null, 0, Integer.MAX_VALUE);

    public static class BaseFrostResistance extends ConfigItemGroup {

        public static final String TRANSLATE_BASE_STRING = AttributeConfigGroup.TRANSLATE_BASE_STRING + "base_frost_resistance.";

        public static final DoubleConfigItem BASE = new DoubleConfigItem("base", 0.0D, TRANSLATE_BASE_STRING + "base", null, 0.0D, Double.POSITIVE_INFINITY);
        public static final DoubleConfigItem PLAYER = new DoubleConfigItem("player", 0.0D, TRANSLATE_BASE_STRING + "player", null, 0.0D, Double.POSITIVE_INFINITY);

        /**
         * Creates a new {@link ConfigItemGroup} with the list of configs and the name
         */
        public BaseFrostResistance() {
            super(ImmutableList.of(BASE, PLAYER), "base_frost_resistance");
        }
    }

    public static class BaseMaxFrost extends ConfigItemGroup {

        public static final String TRANSLATE_BASE_STRING = AttributeConfigGroup.TRANSLATE_BASE_STRING + "base_max_frost.";

        public static final DoubleConfigItem BASE = new DoubleConfigItem("base", 40.0D, TRANSLATE_BASE_STRING + "base", null, 0.0D, Double.POSITIVE_INFINITY);
        public static final DoubleConfigItem PLAYER = new DoubleConfigItem("player", 45.0D, TRANSLATE_BASE_STRING + "player", null, 0.0D, Double.POSITIVE_INFINITY);

        /**
         * Creates a new {@link ConfigItemGroup} with the list of configs and the name
         */
        public BaseMaxFrost() {
            super(ImmutableList.of(BASE, PLAYER), "base_max_frost");
        }
    }

    /**
     * Creates a new {@link ConfigItemGroup}
     */
    public AttributeConfigGroup() {
        super(ImmutableList.of(PERCENT_FROST_REDUCTION_PER_FROST_RESISTANCE, MAX_FROST_MULTIPLIER, new BaseFrostResistance(), new BaseMaxFrost()), "attribute_config");
    }
}
