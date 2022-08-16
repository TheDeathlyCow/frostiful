package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.entity.FrostifulEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class FrostifulEntityRenderers {

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(FrostifulEntityTypes.FROST_TIPPED_ARROW, FrostTippedArrowEntityRenderer::new);
        EntityRendererRegistry.register(FrostifulEntityTypes.FROST_SPELL, FrostSpellEntityRenderer::new);
    }

}
