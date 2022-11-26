package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.entity.FEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class FEntityRenderers {

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(FEntityTypes.FROST_TIPPED_ARROW, FrostTippedArrowEntityRenderer::new);
        EntityRendererRegistry.register(FEntityTypes.FROST_SPELL, FrostSpellEntityRenderer::new);
    }

}
