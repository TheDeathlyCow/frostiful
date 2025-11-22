package com.github.thedeathlycow.frostiful.client.gui;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

@Environment(EnvType.CLIENT)
public final class FrostOverlayRenderer {

    private static final ResourceLocation POWDER_SNOW_OUTLINE = ResourceLocation.withDefaultNamespace("textures/misc/powder_snow_outline.png");

    private FrostOverlayRenderer() {
    }

    /**
     * Renders the frost overlay in this frame for the main client player
     *
     * @param player         The main client player to render the frost overlay for
     * @param renderCallback A callback that renders the frost overlay texture
     */
    public static void renderFrostOverlay(
            GuiGraphics context,
            LocalPlayer player,
            OverlayRenderCallback renderCallback
    ) {
        float freezeScale = player.thermoo$getTemperatureScale();
        if (freezeScale > 0) {
            return;
        }
        freezeScale = -freezeScale;


        FrostifulConfig config = Frostiful.getConfig();

        // disable frost overlay when wearing frostology cloak
        boolean isOverlayDisabled = config.clientConfig.isDisableFrostOverlayWhenWearingFrostologyCloak()
                && player.getItemBySlot(EquipmentSlot.CHEST).has(FDataComponentTypes.ICE_LIKE);

        if (isOverlayDisabled) {
            return;
        }

        float renderThreshold = config.clientConfig.getFrostOverlayStart();

        if (freezeScale >= renderThreshold) {
            // scale opacity to temp scale
            float opacity = renderThreshold >= 1.0f
                    ? 0.0f
                    : (freezeScale - renderThreshold) / (1.0f - renderThreshold);
            renderCallback.renderOverlay(context, POWDER_SNOW_OUTLINE, opacity);
        }
    }
}
