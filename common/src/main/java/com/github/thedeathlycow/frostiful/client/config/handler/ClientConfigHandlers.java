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

package com.github.thedeathlycow.frostiful.client.config.handler;

import com.github.thedeathlycow.frostiful.client.config.section.AccessibilitySettings;
import com.github.thedeathlycow.frostiful.client.config.section.DisplaySettings;
import com.github.thedeathlycow.frostiful.config.handler.ConfigHandlers;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;

public final class ClientConfigHandlers {
    public static final ConfigClassHandler<AccessibilitySettings> ACCESSIBILITY = ConfigHandlers.createHandler(
            AccessibilitySettings.class,
            "client/accessibility",
            ClientConfigPaths.ACCESSIBILITY
    );

    public static final ConfigClassHandler<DisplaySettings> DISPLAY = ConfigHandlers.createHandler(
            DisplaySettings.class,
            "client/display",
            ClientConfigPaths.DISPLAY
    );

    private ClientConfigHandlers() {

    }
}