package com.github.thedeathlycow.frostiful.client.model;

import com.github.thedeathlycow.frostiful.entity.FrostWraithEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public class FrostWraithEntityModel extends BipedEntityModel<FrostWraithEntity> {
    public FrostWraithEntityModel(ModelPart root) {
        super(root);
        this.leftLeg.visible = false;
        this.rightLeg.visible = false;
        this.hat.visible = true;
    }
}
