package com.github.thedeathlycow.frostiful.client.model;

import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.FastColor;

@Environment(EnvType.CLIENT)
public class FrostologerEntityModel<F extends FrostologerEntity> extends IllagerModel<F> {


    protected final ModelPart head;
    protected final ModelPart rightArm;
    protected final ModelPart leftArm;

    private float rgColourMul = 0f;

    private final ModelPart cloak;

    public FrostologerEntityModel(ModelPart root) {
        super(root);
        ModelPart hat = this.getHead().getChild("hat");
        hat.visible = true;
        this.head = this.getHead();
        this.rightArm = this.root().getChild("right_arm");
        this.leftArm = this.root().getChild("left_arm");

        this.cloak = root.getChild("cloak");
        this.cloak.visible = false;
    }


    public void render(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color, boolean applyColdOverlay) {
        if (this.rgColourMul < 1 && applyColdOverlay) {
            float alpha = FastColor.ARGB32.alpha(color) / 255f;
            float red = FastColor.ARGB32.red(color) / 255f * this.rgColourMul;
            float green = FastColor.ARGB32.green(color) / 255f * this.rgColourMul;
            float blue = FastColor.ARGB32.blue(color) / 255f;
            color = FastColor.ARGB32.colorFromFloat(alpha, red, green, blue);
        }

        super.renderToBuffer(matrices, vertices, light, overlay, color);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.render(matrices, vertices, light, overlay, color, true);
    }

    public void forceRenderCloak(PoseStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.cloak.visible = true;
        this.cloak.render(matrices, vertices, light, overlay);
        this.cloak.visible = false;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);
        head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));

        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arms = root.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        arms.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), PartPose.ZERO);

        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));

        root.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, CubeDeformation.NONE, 1.0F, 0.5F), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
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
        super.setupAnim(frostologer, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        this.rgColourMul = (0.625f * (frostologer.thermoo$getTemperatureScale() + 1f)) + 0.5f;

        if (frostologer.isUsingFrostWand()) {
            if (frostologer.isLeftHanded()) {
                this.leftArm.yRot = 0.1f + this.head.yRot;
                this.leftArm.xRot = -1.57f + this.head.xRot;
            } else {
                this.rightArm.yRot = -0.1f + this.head.yRot;
                this.rightArm.xRot = -1.57f + this.head.xRot;
            }
        }
    }
}
