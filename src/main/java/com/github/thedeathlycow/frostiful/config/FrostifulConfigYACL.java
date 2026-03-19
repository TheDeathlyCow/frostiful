package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.config.section.ClientConfig;
import com.github.thedeathlycow.frostiful.config.section.CombatConfig;
import com.github.thedeathlycow.frostiful.config.section.EnvironmentConfig;
import com.github.thedeathlycow.frostiful.config.section.FreezingConfig;

public final class FrostifulConfigYACL {
    public static final String MAIN_CATEGORY_NAME = "main";

    public static ClientConfig clientConfig() {
        return ClientConfig.HANDLER.instance();
    }

    public static CombatConfig combatConfig() {
        return CombatConfig.HANDLER.instance();
    }

    public static EnvironmentConfig environmentConfig() {
        return EnvironmentConfig.HANDLER.instance();
    }

    public static FreezingConfig freezingConfig() {
        return FreezingConfig.HANDLER.instance();
    }

    public static void initialize() {
        ClientConfig.HANDLER.load();
        ClientConfig.HANDLER.save();

        CombatConfig.HANDLER.load();
        CombatConfig.HANDLER.save();

        EnvironmentConfig.HANDLER.load();
        EnvironmentConfig.HANDLER.save();

        FreezingConfig.HANDLER.load();
        FreezingConfig.HANDLER.save();
    }

    private FrostifulConfigYACL() {

    }
}