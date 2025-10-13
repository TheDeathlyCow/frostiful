package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.FrostologerCapeModel;
import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.equipment.EquipmentModelLoader;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class FrostologerCloakFeatureRenderer extends FeatureRenderer<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {
    private final FrostologerCapeModel<FrostologerEntityRenderState> model;
    private final EquipmentModelLoader equipmentModelLoader;

    public FrostologerCloakFeatureRenderer(
            FeatureRendererContext<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> featureRendererContext,
            LoadedEntityModels modelLoader,
            EquipmentModelLoader equipmentModelLoader
    ) {
        super(featureRendererContext);
        this.equipmentModelLoader = equipmentModelLoader;
        this.model = new FrostologerCapeModel<>(modelLoader.getModelPart(FEntityModelLayers.FROSTOLOGER_CAPE));
    }

    @Override
    public void render(
            MatrixStack matrixStack,
            OrderedRenderCommandQueue queue,
            int light,
            FrostologerEntityRenderState state,
            float limbAngle,
            float limbDistance
    ) {
        if (!state.invisible && state.capeTexture != null) {
            matrixStack.push();
            matrixStack.translate(0.0, 0.0, 3f / 16f);
            queue.submitModel(
                    this.model,
                    state,
                    matrixStack,
                    RenderLayer.getEntitySolid(state.capeTexture.texturePath()),
                    light,
                    OverlayTexture.DEFAULT_UV,
                    state.outlineColor,
                    null
            );

            matrixStack.pop();
        }
    }
}
