package com.github.thedeathlycow.frostiful.client.gui;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.ClientConfig;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;

@Environment(EnvType.CLIENT)
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

        ClientConfig config = FrostifulConfigYACL.clientConfig();

        // disable frost overlay when wearing frostology cloak
        boolean isOverlayDisabled = config.isDisableFrostOverlayWhenWearingFrostologyCloak()
                && player.getItemBySlot(EquipmentSlot.CHEST).has(FDataComponentTypes.ICE_LIKE);

        if (isOverlayDisabled) {
            return;
        }

        float renderThreshold = config.getFrostOverlayStart();

        if (temperatureScale <= renderThreshold) {
            // scale opacity to temp scale
            float opacity = Mth.clamp((temperatureScale + renderThreshold + 1) / renderThreshold, 0f, 1f);
            renderCallback.renderOverlay(context, POWDER_SNOW_OUTLINE, opacity);
        }
    }
}
