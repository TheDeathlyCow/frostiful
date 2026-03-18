package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.config.section.ClientConfig;
import com.github.thedeathlycow.frostiful.config.section.CombatConfig;

public final class FrostifulConfigYACL {
    public static final String MAIN_CATEGORY_NAME = "main";

    public static ClientConfig clientConfig() {
        return ClientConfig.HANDLER.instance();
    }

    public static CombatConfig combatConfig() {
        return CombatConfig.HANDLER.instance();
    }

    public static void initialize() {
        ClientConfig.HANDLER.load();
        ClientConfig.HANDLER.save();

        CombatConfig.HANDLER.load();
        CombatConfig.HANDLER.save();
    }

    private FrostifulConfigYACL() {

    }
}