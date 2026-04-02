package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.config.section.*;

public final class FrostifulConfigYACL {
    public static final String MAIN_CATEGORY_NAME = "main";

    public static TemperatureSourceSettings temperatureSourceSettings() {
        return TemperatureSourceSettings.HANDLER.instance();
    }

    public static EnvironmentSettings environmentSettings() {
        return EnvironmentSettings.HANDLER.instance();
    }

    public static SoakingSettings soakingSettings() {
        return SoakingSettings.HANDLER.instance();
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
        TemperatureSourceSettings.HANDLER.load();
        TemperatureSourceSettings.HANDLER.save();

        EnvironmentSettings.HANDLER.load();
        EnvironmentSettings.HANDLER.save();

        SoakingSettings.HANDLER.load();
        SoakingSettings.HANDLER.save();

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