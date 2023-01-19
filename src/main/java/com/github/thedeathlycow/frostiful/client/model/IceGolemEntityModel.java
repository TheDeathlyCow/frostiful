package com.github.thedeathlycow.frostiful.client.model;

import com.github.thedeathlycow.frostiful.entity.IceGolemEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

public class IceGolemEntityModel extends SinglePartEntityModel<IceGolemEntity> {


    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart nose;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public IceGolemEntityModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.nose = root.getChild("nose");

        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");

    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        ModelPartData nose = root.addChild("nose", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -22.0F, -8.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(6, 0).cuboid(-1.0F, -17.0F, -8.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 21.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -30.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 24).cuboid(-5.0F, -18.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F))
                .uv(40, 16).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 21.0F, 0.0F));

        ModelPartData leftArm = root.addChild("left_arm", ModelPartBuilder.create().uv(0, 44).mirrored().cuboid(6.0F, -25.0F, -3.0F, 4.0F, 25.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData rightArm = root.addChild("right_arm", ModelPartBuilder.create().uv(0, 44).cuboid(-10.0F, -25.0F, -3.0F, 4.0F, 25.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(IceGolemEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
