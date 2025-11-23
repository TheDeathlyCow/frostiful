package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class FrostologerEyesFeatureRenderer<T extends FrostologerEntity, M extends FrostologerEntityModel<T>> extends RenderLayer<T, M> {

    private final RenderType skin;

    public FrostologerEyesFeatureRenderer(RenderLayerParent<T, M> context, ResourceLocation id) {
        super(context);
        this.skin = RenderType.entityTranslucentEmissive(id);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.isAtMaxPower()) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.skin);
            this.getParentModel()
                    .render(
                            matrices,
                            vertexConsumer,
                            0x00F000F0,
                            OverlayTexture.NO_OVERLAY
                    );
        }
    }

}
