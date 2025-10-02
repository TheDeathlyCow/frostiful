package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.IronGolemCrackFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostologerFrostFeatureRenderer extends FeatureRenderer<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {

    public FrostologerFrostFeatureRenderer(
            FeatureRendererContext<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> context
    ) {
        super(context);
    }

    @Override
    public void render(
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            int light,
            FrostologerEntityRenderState state,
            float limbAngle,
            float limbDistance
    ) {
        Identifier texture = state.frostLayer.getTexture();
        if (texture != null) {
            FeatureRenderer.renderModel(
                    this.getContextModel(),
                    texture,
                    matrices,
                    queue,
                    light,
                    state,
                    -1,
                    1
            );
        }
    }
}
