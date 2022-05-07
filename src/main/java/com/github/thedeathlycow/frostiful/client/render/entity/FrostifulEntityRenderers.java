package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.entity.FrostifulEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FrostifulEntityRenderers {

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(FrostifulEntityTypes.FROST_TIPPED_ARROW, FrostTippedArrowEntityRenderer::new);
    }

}
