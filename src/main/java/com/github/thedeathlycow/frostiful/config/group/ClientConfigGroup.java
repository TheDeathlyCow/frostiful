package com.github.thedeathlycow.frostiful.config.group;

import com.google.common.collect.ImmutableList;
import com.oroarmor.config.BooleanConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import com.oroarmor.config.DoubleConfigItem;

public class ClientConfigGroup extends ConfigItemGroup {

    private static final String TRANSLATE_BASE_STRING = "config.frostiful.client_config.";
    public static final DoubleConfigItem FROST_OVERLAY_START = new DoubleConfigItem("frost_overlay_start", 0.5, TRANSLATE_BASE_STRING + "frost_overlay_start", null, 0.0, 1.0);
    public static final BooleanConfigItem DO_COLD_HEART_OVERLAY = new BooleanConfigItem("do_cold_heart_overlay", true, TRANSLATE_BASE_STRING + "do_cold_heart_overlay");

    /**
     * Creates a new {@link ConfigItemGroup} with the list of configs and the name
     */
    public ClientConfigGroup() {
        super(ImmutableList.of(FROST_OVERLAY_START, DO_COLD_HEART_OVERLAY), "client_config");
    }
}
