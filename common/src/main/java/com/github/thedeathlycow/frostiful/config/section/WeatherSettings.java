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

package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import com.github.thedeathlycow.frostiful.survival.wind.WindSpawnMethod;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;

public class WeatherSettings {
    public static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;
    public static final String ICICLE_GROUP = "icicle";
    public static final String WIND_GROUP = "wind";

    @AutoGen(category = CATEGORY, group = WIND_GROUP)
    @Translate.Name("Freezing Wind spawning method")
    @SerialEntry(comment = "Sets how freezing winds should spawn. Points create small one-off explosions of wind, entity creates an entity that rolls across the landscape, and none disables the feature entirely.")
    @EnumCycler
    WindSpawnMethod freezingWindSpawningMethod = WindSpawnMethod.POINT;

    @AutoGen(category = CATEGORY, group = WIND_GROUP)
    @Translate.Name("Enable freezing wind in the air")
    @SerialEntry(comment = "When enabled, allows freezing winds to appear high up in the air.")
    @TickBox
    boolean enableWindInTheAir = true;

    @AutoGen(category = CATEGORY, group = WIND_GROUP)
    @Translate.Name("Wind destroys torches")
    @SerialEntry(comment = "When enabled, freezing winds will destroy exposed fire blocks (includes torches!).")
    @TickBox
    boolean freezingWindDestroysExposedFire = true;

    public WindSpawnMethod freezingWindSpawningMethod() {
        return freezingWindSpawningMethod;
    }

    public boolean enableWindInTheAir() {
        return enableWindInTheAir;
    }

    public boolean freezingWindDestroysExposedFire() {
        return freezingWindDestroysExposedFire;
    }

    @AutoGen(category = CATEGORY, group = ICICLE_GROUP)
    @Translate.Name("Enable icicle formation in weather")
    @SerialEntry(comment = "When enabled, icicles will form on the underside of full-face blocks near the surface of the world during weather.")
    @TickBox
    boolean iciclesFormInWeather = true;

    public boolean iciclesFormInWeather() {
        return iciclesFormInWeather;
    }
}