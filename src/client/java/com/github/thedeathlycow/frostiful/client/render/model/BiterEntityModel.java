package com.github.thedeathlycow.frostiful.client.render.model;

import com.github.thedeathlycow.frostiful.client.anim.BiterAnimations;
import com.github.thedeathlycow.frostiful.client.render.state.BiterEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BiterEntityModel extends EntityModel<BiterEntityRenderState> {
    private final ModelPart modelPart;

    private final ModelPart head;
    private final ModelPart mouthTop;
    private final ModelPart mouthBottom;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public BiterEntityModel(ModelPart modelPart) {
        super(modelPart);
        this.modelPart = modelPart;

        ModelPart root = modelPart.getChild(EntityModelPartNames.ROOT);

        this.head = root.getChild(EntityModelPartNames.HEAD);
        ModelPart mouth = this.head.getChild(EntityModelPartNames.MOUTH);

        this.mouthTop = mouth.getChild("mouth_top");
        this.mouthBottom = mouth.getChild("mouth_bottom");

        this.leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 21.0F, 0.0F));

        ModelPartData mouth = head.addChild(EntityModelPartNames.MOUTH, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData bottom = mouth.addChild("mouth_bottom", ModelPartBuilder.create().uv(54, 28).cuboid(-6.0F, 0.0F, 0.0F, 12.0F, 4.0F, 0.0F, new Dilation(0.0F))
                .uv(50, 0).cuboid(-6.0F, 0.0F, -12.0F, 12.0F, 4.0F, 0.0F, new Dilation(0.0F))
                .uv(40, 20).cuboid(6.0F, 0.0F, -12.0F, 0.0F, 4.0F, 12.0F, new Dilation(0.0F))
                .uv(30, 16).cuboid(-6.0F, 0.0F, -12.0F, 0.0F, 4.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-6.0F, 4.0F, -12.0F, 12.0F, 0.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -18.0F, 6.0F));

        ModelPartData top = mouth.addChild("mouth_top", ModelPartBuilder.create().uv(54, 44).cuboid(-6.0F, -8.0F, 0.0F, 12.0F, 8.0F, 0.0F, new Dilation(0.0F))
                .uv(48, 36).cuboid(-6.0F, -8.0F, -12.0F, 12.0F, 8.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 40).cuboid(6.0F, -8.0F, -12.0F, 0.0F, 8.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(-6.0F, -8.0F, -12.0F, 0.0F, 8.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-6.0F, -8.0F, -12.0F, 12.0F, 0.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -18.0F, 6.0F));

        ModelPartData nose = top.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -22.0F, -8.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 8).cuboid(-1.0F, -16.0F, -8.0F, 2.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 18.0F, -6.0F));

        ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 24).cuboid(-5.0F, -14.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 21.0F, 0.0F));

        ModelPartData leftArm = root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(36, 0).cuboid(-2.0F, 3.0F, -3.0F, 4.0F, 22.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, -1.0F, 0.0F));

        ModelPartData rightArm = root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(36, 0).mirrored().cuboid(-2.0F, 3.0F, -3.0F, 4.0F, 22.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(-8.0F, -1.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(BiterEntityRenderState state) {
        super.setAngles(state);

        this.rightArm.pitch = -1.5F * MathHelper.wrap(state.limbSwingAnimationProgress, 10.0F) * state.limbSwingAmplitude;
        this.leftArm.pitch = 1.5F * MathHelper.wrap(state.limbSwingAnimationProgress, 10.0F) * state.limbSwingAmplitude;
        this.rightArm.yaw = 0.0F;
        this.leftArm.yaw = 0.0F;

        this.animate(state.biteAnimationState, BiterAnimations.BITE, state.age);
    }
}
