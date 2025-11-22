package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class FrostologerEyesFeatureRenderer<T extends FrostologerEntityRenderState, M extends FrostologerEntityModel<T>> extends RenderLayer<T, M> {
    private final RenderType skin;
    private final ResourceLocation texture;

    public FrostologerEyesFeatureRenderer(RenderLayerParent<T, M> context, ResourceLocation texture) {
        super(context);
        this.skin = RenderType.entityTranslucentEmissive(texture);
        this.texture = texture;
    }

    @Override
    public void submit(
            PoseStack matrices,
            SubmitNodeCollector queue,
            int light,
            T state,
            float limbAngle,
            float limbDistance
    ) {
        if (state.glowingEyes) {
            renderColoredCutoutModel(this.getParentModel(), texture, matrices, queue, 0x00F000F0, state, -1, 1);
        }
    }
}
