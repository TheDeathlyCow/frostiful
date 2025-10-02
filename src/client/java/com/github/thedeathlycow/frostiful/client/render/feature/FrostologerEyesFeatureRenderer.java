package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostologerEyesFeatureRenderer<T extends FrostologerEntityRenderState, M extends FrostologerEntityModel<T>> extends FeatureRenderer<T, M> {
    private final RenderLayer skin;

    public FrostologerEyesFeatureRenderer(FeatureRendererContext<T, M> context, Identifier id) {
        super(context);
        this.skin = RenderLayer.getEntityTranslucentEmissive(id);
    }

    @Override
    public void render(
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            int light,
            T state,
            float limbAngle,
            float limbDistance
    ) {
        if (state.glowingEyes) {
            queue.submitCustom(matrices, skin, (matricesEntry, vertexConsumer) -> {
                this.getContextModel()
                        .render(
                                matrices,
                                vertexConsumer,
                                0x00F000F0,
                                OverlayTexture.DEFAULT_UV
                        );
            });
        }
    }
}
