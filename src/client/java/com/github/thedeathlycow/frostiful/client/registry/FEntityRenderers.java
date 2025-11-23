package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.model.IceSkateModel;
import com.github.thedeathlycow.frostiful.client.render.entity.*;
import com.github.thedeathlycow.frostiful.client.render.feature.IceSkateFeatureRenderer;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

@Environment(EnvType.CLIENT)
public class FEntityRenderers {

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful entity renderers");
        EntityRendererRegistry.register(FEntityTypes.GLACIAL_ARROW, GlacialArrowEntityRenderer::new);
        EntityRendererRegistry.register(FEntityTypes.FROST_SPELL, FrostSpellEntityRenderer::new);
        EntityRendererRegistry.register(FEntityTypes.FROSTOLOGER, FrostologerEntityRenderer::new);
        EntityRendererRegistry.register(FEntityTypes.CHILLAGER, ChillagerEntityRenderer::new);
        EntityRendererRegistry.register(FEntityTypes.BITER, BiterEntityRenderer::new);
        EntityRendererRegistry.register(FEntityTypes.PACKED_SNOWBALL, ThrownItemRenderer::new);
        EntityRendererRegistry.register(FEntityTypes.THROWN_ICICLE, ThrownIcicleEntityRenderer::new);
        EntityRendererRegistry.register(FEntityTypes.FREEZING_WIND, NoopRenderer::new);


        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
                (entityType, entityRenderer, registrationHelper, context) -> {
                    if (entityRenderer instanceof HumanoidMobRenderer<?, ?> bipedEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        bipedEntityRenderer,
                                        new IceSkateModel<>(context.bakeLayer(FEntityModelLayers.ICE_SKATES))
                                )
                        );
                    } else if (entityRenderer instanceof PlayerRenderer playerEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        playerEntityRenderer,
                                        new IceSkateModel<>(context.bakeLayer(FEntityModelLayers.ICE_SKATES))
                                )
                        );
                    } else if (entityRenderer instanceof ArmorStandRenderer armorStandEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        armorStandEntityRenderer,
                                        new IceSkateModel<>(context.bakeLayer(FEntityModelLayers.ICE_SKATES))
                                )
                        );
                    } else if (entityRenderer instanceof GiantMobRenderer giantEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        giantEntityRenderer,
                                        new IceSkateModel<>(context.bakeLayer(FEntityModelLayers.ICE_SKATES))
                                )
                        );
                    }
                }
        );
    }

    private FEntityRenderers() {

    }
}
