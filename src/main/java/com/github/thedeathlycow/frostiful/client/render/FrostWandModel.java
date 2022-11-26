package com.github.thedeathlycow.frostiful.client.render;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/**
 * Made with Block Bench
 */
public class FrostWandModel extends Model {
    public static final Identifier TEXTURE = new Identifier(Frostiful.MODID, "textures/entity/frost_wand.png");
    public static final EntityModelLayer FROST_WAND_LAYER = new EntityModelLayer(new Identifier(Frostiful.MODID, "frost_wand"), "main");

    private final ModelPart root;

    public FrostWandModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -23.0F, -1.0F, 1.0F, 23.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -25.0F, -2.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -33.0F, -1.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -31.0F, -3.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(2.0F, -31.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -31.0F, 1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.0F, -31.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(2.0F, -25.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.0F, -25.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -25.0F, 1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -25.0F, -3.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(3.0F, -24.0F, 0.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(3.0F, -24.0F, -2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(2.0F, -24.0F, -2.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -24.0F, -3.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -24.0F, -3.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(2.0F, -24.0F, 1.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(1.0F, -24.0F, 2.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -24.0F, 2.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -24.0F, 2.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -24.0F, 1.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.0F, -24.0F, 0.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.0F, -24.0F, -1.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.0F, -24.0F, -2.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -24.0F, -3.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(2.0F, -24.0F, -3.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(1.0F, -24.0F, -3.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
