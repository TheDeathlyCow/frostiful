package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class FrostologerFrostFeatureRenderer extends RenderLayer<FrostologerEntity, FrostologerEntityModel<FrostologerEntity>> {

    public FrostologerFrostFeatureRenderer(
            RenderLayerParent<FrostologerEntity, FrostologerEntityModel<FrostologerEntity>> context
    ) {
        super(context);
    }

    @Override
    public void render(
            PoseStack matrices,
            MultiBufferSource vertexConsumers,
            int light,
            FrostologerEntity frostologer,
            float limbAngle, float limbDistance,
            float tickDelta, float animationProgress,
            float headYaw, float headPitch
    ) {
        if (frostologer.isInvisible()) {
            return;
        }

        FrostLayers layer = FrostLayers.fromFrostologer(frostologer);

        if (layer == FrostLayers.NONE) {
            return;
        }

        ResourceLocation identifier = layer.getTexture();
        RenderLayer.renderColoredCutoutModel(
                this.getParentModel(),
                identifier,
                matrices,
                vertexConsumers,
                light,
                frostologer,
                -1
        );
    }
}
