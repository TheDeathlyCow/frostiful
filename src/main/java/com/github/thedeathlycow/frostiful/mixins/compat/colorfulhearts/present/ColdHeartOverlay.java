package com.github.thedeathlycow.frostiful.mixins.compat.colorfulhearts.present;

import com.github.thedeathlycow.frostiful.client.FrozenHeartsOverlay;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.mixins.client.DrawContextInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import terrails.colorfulhearts.heart.Heart;
import terrails.colorfulhearts.heart.HeartType;
import terrails.colorfulhearts.render.HeartRenderer;

@Environment(EnvType.CLIENT)
@Mixin(value = HeartRenderer.class, remap = false)
public abstract class ColdHeartOverlay {

    @Inject(
            method = "renderPlayerHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V",
                    shift = At.Shift.BEFORE
            )
    )
    private void drawColdHeartOverlayBar(
            MatrixStack poseStack,
            PlayerEntity player,
            int x, int y,
            int maxHealth,
            int currentHealth,
            int displayHealth,
            int absorption,
            boolean renderHighlight,
            CallbackInfo ci
    ) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.OVERFLOWING_BARS_ID)) return;
        MinecraftClient client = MinecraftClient.getInstance();
        DrawContext drawContext = DrawContextInvoker.create(client, poseStack, client.getBufferBuilders().getEntityVertexConsumers());
        FrozenHeartsOverlay.INSTANCE.drawHeartOverlayBar(
                drawContext,
                player
        );
    }

    @Inject(method = "renderPlayerHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lterrails/colorfulhearts/heart/Heart;draw(Lnet/minecraft/client/util/math/MatrixStack;IIZZLterrails/colorfulhearts/heart/HeartType;)V",
                    remap = true,
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private void captureHeartPositions(
            MatrixStack poseStack,
            PlayerEntity player,
            int x, int y,
            int maxHealth,
            int currentHealth,
            int displayHealth,
            int absorption,
            boolean renderHighlight,
            CallbackInfo ci,
            int healthHearts,
            int displayHealthHearts,
            boolean absorptionSameRow,
            int regenIndex,
            HeartType heartType,
            int index,
            Heart heart,
            int xPos, int yPos,
            boolean highlightHeart
    ) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.OVERFLOWING_BARS_ID)) return;
        FrozenHeartsOverlay.INSTANCE.setHeartPos(index, xPos, yPos);
    }
}
