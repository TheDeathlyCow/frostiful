package com.github.thedeathlycow.frostiful.client.render.model;

import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;

public class FrostologerCapeModel<F extends FrostologerEntityRenderState> extends IllagerEntityModel<F> {
    public static final String CLOAK_NAME = "cloak";

    private final ModelPart cloak;

    public FrostologerCapeModel(ModelPart root) {
        super(root);
        this.cloak = root.getChild(EntityModelPartNames.BODY).getChild(CLOAK_NAME);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = FrostologerEntityModel.getModelData();
        ModelPartData modelPartData = modelData.getRoot().resetChildrenParts();
        ModelPartData root = modelPartData.getChild(EntityModelPartNames.BODY);

        root.addChild(CLOAK_NAME, ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, Dilation.NONE, 1.0F, 0.5F), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(F state) {
        super.setAngles(state);
        if (state.capeTexture != null) {
            this.cloak.rotate(
                    new Quaternionf()
                            .rotateX((6.0f + state.capeSwing / 2.0f + state.capePitch) * MathHelper.PI / 180f)
                            .rotateZ(state.capeStrafe / 2.0f * MathHelper.PI / 180f)
                            .rotateY((180.0f - state.capeStrafe / 2.0f) * MathHelper.PI / 180f)
            );
        }
    }
}