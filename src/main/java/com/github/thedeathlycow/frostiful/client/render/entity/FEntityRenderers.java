package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.model.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.model.IceSkateModel;
import com.github.thedeathlycow.frostiful.client.render.feature.IceSkateFeatureRenderer;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

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


        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
                (entityType, entityRenderer, registrationHelper, context) -> {
                    if (entityRenderer instanceof BipedEntityRenderer<?, ?> bipedEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        bipedEntityRenderer,
                                        new IceSkateModel<>(context.getPart(FEntityModelLayers.ICE_SKATES))
                                )
                        );
                    } else if (entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        playerEntityRenderer,
                                        new IceSkateModel<>(context.getPart(FEntityModelLayers.ICE_SKATES))
                                )
                        );
                    } else if (entityRenderer instanceof ArmorStandEntityRenderer armorStandEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        armorStandEntityRenderer,
                                        new IceSkateModel<>(context.getPart(FEntityModelLayers.ICE_SKATES))
                                )
                        );
                    } else if (entityRenderer instanceof GiantEntityRenderer giantEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        giantEntityRenderer,
                                        new IceSkateModel<>(context.getPart(FEntityModelLayers.ICE_SKATES))
                                )
                        );
                    }
                }
        );
    }

}
