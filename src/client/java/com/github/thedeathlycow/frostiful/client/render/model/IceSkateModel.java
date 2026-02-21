package com.github.thedeathlycow.frostiful.client.render.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class IceSkateModel<T extends HumanoidRenderState> extends HumanoidModel<T> {
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.5F);

    public IceSkateModel(ModelPart root) {
        super(root);
        this.setAllVisible(false);
        this.leftLeg.visible = true;
        this.rightLeg.visible = true;
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0f);
        PartDefinition root = modelData.getRoot();
        root.addOrReplaceChild(
                PartNames.RIGHT_LEG,
                CubeListBuilder.create()
                        // blade base
                        .texOffs(0, 0)
                        .addBox(-1f, 13.0F, -4.0F, 2.0F, 0.0F, 8.0F, CubeDeformation.NONE)
                        // blade cross
                        .texOffs(8, 0)
                        .addBox(0.0F, 13.0F, -4.0F, 0.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                        // blade back
                        .texOffs(0, 2)
                        .addBox(-1f, 11F, -4.0F, 2.0F, 2.0F, 0.0F, CubeDeformation.NONE)
                        // blade front
                        .texOffs(0, 0)
                        .addBox(-1f, 11F, 4.0F, 2.0F, 2.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(-1.9f, 12.0f, 0.0f)
        );

        root.addOrReplaceChild(
                PartNames.LEFT_LEG,
                CubeListBuilder.create()
                        .mirror()
                        .texOffs(0, 0)
                        .addBox(-1f, 13.0F, -4.0F, 2.0F, 0.0F, 8.0F, CubeDeformation.NONE)

                        .texOffs(8, 0)
                        .addBox(0.0F, 13.0F, -4.0F, 0.0F, 1.0F, 8.0F, CubeDeformation.NONE)

                        .texOffs(0, 2)
                        .addBox(-1f, 11F, -4.0F, 2.0F, 2.0F, 0.0F, CubeDeformation.NONE)
                        .texOffs(0, 0)
                        .addBox(-1f, 11f, 4.0F, 2.0F, 2.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(1.9f, 12.0f, 0.0f)
        );
        return LayerDefinition.create(modelData, 32, 32);
    }

    public static LayerDefinition getBabyTexturedModelData() {
        return getTexturedModelData().apply(BABY_TRANSFORMER);
    }

//    @Override
//    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
//        leftLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
//        rightLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
//    }
}
