package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.entity.FEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class FEntityRenderers {

        public static void registerEntityRenderers() {
                EntityRendererRegistry.register(FEntityTypes.FROST_TIPPED_ARROW, FrostTippedArrowEntityRenderer::new);
                EntityRendererRegistry.register(FEntityTypes.FROST_SPELL, FrostSpellEntityRenderer::new);
                EntityRendererRegistry.register(FEntityTypes.FROSTOLOGER, FrostologerEntityRenderer::new);
                EntityRendererRegistry.register(FEntityTypes.CHILLAGER, ChillagerEntityRenderer::new);
                EntityRendererRegistry.register(FEntityTypes.BITER, BiterEntityRenderer::new);
                EntityRendererRegistry.register(FEntityTypes.PACKED_SNOWBALL, FlyingItemEntityRenderer::new);
                EntityRendererRegistry.register(FEntityTypes.THROWN_ICICLE, ThrownIcicleEntityRenderer::new);
                EntityRendererRegistry.register(FEntityTypes.FREEZING_WIND, EmptyEntityRenderer::new);
        }

}
