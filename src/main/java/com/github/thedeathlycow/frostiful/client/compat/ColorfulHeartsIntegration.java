package com.github.thedeathlycow.frostiful.client.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import terrails.colorfulhearts.api.heart.Hearts;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;
import terrails.colorfulhearts.api.neoforge.event.NeoHeartRenderEvent;

public class ColorfulHeartsIntegration {
    public static void initialize(IEventBus modBus) {
        modBus.addListener(ColorfulHeartsIntegration::preRender);
    }

    private static void preRender(NeoHeartRenderEvent.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.thermoo$getTemperatureScale() <= -0.99) {
            OverlayHeart frozenHearts = Hearts.OVERLAY_HEARTS.get(ResourceLocation.withDefaultNamespace("frozen"));

            if (frozenHearts != null) {
                event.setOverlayHeart(frozenHearts);
            }
        }
    }

    private ColorfulHeartsIntegration() {
    }
}
