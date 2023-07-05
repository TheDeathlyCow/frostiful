package com.github.thedeathlycow.frostiful.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class IceSkateModel<T extends LivingEntity> extends EntityModel<T> {

    private final ModelPart skates;
    public IceSkateModel(ModelPart root) {
        this.skates = root.getChild("skates");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("skates", ModelPartBuilder.create().uv(-6, -6).cuboid(1.0F, -2.0F, -4.0F, 0.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(-6, -6).cuboid(-1.0F, -2.0F, -4.0F, 0.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        skates.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
