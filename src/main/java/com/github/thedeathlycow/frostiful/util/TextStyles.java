package com.github.thedeathlycow.frostiful.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public class TextStyles {

    public static final Style GAME_RULE_TITLE = Style.EMPTY
            .withBold(true)
            .withColor(TextColor.fromLegacyFormat(ChatFormatting.AQUA))
            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.github.com/TheDeathlyCow/lost-in-the-cold"));

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
