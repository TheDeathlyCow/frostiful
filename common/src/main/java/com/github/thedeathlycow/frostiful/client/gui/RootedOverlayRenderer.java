package com.github.thedeathlycow.frostiful.client.gui;

import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.frostiful.survival.system.FrostRootSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

public class RootedOverlayRenderer {
    private static final Identifier FROSTIFUL_ROOTED_OVERLAY = Identifier.withDefaultNamespace("textures/block/ice.png");

    public static void render(
            LivingEntity entity,
            GuiGraphicsExtractor extractor,
            DeltaTracker tickCounter,
            OverlayRenderCallback callback
    ) {
        if (entity.hasAttached(FDataAttachments.FROST_WAND_ROOT_TICKS)) {
            int rootTicksRemaining = entity.getAttachedOrThrow(FDataAttachments.FROST_WAND_ROOT_TICKS);

            if (rootTicksRemaining > 0) {
                callback.renderOverlay(extractor, FROSTIFUL_ROOTED_OVERLAY, FrostRootSystem.getProgress(rootTicksRemaining));
            }
        }
    }

    private RootedOverlayRenderer() {
    }

}
