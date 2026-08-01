package com.github.thedeathlycow.frostiful.client.gui;

import com.github.thedeathlycow.frostiful.entity.attachment.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.registry.FCardinalComponents;
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
        FrostWandRootComponent component = FCardinalComponents.FROST_WAND_ROOT_COMPONENT.get(entity);
        if (component.isRooted()) {
            callback.renderOverlay(extractor, FROSTIFUL_ROOTED_OVERLAY, component.getRootProgress());
        }
    }

    private RootedOverlayRenderer() {
    }

}
