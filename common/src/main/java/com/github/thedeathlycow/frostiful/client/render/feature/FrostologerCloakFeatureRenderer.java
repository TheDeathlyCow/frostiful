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

import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.FrostologerCapeModel;
import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentAssetManager;

public class FrostologerCloakFeatureRenderer extends RenderLayer<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {
    private final FrostologerCapeModel<FrostologerEntityRenderState> model;
    private final EquipmentAssetManager equipmentModelLoader;

    public FrostologerCloakFeatureRenderer(
            RenderLayerParent<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> featureRendererContext,
            EntityModelSet modelLoader,
            EquipmentAssetManager equipmentModelLoader
    ) {
        super(featureRendererContext);
        this.equipmentModelLoader = equipmentModelLoader;
        this.model = new FrostologerCapeModel<>(modelLoader.bakeLayer(FEntityModelLayers.FROSTOLOGER_CAPE));
    }

    @Override
    public void submit(
            PoseStack matrixStack,
            SubmitNodeCollector queue,
            int light,
            FrostologerEntityRenderState state,
            float limbAngle,
            float limbDistance
    ) {
        if (!state.isInvisible && state.capeTexture != null) {
            matrixStack.pushPose();
            matrixStack.translate(0.0, 0.0, 3f / 16f);
            queue.submitModel(
                    this.model,
                    state,
                    matrixStack,
                    RenderTypes.entitySolid(state.capeTexture.texturePath()),
                    light,
                    OverlayTexture.NO_OVERLAY,
                    state.outlineColor,
                    null
            );

            matrixStack.popPose();
        }
    }
}
