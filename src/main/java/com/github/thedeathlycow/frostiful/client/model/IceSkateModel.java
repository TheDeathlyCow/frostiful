package com.github.thedeathlycow.frostiful.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class IceSkateModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public IceSkateModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0f);
        ModelPartData root = modelData.getRoot();
        root.addChild(
                EntityModelPartNames.RIGHT_LEG,
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-1.0F, 13.0F, -4.0F, 2.0F, 0.0F, 8.0F, Dilation.NONE)
                        .uv(0, 2)
                        .cuboid(-1.0F, 11.0F, -4.0F, 2.0F, 2.0F, 0.0F, Dilation.NONE)
                        .uv(0, 0)
                        .cuboid(-1.0F, 11.0F, 4.0F, 2.0F, 2.0F, 0.0F, Dilation.NONE),
                ModelTransform.pivot(0.0F, 26.0F, 0.0F)
        );

        root.addChild(
                EntityModelPartNames.LEFT_LEG,
                ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 0)
                        .cuboid(-1.0F, 13.0F, -4.0F, 2.0F, 0.0F, 8.0F, Dilation.NONE)
                        .uv(0, 2)
                        .cuboid(-1.0F, 11.0F, -4.0F, 2.0F, 2.0F, 0.0F, Dilation.NONE)
                        .uv(0, 0)
                        .cuboid(-1.0F, 11.0F, 4.0F, 2.0F, 2.0F, 0.0F, Dilation.NONE),
                ModelTransform.pivot(0.0F, 26.0F, 0.0F)
        );
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        leftLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        rightLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
