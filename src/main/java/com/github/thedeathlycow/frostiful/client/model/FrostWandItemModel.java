package com.github.thedeathlycow.frostiful.client.model;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/**
 * Made with Block Bench
 */
@Environment(EnvType.CLIENT)
public class FrostWandItemModel extends Model {
    public static final Identifier TEXTURE = new Identifier(Frostiful.MODID, "textures/entity/frost_wand.png");
    public static final EntityModelLayer FROST_WAND_LAYER = new EntityModelLayer(new Identifier(Frostiful.MODID, "frost_wand"), "main");

    private static final int FULL_BRIGHTNESS = (15 << 4) | (15 << 20); // packed lightmap coordinates are (block << 4) | (sky << 20)

    private final ModelPart root;

    public FrostWandItemModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucent);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData pole_topper = root.addChild("pole_topper", ModelPartBuilder.create().uv(4, 0).cuboid(-1.0F, -25.0F, -2.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(13, 5).cuboid(-1.0F, -25.0F, 1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(9, 10).cuboid(2.0F, -25.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(13, 0).cuboid(-1.0F, -25.0F, -3.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 9).cuboid(-2.0F, -25.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData icicles = root.addChild("icicles", ModelPartBuilder.create().uv(16, 16).cuboid(3.0F, -24.0F, 0.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(9, 8).cuboid(-1.0F, -24.0F, 1.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 8).cuboid(-2.0F, -24.0F, 0.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(9, 10).cuboid(-2.0F, -24.0F, -1.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 4).cuboid(-2.0F, -24.0F, -2.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 10).cuboid(2.0F, -24.0F, -3.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-1.0F, -24.0F, -3.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(11, 8).cuboid(3.0F, -24.0F, -2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 18).cuboid(2.0F, -24.0F, -2.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(5, 0).cuboid(2.0F, -24.0F, 1.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(8, 18).cuboid(1.0F, -24.0F, 2.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(17, 7).cuboid(0.0F, -24.0F, -3.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
                .uv(16, 2).cuboid(-1.0F, -24.0F, 2.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
                .uv(3, 0).cuboid(0.0F, -24.0F, 2.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(13, 2).cuboid(-1.0F, -24.0F, -3.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(18, 2).cuboid(1.0F, -24.0F, -3.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData spell = root.addChild("spell", ModelPartBuilder.create().uv(4, 13).cuboid(0.0F, -33.0F, -1.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(14, 9).cuboid(0.0F, -31.0F, 1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 14).cuboid(-2.0F, -31.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(16, 13).cuboid(0.0F, -31.0F, -3.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(12, 14).cuboid(2.0F, -31.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData pole = root.addChild("pole", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -23.0F, -1.0F, 1.0F, 23.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 5).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, FULL_BRIGHTNESS, overlay, red, green, blue, alpha);
    }
}
