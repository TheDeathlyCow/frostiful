package com.github.thedeathlycow.frostiful.mixins.client.gui;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
abstract class ColdHeartOverlay {

    private static final Identifier HEART_OVERLAY_TEXTURE = new Identifier(Frostiful.MODID, "textures/gui/cold_heart_overlay.png");

    private final int MAX_COLD_HEARTS = 20;
    private final int[] heartYPositions = new int[MAX_COLD_HEARTS];
    private final int[] heartXPositions = new int[MAX_COLD_HEARTS];

    @Inject(
            method = "renderHealthBar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIIZZ)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private void captureHeartPositions(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci, InGameHud.HeartType heartType, int i, int j, int k, int l, int m, int n, int o, int p, int q) {
        if (m < MAX_COLD_HEARTS) {
            heartYPositions[m] = q;
            heartXPositions[m] = p;
        }
    }

    @Inject(
            method = "renderHealthBar",
            at = @At(
                    value = "TAIL"
            )
    )
    private void drawColdHeartOverlayBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        double freezingProgress = ((double) player.getFrozenTicks()) / player.getMinFreezeDamageTicks();

        freezingProgress = MathHelper.clamp(freezingProgress, 0.0D, 1.0D);

        // number of half cold hearts
        int frozenHealthPoints = (int) (freezingProgress * MAX_COLD_HEARTS);

        // number of whole hearts
        int frozenHealthHearts = MathHelper.ceil((double) frozenHealthPoints / 2.0D);

        for (int m = 0; m < frozenHealthHearts; m++) {
            // is half heart if this is the last heart being rendered and we have an odd
            // number of frozen health points
            int p = heartXPositions[m];
            int q = heartYPositions[m];
            boolean isHalfHeart = m + 1 >= frozenHealthHearts && (frozenHealthPoints & 1) == 1;
            this.drawHeartOverLay(matrices, p, q, isHalfHeart);
        }
    }

    private void drawHeartOverLay(MatrixStack matrices, int x, int y, boolean isHalfHeart) {
        RenderSystem.setShaderTexture(0, HEART_OVERLAY_TEXTURE);
        int u = !isHalfHeart ? 0 : 9;
        DrawableHelper.drawTexture(matrices, x, y, u, 0, 9, 10, 18, 10);
        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
    }

}
