package com.github.thedeathlycow.frostiful.mixins.compat.healthoverlay.present;

import com.github.thedeathlycow.frostiful.client.FrozenHeartsOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    private final int[] heartYPositions = new int[FrozenHeartsOverlay.MAX_COLD_HEARTS];
    private final int[] heartXPositions = new int[FrozenHeartsOverlay.MAX_COLD_HEARTS];

    @Inject(method = "renderPlayerHearts", at = @At("TAIL"))
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
        FrozenHeartsOverlay.drawHeartOverlayBar(poseStack, player, heartXPositions, heartYPositions);
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
        if (index < FrozenHeartsOverlay.MAX_COLD_HEARTS) {
            heartXPositions[index] = xPos;
            heartYPositions[index] = yPos;
        }
    }
}
