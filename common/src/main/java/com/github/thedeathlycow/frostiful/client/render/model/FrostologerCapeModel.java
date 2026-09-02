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

package com.github.thedeathlycow.frostiful.client.render.model;

import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class FrostologerCapeModel<F extends FrostologerEntityRenderState> extends IllagerModel<F> {
    public static final String CLOAK_NAME = "cloak";

    private final ModelPart cloak;

    public FrostologerCapeModel(ModelPart root) {
        super(root);
        this.cloak = root.getChild(PartNames.BODY).getChild(CLOAK_NAME);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = FrostologerEntityModel.getModelData();
        PartDefinition modelPartData = modelData.getRoot().clearRecursively();
        PartDefinition root = modelPartData.getChild(PartNames.BODY);

        root.addOrReplaceChild(CLOAK_NAME, CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, CubeDeformation.NONE, 1.0F, 0.5F), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(F state) {
        super.setupAnim(state);
        if (state.capeTexture != null) {
            this.cloak.rotateBy(
                    new Quaternionf()
                            .rotateX((6.0f + state.capeSwing / 2.0f + state.capePitch) * Mth.PI / 180f)
                            .rotateZ(state.capeStrafe / 2.0f * Mth.PI / 180f)
                            .rotateY((180.0f - state.capeStrafe / 2.0f) * Mth.PI / 180f)
            );
        }
    }
}