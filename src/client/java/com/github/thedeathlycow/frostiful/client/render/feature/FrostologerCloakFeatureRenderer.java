package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.FrostologerCapeModel;
import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentAssetManager;

@Environment(EnvType.CLIENT)
public class FrostologerCloakFeatureRenderer extends RenderLayer<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {
    private final FrostologerCapeModel<FrostologerEntityRenderState> model;
    private final EquipmentAssetManager equipmentModelLoader;

    public FrostologerCloakFeatureRenderer(
            RenderLayerParent<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> featureRendererContext,
            EntityModelSet modelLoader,
            EquipmentAssetManager equipmentModelLoader
    ) {
        super(featureRendererContext);
        this.equipmentModelLoader = equipmentModelLoader;
        this.model = new FrostologerCapeModel<>(modelLoader.bakeLayer(FEntityModelLayers.FROSTOLOGER_CAPE));
    }

    @Override
    public void submit(
            PoseStack matrixStack,
            SubmitNodeCollector queue,
            int light,
            FrostologerEntityRenderState state,
            float limbAngle,
            float limbDistance
    ) {
        if (!state.isInvisible && state.capeTexture != null) {
            matrixStack.pushPose();
            matrixStack.translate(0.0, 0.0, 3f / 16f);
            queue.submitModel(
                    this.model,
                    state,
                    matrixStack,
                    RenderType.entitySolid(state.capeTexture.texturePath()),
                    light,
                    OverlayTexture.NO_OVERLAY,
                    state.outlineColor,
                    null
            );

            matrixStack.popPose();
        }
    }
}
