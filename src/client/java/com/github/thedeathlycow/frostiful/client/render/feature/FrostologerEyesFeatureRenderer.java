package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class FrostologerEyesFeatureRenderer<T extends FrostologerEntityRenderState, M extends FrostologerEntityModel<T>> extends RenderLayer<T, M> {
    private final RenderType skin;
    private final Identifier texture;

    public FrostologerEyesFeatureRenderer(RenderLayerParent<T, M> context, Identifier texture) {
        super(context);
        this.skin = RenderTypes.entityTranslucentEmissive(texture);
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
