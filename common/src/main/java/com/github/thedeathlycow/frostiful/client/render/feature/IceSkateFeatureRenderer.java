/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.IceSkateModel;
import com.github.thedeathlycow.frostiful.client.render.state.FHumanoidRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class IceSkateFeatureRenderer<
        S extends HumanoidRenderState,
        M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    private final IceSkateModel<S> model;
    private final IceSkateModel<S> babyModel;

    private static final Identifier SKATE_TEXTURE = Frostiful.id("textures/entity/skates.png");

    public IceSkateFeatureRenderer(
            RenderLayerParent<S, M> context,
            EntityModelSet loader
    ) {
        super(context);
        this.model = new IceSkateModel<>(loader.bakeLayer(FEntityModelLayers.ICE_SKATES));
        this.babyModel = new IceSkateModel<>(loader.bakeLayer(FEntityModelLayers.ICE_SKATES_BABY));
    }

    @Override
    public void submit(
            PoseStack matrices,
            SubmitNodeCollector queue,
            int light,
            S state,
            float limbAngle,
            float limbDistance
    ) {
        if (((FHumanoidRenderState) state).frostiful$wearingIceSkates()) {
            IceSkateModel<S> model = state.isBaby ? this.babyModel : this.model;

            M contextModel = this.getParentModel();
            contextModel.root().translateAndRotate(matrices);

            queue.order(1)
                    .submitModel(
                            model,
                            state,
                            matrices,
                            RenderTypes.armorCutoutNoCull(SKATE_TEXTURE),
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
