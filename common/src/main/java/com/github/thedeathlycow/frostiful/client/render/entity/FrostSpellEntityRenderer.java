package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.FrostSpell;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;


public class FrostSpellEntityRenderer extends EntityRenderer<FrostSpell, EntityRenderState> {

    private static final Identifier TEXTURE = Frostiful.id("textures/entity/frost_spell.png");
    private static final RenderType LAYER = RenderTypes.entityCutout(TEXTURE);

    public FrostSpellEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(
            EntityRenderState renderState,
            PoseStack matrices,
            SubmitNodeCollector queue,
            CameraRenderState cameraState
    ) {
        matrices.pushPose();
        matrices.scale(2.0f, 2.0f, 2.0f);
        matrices.mulPose(cameraState.orientation);
        queue.submitCustomGeometry(matrices, LAYER, (entry, vertexConsumer) -> {
            produceVertex(vertexConsumer, entry, renderState.lightCoords, 0f, 0, 0, 1);
            produceVertex(vertexConsumer, entry, renderState.lightCoords, 1f, 0, 1, 1);
            produceVertex(vertexConsumer, entry, renderState.lightCoords, 1f, 1, 1, 0);
            produceVertex(vertexConsumer, entry, renderState.lightCoords, 0f, 1, 0, 0);
        });
        matrices.popPose();
        super.submit(renderState, matrices, queue, cameraState);
    }

    private static void produceVertex(
            VertexConsumer vertexConsumer,
            PoseStack.Pose matrix,
            int light,
            float x, int z,
            int textureU, int textureV
    ) {
        vertexConsumer.addVertex(matrix, x - 0.5F, z - 0.25F, 0.0F)
                .setColor(CommonColors.WHITE)
                .setUv(textureU, textureV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(matrix, 0.0F, 1.0F, 0.0F);
    }
}
