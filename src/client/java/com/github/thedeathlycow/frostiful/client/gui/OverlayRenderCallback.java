package com.github.thedeathlycow.frostiful.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;

@FunctionalInterface
public interface OverlayRenderCallback {

    void renderOverlay(GuiGraphics context, Identifier texture, float opacity);

}