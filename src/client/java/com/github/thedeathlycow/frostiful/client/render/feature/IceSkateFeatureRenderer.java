package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.IceSkateModel;
import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class IceSkateFeatureRenderer<
        S extends BipedEntityRenderState,
        M extends BipedEntityModel<S>> extends FeatureRenderer<S, M> {
    private final IceSkateModel<S> model;
    private final IceSkateModel<S> babyModel;

    private static final Identifier SKATE_TEXTURE = Frostiful.id("textures/entity/skates.png");

    public IceSkateFeatureRenderer(
            FeatureRendererContext<S, M> context,
            LoadedEntityModels loader
    ) {
        super(context);
        this.model = new IceSkateModel<>(loader.getModelPart(FEntityModelLayers.ICE_SKATES));
        this.babyModel = new IceSkateModel<>(loader.getModelPart(FEntityModelLayers.ICE_SKATES_BABY));
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
            IceSkateModel<S> model = state.baby ? this.babyModel : this.model;

            M contextModel = this.getContextModel();
            contextModel.getRootPart().applyTransform(matrices);

            queue.getBatchingQueue(1)
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
