package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.IceSkateModel;
import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

public class IceSkateFeatureRenderer<
        S extends HumanoidRenderState,
        M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    private final IceSkateModel<S> model;
    private final IceSkateModel<S> babyModel;

    private static final ResourceLocation SKATE_TEXTURE = Frostiful.id("textures/entity/skates.png");

    public IceSkateFeatureRenderer(
            RenderLayerParent<S, M> context,
            EntityModelSet loader
    ) {
        super(context);
        this.model = new IceSkateModel<>(loader.bakeLayer(FEntityModelLayers.ICE_SKATES));
        this.babyModel = new IceSkateModel<>(loader.bakeLayer(FEntityModelLayers.ICE_SKATES_BABY));
    }

    @Override
    public void render(
            PoseStack matrices,
            SubmitNodeCollector queue,
            int light,
            S state,
            float limbAngle,
            float limbDistance
    ) {
        if (((FBipedRenderState) state).frostiful$wearingIceSkates()) {
            IceSkateModel<S> model = state.isBaby ? this.babyModel : this.model;

            M contextModel = this.getParentModel();
            contextModel.root().translateAndRotate(matrices);

            queue.order(1)
                    .submitModel(
                            model,
                            state,
                            matrices,
                            RenderType.armorCutoutNoCull(SKATE_TEXTURE),
                            light,
                            LivingEntityRenderer.getOverlayCoords(state, 0.0f),
                            -1,
                            null,
                            state.outlineColor,
                            null
                    );
        }
    }
}
