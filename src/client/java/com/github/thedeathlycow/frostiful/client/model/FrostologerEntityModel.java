package com.github.thedeathlycow.frostiful.client.model;

import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class FrostologerEntityModel<F extends FrostologerEntityRenderState> extends IllagerEntityModel<F> {


    protected final ModelPart head;
    protected final ModelPart rightArm;
    protected final ModelPart leftArm;
    private final ModelPart cloak;

    public FrostologerEntityModel(ModelPart root) {
        super(root);
        ModelPart hat = this.getHead().getChild("hat");
        hat.visible = true;
        this.head = this.getHead();
        this.rightArm = this.getRootPart().getChild("right_arm");
        this.leftArm = this.getRootPart().getChild("left_arm");

        this.cloak = root.getChild("cloak");
        this.cloak.visible = false;
    }

    public void renderCloak(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.cloak.visible = true;
        this.cloak.render(matrices, vertices, light, overlay);
        this.cloak.visible = false;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("hat", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new Dilation(0.45F)), ModelTransform.NONE);
        head.addChild("nose", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        root.addChild("body", ModelPartBuilder.create().uv(16, 20).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).uv(0, 38).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData arms = root.addChild("arms", ModelPartBuilder.create().uv(44, 22).cuboid(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).uv(40, 38).cuboid(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), ModelTransform.of(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        arms.addChild("left_shoulder", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), ModelTransform.NONE);

        root.addChild("right_leg", ModelPartBuilder.create().uv(0, 22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
        root.addChild("left_leg", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
        root.addChild("right_arm", ModelPartBuilder.create().uv(40, 46).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        root.addChild("left_arm", ModelPartBuilder.create().uv(40, 46).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        root.addChild("cloak", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, Dilation.NONE, 1.0F, 0.5F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(F state) {
        super.setAngles(state);

        if (state.usingFrostWand) {
            if (state.illagerMainArm == Arm.LEFT) {
                this.leftArm.yaw = 0.1f + this.head.yaw;
                this.leftArm.pitch = -1.57f + this.head.pitch;
            } else {
                this.rightArm.yaw = -0.1f + this.head.yaw;
                this.rightArm.pitch = -1.57f + this.head.pitch;
            }
        }

        if (state.capeTexture != null) {
            this.cloak.rotate(
                    new Quaternionf()
                            .rotateX((6.0f + state.capeSwing / 2.0f + state.capePitch) * MathHelper.PI / 180f)
                            .rotateZ(state.capeStrafe / 2.0f * MathHelper.PI / 180f)
                            .rotateY((180.0f - state.capeStrafe / 2.0f) * MathHelper.PI / 180f)
            );
        }
    }
}
