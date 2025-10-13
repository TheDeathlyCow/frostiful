package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.render.entity.*;
import com.github.thedeathlycow.frostiful.client.render.feature.IceSkateFeatureRenderer;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.*;

@Environment(EnvType.CLIENT)
public class FEntityRenderers {

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful entity renderers");
        EntityRendererFactories.register(FEntityTypes.GLACIAL_ARROW, GlacialArrowEntityRenderer::new);
        EntityRendererFactories.register(FEntityTypes.FROST_SPELL, FrostSpellEntityRenderer::new);
        EntityRendererFactories.register(FEntityTypes.FROSTOLOGER, FrostologerEntityRenderer::new);
        EntityRendererFactories.register(FEntityTypes.CHILLAGER, ChillagerEntityRenderer::new);
        EntityRendererFactories.register(FEntityTypes.BITER, BiterEntityRenderer::new);
        EntityRendererFactories.register(FEntityTypes.PACKED_SNOWBALL, FlyingItemEntityRenderer::new);
        EntityRendererFactories.register(FEntityTypes.THROWN_ICICLE, ThrownIcicleEntityRenderer::new);
        EntityRendererFactories.register(FEntityTypes.FREEZING_WIND, EmptyEntityRenderer::new);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
                (entityType, entityRenderer, registrationHelper, context) -> {
                    if (entityRenderer instanceof BipedEntityRenderer<?, ?, ?> bipedEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        bipedEntityRenderer,
                                        context.getEntityModels()
                                )
                        );
                    } else if (entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        playerEntityRenderer,
                                        context.getEntityModels()
                                )
                        );
                    } else if (entityRenderer instanceof ArmorStandEntityRenderer armorStandEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        armorStandEntityRenderer,
                                        context.getEntityModels()
                                )
                        );
                    } else if (entityRenderer instanceof GiantEntityRenderer giantEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        giantEntityRenderer,
                                        context.getEntityModels()
                                )
                        );
                    }
                }
        );
    }

    private FEntityRenderers() {

    }
}
