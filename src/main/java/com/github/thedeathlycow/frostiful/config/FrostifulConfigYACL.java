package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.config.section.*;

public final class FrostifulConfigYACL {
    public static final String MAIN_CATEGORY_NAME = "main";

    public static CombatConfig combatConfig() {
        return CombatConfig.HANDLER.instance();
    }

    public static EnvironmentConfig environmentConfig() {
        return EnvironmentConfig.HANDLER.instance();
    }

    public static FreezingConfig freezingConfig() {
        return FreezingConfig.HANDLER.instance();
    }

    public static IcicleConfig icicleConfig() {
        return IcicleConfig.HANDLER.instance();
    }

    public static BlockSettings blockSettings() {
        return BlockSettings.HANDLER.instance();
    }

    public static EntitySettings entitySettings() {
        return EntitySettings.HANDLER.instance();
    }

    public static ItemSettings itemSettings() {
        return ItemSettings.HANDLER.instance();
    }

    public static WeatherSettings weatherSettings() {
        return WeatherSettings.HANDLER.instance();
    }

    public static void initialize() {
        CombatConfig.HANDLER.load();
        CombatConfig.HANDLER.save();

        EnvironmentConfig.HANDLER.load();
        EnvironmentConfig.HANDLER.save();

        FreezingConfig.HANDLER.load();
        FreezingConfig.HANDLER.save();

        IcicleConfig.HANDLER.load();
        IcicleConfig.HANDLER.save();

        BlockSettings.HANDLER.load();
        BlockSettings.HANDLER.save();

        EntitySettings.HANDLER.load();
        EntitySettings.HANDLER.save();

        ItemSettings.HANDLER.load();
        ItemSettings.HANDLER.save();

        WeatherSettings.HANDLER.load();
        WeatherSettings.HANDLER.save();
    }

    private FrostifulConfigYACL() {

    }
}