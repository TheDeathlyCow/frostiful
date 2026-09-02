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

package com.github.thedeathlycow.frostiful.compat;

import dev.yumi.mc.core.api.YumiMods;

public class FrostifulIntegrations {

    private FrostifulIntegrations() {}

    public static final String COLORFUL_HEARTS_ID = "colorfulhearts";

    public static final String OVERFLOWING_BARS_ID = "overflowingbars";

    public static final String TRINKETS_ID = "trinkets_updated";

    public static final String FABRIC_SEASONS_ID = "seasons";

    public static final String SCORCHFUL_ID = "scorchful";

    public static boolean isHeartsRenderOverridden() {
        return isModLoaded(COLORFUL_HEARTS_ID) || isModLoaded(OVERFLOWING_BARS_ID);
    }

    public static boolean isTrinketsLoaded() {
        return isModLoaded(TRINKETS_ID);
    }

    public static boolean isModLoaded(String id) {
        return YumiMods.get().isModLoaded(id);
    }
}
