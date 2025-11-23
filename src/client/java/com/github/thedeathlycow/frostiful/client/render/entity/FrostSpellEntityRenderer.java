package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.SpellEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;

@Environment(EnvType.CLIENT)
public class FrostSpellEntityRenderer extends EntityRenderer<SpellEntity> {

    private static final ResourceLocation TEXTURE = Frostiful.id("textures/entity/frost_spell.png");
    private static final RenderType LAYER;

    public FrostSpellEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    public void render(
            SpellEntity dragonFireballEntity,
            float yaw, float tickDelta,
            PoseStack matrixStack,
            MultiBufferSource vertexConsumerProvider,
            int light
    ) {
        matrixStack.pushPose();
        matrixStack.scale(2.0F, 2.0F, 2.0F);
        matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        PoseStack.Pose entry = matrixStack.last();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
        produceVertex(vertexConsumer, entry, light, 0.0F, 0, 0, 1);
        produceVertex(vertexConsumer, entry, light, 1.0F, 0, 1, 1);
        produceVertex(vertexConsumer, entry, light, 1.0F, 1, 1, 0);
        produceVertex(vertexConsumer, entry, light, 0.0F, 1, 0, 0);
        matrixStack.popPose();
        super.render(dragonFireballEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
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

    @Override
    public ResourceLocation getTexture(SpellEntity entity) {
        return TEXTURE;
    }

    static {
        LAYER = RenderType.entityCutoutNoCull(TEXTURE);
    }
}
