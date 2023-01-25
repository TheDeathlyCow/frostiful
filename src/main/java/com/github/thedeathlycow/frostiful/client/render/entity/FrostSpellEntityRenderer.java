package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.entity.SpellEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class FrostSpellEntityRenderer extends EntityRenderer<SpellEntity> {

    private static final Identifier TEXTURE = Frostiful.id("textures/entity/frost_spell.png");
    private static final RenderLayer LAYER;

    protected FrostSpellEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(SpellEntity spellEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
        produceVertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, 1);
        produceVertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 0, 1, 1);
        produceVertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 1, 1, 0);
        produceVertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, 0);
        matrixStack.pop();
        super.render(spellEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    private static void produceVertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, int light, float x, int y, int textureU, int textureV) {
        vertexConsumer
                .vertex(positionMatrix, x - 0.5f, y - 0.25f, 0.0f)
                .color(0xff, 0xff, 0xff, 0xff)
                .texture(textureU, textureV)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(normalMatrix, 0.0F, 1.0F, 0.0F)
                .next();
    }

    @Override
    public Identifier getTexture(SpellEntity entity) {
        return TEXTURE;
    }

    static {
        LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);
    }
}
