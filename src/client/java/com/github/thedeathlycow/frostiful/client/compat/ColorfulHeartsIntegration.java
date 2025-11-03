package com.github.thedeathlycow.frostiful.client.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import terrails.colorfulhearts.api.fabric.ColorfulHeartsApi;
import terrails.colorfulhearts.api.fabric.event.FabHeartEvents;
import terrails.colorfulhearts.api.heart.Hearts;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

public class ColorfulHeartsIntegration implements ColorfulHeartsApi {

    public ColorfulHeartsIntegration() {
        OverlayHeart frozenHearts = Hearts.OVERLAY_HEARTS.get(ResourceLocation.withDefaultNamespace("frozen"));
        if (frozenHearts != null) {
            FabHeartEvents.PRE_RENDER.register(event -> {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null && player.thermoo$getTemperatureScale() <= -0.99) {
                    event.setOverlayHeart(frozenHearts);
                }
            });
        }
    }
}
