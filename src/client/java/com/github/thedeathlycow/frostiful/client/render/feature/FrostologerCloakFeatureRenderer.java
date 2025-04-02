package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.equipment.EquipmentModelLoader;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class FrostologerCloakFeatureRenderer extends FeatureRenderer<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {
    private final FrostologerEntityModel<FrostologerEntityRenderState> model;
    private final EquipmentModelLoader equipmentModelLoader;

    public FrostologerCloakFeatureRenderer(
            FeatureRendererContext<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> featureRendererContext,
            LoadedEntityModels modelLoader,
            EquipmentModelLoader equipmentModelLoader
    ) {
        super(featureRendererContext);
        this.equipmentModelLoader = equipmentModelLoader;
        this.model = new FrostologerEntityModel<>(modelLoader.getModelPart(FEntityModelLayers.FROSTOLOGER));
    }

    @Override
    public void render(
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int light,
            FrostologerEntityRenderState state,
            float limbAngle,
            float limbDistance
    ) {
        if (!state.invisible && state.capeTexture != null) {
            matrixStack.push();
            matrixStack.translate(0.0, 0.0, 3f / 16f);

            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(
                    RenderLayer.getEntitySolid(state.capeTexture)
            );
            this.model.setAngles(state);
            this.model.renderCloak(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

            matrixStack.pop();
        }
    }
}
