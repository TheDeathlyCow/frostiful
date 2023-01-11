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
import terrails.healthoverlay.heart.Heart;
import terrails.healthoverlay.heart.HeartType;
import terrails.healthoverlay.render.HeartRenderer;
import terrails.healthoverlay.utilities.Vec2i;

@Environment(EnvType.CLIENT)
@Mixin(value = HeartRenderer.class, remap = false)
public abstract class ColdHeartOverlay {
    private final int[] heartYPositions = new int[FrozenHeartsOverlay.MAX_COLD_HEARTS];
    private final int[] heartXPositions = new int[FrozenHeartsOverlay.MAX_COLD_HEARTS];

    @Inject(method = "renderPlayerHearts", at = @At("TAIL"))
    private void drawColdHeartOverlayBar(MatrixStack poseStack, PlayerEntity player, CallbackInfo ci) {
        FrozenHeartsOverlay.drawHeartOverlayBar(poseStack, player, heartXPositions, heartYPositions);
    }

    @Inject(method = "renderPlayerHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lterrails/healthoverlay/heart/Heart;draw(Lnet/minecraft/client/util/math/MatrixStack;IIZLterrails/healthoverlay/heart/HeartType;)V",
                    remap = true,
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private void injectionTest(
            MatrixStack poseStack, PlayerEntity player, CallbackInfo ci,
            int currentHealth, long tickCount, boolean blinking,
            long currentTime, int maxHealth, int absorption,
            int regenerationIndex, Vec2i healthCoords, Vec2i absorptionCoords,
            int xPos, int yPos, HeartType heartType, int index, Heart heart,
            int regenOffset, int yPosition, int xPosition
    ) {
        if (index < FrozenHeartsOverlay.MAX_COLD_HEARTS) {
            heartXPositions[index] = xPosition;
            heartYPositions[index] = yPosition;
        }
    }
}
