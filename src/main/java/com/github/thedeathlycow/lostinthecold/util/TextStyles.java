package com.github.thedeathlycow.lostinthecold.util;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;

public class TextStyles {

    public static final Style GAME_RULE_TITLE = Style.EMPTY
            .withBold(true)
            .withColor(TextColor.parse("aqua"))
            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.github.com/TheDeathlyCow/lost-in-the-cold"));

}
