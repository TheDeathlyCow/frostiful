package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.FrostSpellEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.DragonFireballEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostSpellEntityRenderer extends EntityRenderer<FrostSpellEntity, EntityRenderState> {

    private static final Identifier TEXTURE = Frostiful.id("textures/entity/frost_spell.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);

    public FrostSpellEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void render(
            EntityRenderState renderState,
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            CameraRenderState cameraState
    ) {
        matrices.push();
        matrices.scale(2.0f, 2.0f, 2.0f);
        matrices.multiply(cameraState.orientation);
        queue.submitCustom(matrices, LAYER, (entry, vertexConsumer) -> {
            produceVertex(vertexConsumer, entry, renderState.light, 0f, 0, 0, 1);
            produceVertex(vertexConsumer, entry, renderState.light, 1f, 0, 1, 1);
            produceVertex(vertexConsumer, entry, renderState.light, 1f, 1, 1, 0);
            produceVertex(vertexConsumer, entry, renderState.light, 0f, 1, 0, 0);
        });
        matrices.pop();
        super.render(renderState, matrices, queue, cameraState);
    }

    private static void produceVertex(
            VertexConsumer vertexConsumer,
            MatrixStack.Entry matrix,
            int light,
            float x, int z,
            int textureU, int textureV
    ) {
        vertexConsumer.vertex(matrix, x - 0.5F, z - 0.25F, 0.0F)
                .color(Colors.WHITE)
                .texture(textureU, textureV)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(matrix, 0.0F, 1.0F, 0.0F);
    }
}
