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

package com.github.thedeathlycow.frostiful.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.net.URI;

public class TextStyles {

    public static final Style GAME_RULE_TITLE = Style.EMPTY
            .withBold(true)
            .withColor(TextColor.fromLegacyFormat(ChatFormatting.AQUA))
            .withClickEvent(new ClickEvent.OpenUrl(URI.create("https://modded.wiki/w/Mod:Frostiful")));

    public static final Style INERT_FROSTOLOGY_CLOAK_TOOLTIP = Style.EMPTY
            .withItalic(false)
            .withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY));

    public static final Style FROSTOLOGY_CLOAK_TOOLTIP = Style.EMPTY
            .withItalic(true)
            .withColor(TextColor.fromLegacyFormat(ChatFormatting.BLUE));

    public static final Style WARMING_TOOLTIP = Style.EMPTY
            .withItalic(false)
            .withColor(TextColor.fromLegacyFormat(ChatFormatting.GOLD));

    private TextStyles() {

    }
}
