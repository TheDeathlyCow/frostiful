package com.github.thedeathlycow.frostiful.client.render.model;

import com.github.thedeathlycow.frostiful.client.anim.BiterAnimations;
import com.github.thedeathlycow.frostiful.client.render.state.BiterEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class BiterEntityModel extends EntityModel<BiterEntityRenderState> {
    private final ModelPart modelPart;

    private final ModelPart head;
    private final ModelPart mouthTop;
    private final ModelPart mouthBottom;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    private final KeyframeAnimation biteAnimation;

    public BiterEntityModel(ModelPart modelPart) {
        super(modelPart);
        this.modelPart = modelPart;

        ModelPart root = modelPart.getChild(PartNames.ROOT);

        this.head = root.getChild(PartNames.HEAD);
        ModelPart mouth = this.head.getChild(PartNames.MOUTH);

        this.mouthTop = mouth.getChild("mouth_top");
        this.mouthBottom = mouth.getChild("mouth_bottom");

        this.leftArm = root.getChild(PartNames.LEFT_ARM);
        this.rightArm = root.getChild(PartNames.RIGHT_ARM);

        this.biteAnimation = BiterAnimations.BITE.bake(this.root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild(PartNames.ROOT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition head = root.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition mouth = head.addOrReplaceChild(PartNames.MOUTH, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bottom = mouth.addOrReplaceChild("mouth_bottom", CubeListBuilder.create().texOffs(54, 28).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(50, 0).addBox(-6.0F, 0.0F, -12.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(40, 20).addBox(6.0F, 0.0F, -12.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(30, 16).addBox(-6.0F, 0.0F, -12.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-6.0F, 4.0F, -12.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 6.0F));

        PartDefinition top = mouth.addOrReplaceChild("mouth_top", CubeListBuilder.create().texOffs(54, 44).addBox(-6.0F, -8.0F, 0.0F, 12.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(48, 36).addBox(-6.0F, -8.0F, -12.0F, 12.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(6.0F, -8.0F, -12.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-6.0F, -8.0F, -12.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, -8.0F, -12.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 6.0F));

        PartDefinition nose = top.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -22.0F, -8.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-1.0F, -16.0F, -8.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -6.0F));

        PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 24).addBox(-5.0F, -14.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition leftArm = root.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(36, 0).addBox(-2.0F, 3.0F, -3.0F, 4.0F, 22.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -1.0F, 0.0F));

        PartDefinition rightArm = root.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(36, 0).mirror().addBox(-2.0F, 3.0F, -3.0F, 4.0F, 22.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -1.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void setupAnim(BiterEntityRenderState state) {
        super.setupAnim(state);

        this.rightArm.xRot = -1.5F * Mth.triangleWave(state.walkAnimationPos, 10.0F) * state.walkAnimationSpeed;
        this.leftArm.xRot = 1.5F * Mth.triangleWave(state.walkAnimationPos, 10.0F) * state.walkAnimationSpeed;
        this.rightArm.yRot = 0.0F;
        this.leftArm.yRot = 0.0F;

        this.biteAnimation.apply(state.biteAnimationState, state.ageInTicks);
    }
}
