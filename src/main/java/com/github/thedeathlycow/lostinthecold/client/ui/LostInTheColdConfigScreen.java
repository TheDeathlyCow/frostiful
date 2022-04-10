package com.github.thedeathlycow.lostinthecold.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.text.TranslatableText;

public class LostInTheColdConfigScreen extends GameOptionsScreen {

    public LostInTheColdConfigScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, new TranslatableText("screen.lost-in-the-cold.config"));
    }

}
