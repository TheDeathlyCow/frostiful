package com.github.thedeathlycow.frostiful.client.model;

import com.github.thedeathlycow.frostiful.entity.FrostologerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;

@Environment(EnvType.CLIENT)
public class FrostologerEntityModel<F extends FrostologerEntity> extends IllagerEntityModel<F> {

    public FrostologerEntityModel(ModelPart root) {
        super(root);
        ModelPart hat = this.getHead().getChild("hat");
        hat.visible = true;
        EntityRenderers
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
            ModelPart rightArm = this.getPart().getChild("right_arm");
            ModelPart head = this.getHead();

            rightArm.yaw = -0.1f + head.yaw;
            rightArm.pitch = -1.57f + head.pitch;
        }
    }
}
