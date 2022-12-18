package com.github.thedeathlycow.frostiful.client.model;

import com.github.thedeathlycow.frostiful.entity.FrostologerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

@Environment(EnvType.CLIENT)
public class FrostologerEntityModel<F extends FrostologerEntity> extends IllagerEntityModel<F> {


    protected final ModelPart head;
    protected final ModelPart rightArm;
    protected final ModelPart leftArm;

    public FrostologerEntityModel(ModelPart root) {
        super(root);
        ModelPart hat = this.getHead().getChild("hat");
        hat.visible = true;
        this.head = this.getHead();
        this.rightArm = this.getPart().getChild("right_arm");
        this.leftArm = this.getPart().getChild("left_arm");
    }

    @Override
    public void setAngles(
            F frostologer,
            float limbAngle,
            float limbDistance,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        super.setAngles(frostologer, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        if (frostologer.isUsingFrostWand()) {
            if (frostologer.isLeftHanded()) {
                this.leftArm.yaw = 0.1f + this.head.yaw;
                this.leftArm.pitch = -1.57f + this.head.pitch;
            } else {
                this.rightArm.yaw = -0.1f + this.head.yaw;
                this.rightArm.pitch = -1.57f + this.head.pitch;
            }
        }
    }
}
