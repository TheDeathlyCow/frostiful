package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.render.model.IceSkateModel;
import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.model.BakedSimpleModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class IceSkateFeatureRenderer<
        S extends BipedEntityRenderState,
        M extends BipedEntityModel<S>,
        I extends IceSkateModel<S>
        > extends FeatureRenderer<S, M> {
    private final I model;

    private static final Identifier SKATE_TEXTURE = Frostiful.id("textures/entity/skates.png");

    public IceSkateFeatureRenderer(
            FeatureRendererContext<S, M> context,
            I model
    ) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            int light,
            S state,
            float limbAngle,
            float limbDistance
    ) {
        if (((FBipedRenderState) state).frostiful$wearingIceSkates()) {
            this.getContextModel().applyTransform(matrices);

            queue.getBatchingQueue(0)
                    .submitModel(
                            model,
                            state,
                            matrices,
                            RenderLayer.getArmorCutoutNoCull(SKATE_TEXTURE),
                            light,
                            LivingEntityRenderer.getOverlay(state, 0.0f),
                            -1,
                            null,
                            state.outlineColor,
                            null
                    );
        }
    }
}
