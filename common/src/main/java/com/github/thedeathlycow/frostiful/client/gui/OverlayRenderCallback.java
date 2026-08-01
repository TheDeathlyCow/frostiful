package com.github.thedeathlycow.frostiful.client.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

@FunctionalInterface
public interface OverlayRenderCallback {

    void renderOverlay(GuiGraphicsExtractor extractor, Identifier texture, float opacity);

}