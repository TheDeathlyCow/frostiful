package com.github.thedeathlycow.frostiful.client.gui;

import com.github.thedeathlycow.frostiful.entity.attachment.FrostWandRootComponent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class RootedOverlayRenderer {
    private static final ResourceLocation FROSTIFUL_ROOTED_OVERLAY = ResourceLocation.withDefaultNamespace("textures/block/ice.png");

    public static void render(
            LivingEntity entity,
            GuiGraphics context,
            DeltaTracker tickCounter,
            OverlayRenderCallback callback
    ) {
        FrostWandRootComponent component = FrostWandRootComponent.get(entity);
        if (component.isRooted()) {
            callback.renderOverlay(context, FROSTIFUL_ROOTED_OVERLAY, component.getRootProgress());
        }
    }

    private RootedOverlayRenderer() {
    }

}
