package com.github.thedeathlycow.frostiful.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class IceSkateModel<T extends LivingEntity> extends BipedEntityModel<T> {

    private final ModelPart leftSkates;
    private final ModelPart rightSkates;

    public IceSkateModel(ModelPart root) {
        super(root);
        this.leftSkates = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightSkates = root.getChild(EntityModelPartNames.RIGHT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0f);
        ModelPartData root = modelData.getRoot();
        root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create()
                        .uv(-6, -6)
                        .cuboid(3.0F, 13.0F, -4.0F, 0.0F, 2.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );
        root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create()
                        .uv(-6, -6)
                        .cuboid(-3.0F, 13.0F, -4.0F, 0.0F, 2.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        leftSkates.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        rightSkates.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
