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
        Updater.run();

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