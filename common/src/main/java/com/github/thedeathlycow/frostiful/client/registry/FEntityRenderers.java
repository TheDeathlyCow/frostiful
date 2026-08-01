package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.render.entity.*;
import com.github.thedeathlycow.frostiful.client.render.feature.IceSkateFeatureRenderer;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;


public class FEntityRenderers {

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful entity renderers");
        EntityRenderers.register(FEntityTypes.GLACIAL_ARROW, GlacialArrowEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.FROST_SPELL, FrostSpellEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.FROSTOLOGER, FrostologerEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.CHILLAGER, ChillagerEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.BITER, BiterEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.PACKED_SNOWBALL, ThrownItemRenderer::new);
        EntityRenderers.register(FEntityTypes.THROWN_ICICLE, ThrownIcicleEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.FREEZING_WIND, NoopRenderer::new);

        LivingEntityRenderLayerRegistrationCallback.EVENT.register(
                (entityType, entityRenderer, registrationHelper, context) -> {
                    if (entityRenderer instanceof HumanoidMobRenderer<?, ?, ?> bipedEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        bipedEntityRenderer,
                                        context.getModelSet()
                                )
                        );
                    } else if (entityRenderer instanceof AvatarRenderer<?> playerEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        playerEntityRenderer,
                                        context.getModelSet()
                                )
                        );
                    } else if (entityRenderer instanceof ArmorStandRenderer armorStandEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        armorStandEntityRenderer,
                                        context.getModelSet()
                                )
                        );
                    } else if (entityRenderer instanceof GiantMobRenderer giantEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        giantEntityRenderer,
                                        context.getModelSet()
                                )
                        );
                    }
                }
        );
    }

    private FEntityRenderers() {

    }
}
