/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.config.handler.ConfigHandlers;
import com.github.thedeathlycow.frostiful.config.section.*;

public final class FrostifulConfigYACL {
    public static final String MAIN_CATEGORY_NAME = "main";

    public static TemperatureSourceSettings temperatureSourceSettings() {
        return ConfigHandlers.TEMPERATURE_SOURCE.instance();
    }

    public static EnvironmentSettings environmentSettings() {
        return ConfigHandlers.ENVIRONMENT.instance();
    }

    public static SoakingSettings soakingSettings() {
        return ConfigHandlers.SOAKING.instance();
    }

    public static BlockSettings blockSettings() {
        return ConfigHandlers.BLOCK.instance();
    }

    public static EntitySettings entitySettings() {
        return ConfigHandlers.ENTITY.instance();
    }

    public static ItemSettings itemSettings() {
        return ConfigHandlers.ITEM.instance();
    }

    public static WeatherSettings weatherSettings() {
        return ConfigHandlers.WEATHER.instance();
    }

    public static void initialize() {
        Updater.run();

        ConfigHandlers.TEMPERATURE_SOURCE.load();
        ConfigHandlers.TEMPERATURE_SOURCE.save();

        ConfigHandlers.ENVIRONMENT.load();
        ConfigHandlers.ENVIRONMENT.save();

        ConfigHandlers.SOAKING.load();
        ConfigHandlers.SOAKING.save();

        ConfigHandlers.BLOCK.load();
        ConfigHandlers.BLOCK.save();

        ConfigHandlers.ENTITY.load();
        ConfigHandlers.ENTITY.save();

        ConfigHandlers.ITEM.load();
        ConfigHandlers.ITEM.save();

        ConfigHandlers.WEATHER.load();
        ConfigHandlers.WEATHER.save();
    }

    private FrostifulConfigYACL() {

    }
}