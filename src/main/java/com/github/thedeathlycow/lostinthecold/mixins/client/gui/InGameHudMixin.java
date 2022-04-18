package com.github.thedeathlycow.lostinthecold.mixins.client.gui;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
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
abstract class InGameHudMixin {

    private static int counter = 0;
    private static final Identifier HEART_OVERLAY_TEXTURE = new Identifier(LostInTheCold.MODID, "textures/gui/frozen_heart_full.png");

    @Inject(
            method = "renderHealthBar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIIZZ)V",
                    ordinal = 3,
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    @Deprecated
    private void renderFrozenHeart(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci, InGameHud.HeartType heartType, int i, int j, int k, int l, int m, int n, int o, int p, int q, int r, boolean bl3) {

        double freezingProgress = ((double) player.getFrozenTicks()) / player.getMinFreezeDamageTicks();
        freezingProgress = MathHelper.clamp(freezingProgress, 0.0D, 1.0D);

        int frozenHealthPoints = (int) (freezingProgress * 20);
        int frozenHealthHearts = MathHelper.ceil((double) frozenHealthPoints / 2.0D);

        if (m < frozenHealthHearts) {
            boolean isHalfHeart = m + 1 == frozenHealthHearts && (frozenHealthPoints & 1) == 1;

            drawHeartOverLay(matrices, p, q);
            // ((InGameHudInvoker) this).invokeDrawHeart(matrices, InGameHud.HeartType.FROZEN, p, q, i, false, isHalfHeart);
        }
    }

    private void drawHeartOverLay(MatrixStack matrices, int x, int y) {
        RenderSystem.setShaderTexture(0, HEART_OVERLAY_TEXTURE);
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, 9, 10, 9, 10);
        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
    }

}
