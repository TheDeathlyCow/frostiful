package com.github.thedeathlycow.frostiful.client.gui;

import com.github.thedeathlycow.frostiful.client.config.FrostifulClientConfig;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;


public final class FrostOverlayRenderer {

    private static final Identifier POWDER_SNOW_OUTLINE = Identifier.withDefaultNamespace("textures/misc/powder_snow_outline.png");

    private FrostOverlayRenderer() {
    }

    /**
     * Renders the frost overlay in this frame for the main client player
     *
     * @param player         The main client player to render the frost overlay for
     * @param renderCallback A callback that renders the frost overlay texture
     */
    public static void renderFrostOverlay(
            GuiGraphicsExtractor context,
            LocalPlayer player,
            OverlayRenderCallback renderCallback
    ) {
        float temperatureScale = player.thermoo$getTemperatureScale();
        if (temperatureScale > 0) {
            return;
        }

        // disable frost overlay when wearing frostology cloak
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(FItemTags.CHILLAGER_LORD_CLOAK)) {
            return;
        }

        float renderThreshold = FrostifulClientConfig.displaySettings().frostOverlayStart();

        if (temperatureScale <= renderThreshold) {
            // scale opacity to temp scale
            float opacity = Mth.clamp((temperatureScale + renderThreshold + 1) / renderThreshold, 0f, 1f);
            renderCallback.renderOverlay(context, POWDER_SNOW_OUTLINE, opacity);
        }
    }
}
