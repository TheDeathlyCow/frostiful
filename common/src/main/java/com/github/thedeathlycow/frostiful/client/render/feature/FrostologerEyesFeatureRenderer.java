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

import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

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
