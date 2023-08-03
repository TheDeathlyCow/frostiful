package com.github.thedeathlycow.frostiful.mixins.compat.overflowingbars.absent;

import com.github.thedeathlycow.frostiful.client.FrozenHeartsOverlay;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
abstract class ColdHeartOverlay {

    @Inject(
            method = "renderHealthBar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIIZZ)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private void captureHeartPositions(
            DrawContext context,
            PlayerEntity player,
            int x, int y,
            int lines,
            int regeneratingHeartIndex,
            float maxHealth,
            int lastHealth,
            int health,
            int absorption,
            boolean blinking,
            CallbackInfo ci,
            // local captures
            InGameHud.HeartType heartType,
            int i, int j, int k, int l,
            int m, // index of heart
            int n, int o,
            int p, int q // position of heart to capture
    ) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.COLORFUL_HEARTS_ID)) return;
        FrozenHeartsOverlay.INSTANCE.setHeartPos(m, p, q);
    }

    @Inject(
            method = "renderHealthBar",
            at = @At(
                    value = "TAIL"
            )
    )
    private void drawColdHeartOverlayBar(
            DrawContext context,
            PlayerEntity player,
            int x, int y,
            int lines,
            int regeneratingHeartIndex,
            float maxHealth,
            int lastHealth,
            int health,
            int absorption,
            boolean blinking,
            CallbackInfo ci
    ) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.COLORFUL_HEARTS_ID)) return;
        FrozenHeartsOverlay.INSTANCE.drawHeartOverlayBar(
                context,
                player
        );
    }

}
