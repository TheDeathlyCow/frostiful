package com.github.thedeathlycow.frostiful.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

@FunctionalInterface
public interface OverlayRenderCallback {

    void renderOverlay(GuiGraphics context, ResourceLocation texture, float opacity);

}