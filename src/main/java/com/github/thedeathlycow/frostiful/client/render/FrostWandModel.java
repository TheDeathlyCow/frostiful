package com.github.thedeathlycow.frostiful.client.render;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostWandModel extends Model {
    public static final Identifier TEXTURE = new Identifier(Frostiful.MODID, "textures/entity/frost_wand.png");

    private final ModelPart root;

    public FrostWandModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
