package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class FrostologerFrostFeatureRenderer extends RenderLayer<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {

    public FrostologerFrostFeatureRenderer(
            RenderLayerParent<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> context
    ) {
        super(context);
    }

    @Override
    public void render(
            PoseStack matrices,
            SubmitNodeCollector queue,
            int light,
            FrostologerEntityRenderState state,
            float limbAngle,
            float limbDistance
    ) {
        ResourceLocation texture = state.frostLayer.getTexture();
        if (texture != null) {
            RenderLayer.renderColoredCutoutModel(
                    this.getParentModel(),
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
