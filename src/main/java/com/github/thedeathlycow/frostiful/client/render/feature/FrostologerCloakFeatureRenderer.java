package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.github.thedeathlycow.frostiful.item.cloak.AbstractFrostologyCloakItem;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class FrostologerCloakFeatureRenderer extends RenderLayer<FrostologerEntity, FrostologerEntityModel<FrostologerEntity>> {
    public FrostologerCloakFeatureRenderer(
            RenderLayerParent<FrostologerEntity, FrostologerEntityModel<FrostologerEntity>> featureRendererContext
    ) {
        super(featureRendererContext);
    }

    public void render(
            PoseStack matrixStack,
            MultiBufferSource vertexConsumerProvider,
            int light,
            FrostologerEntity frostologer,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        ItemStack cloak = frostologer.getItemBySlot(EquipmentSlot.CHEST);

        if (!cloak.is(FItems.FROSTOLOGY_CLOAK)) {
            return;
        }

        matrixStack.pushPose();
        matrixStack.translate(0.0, 0.0, 3f / 16f);
        double capeX = Mth.lerp(tickDelta, frostologer.prevCapeX, frostologer.capeX) - Mth.lerp(tickDelta, frostologer.xo, frostologer.getX());
        double capeY = Mth.lerp(tickDelta, frostologer.prevCapeY, frostologer.capeY) - Mth.lerp(tickDelta, frostologer.yo, frostologer.getY());
        double capeZ = Mth.lerp(tickDelta, frostologer.prevCapeZ, frostologer.capeZ) - Mth.lerp(tickDelta, frostologer.zo, frostologer.getZ());
        float yawDelta = frostologer.yBodyRotO + (frostologer.yBodyRot - frostologer.yBodyRotO);
        double rotZ = Mth.sin(yawDelta * Mth.PI / 180);
        double rotX = -Mth.cos(yawDelta * Mth.PI / 180);
        float q = Mth.clamp((float) capeY * 10.0F, -6.0F, 32.0F);
        float r = Mth.clamp((float) (capeX * rotZ + capeZ * rotX) * 100.0F, 0.0F, 150.0F);
        float s = Mth.clamp((float) (capeX * rotX - capeZ * rotZ) * 100.0F, -20.0F, 20.0F);

        if (r < 0.0F) {
            r = 0.0F;
        }

        float t = Mth.lerp(tickDelta, frostologer.prevStrideDistance, frostologer.strideDistance);
        q += Mth.sin(Mth.lerp(tickDelta, frostologer.walkDistO, frostologer.walkDist) * 6.0F) * 32.0F * t;
        if (frostologer.isCrouching()) {
            q += 25.0F;
        }

        matrixStack.mulPose(Axis.XP.rotationDegrees(6.0F + r / 2.0F + q));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(s / 2.0F));
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - s / 2.0F));
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entitySolid(AbstractFrostologyCloakItem.MODEL_TEXTURE_ID));
        this.getParentModel().forceRenderCloak(matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
    }
}
