package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class FrozenHeartsOverlay {

    private static final Identifier HEART_OVERLAY_TEXTURE = new Identifier(Frostiful.MODID, "textures/gui/cold_heart_overlay.png");
    public static final int MAX_COLD_HEARTS = 20;

    public static void drawHeartOverlayBar(MatrixStack matrices, PlayerEntity player, int[] heartXPositions,
                                           int[] heartYPositions) {

        FrostifulConfig config = Frostiful.getConfig();
        if (!config.clientConfig.doColdHeartOverlay()) {
            return;
        }

        final FreezableEntity freezable = (FreezableEntity) player;
        float freezingProgress = freezable.frostiful$getFrostProgress();

        // number of half cold hearts
        int frozenHealthPoints = (int) (freezingProgress * player.getMaxHealth());

        // number of whole hearts
        int frozenHealthHearts = MathHelper.ceil(frozenHealthPoints / 2.0f);

        int previousTexture = RenderSystem.getShaderTexture(0);
        RenderSystem.setShaderTexture(0, HEART_OVERLAY_TEXTURE);
        for (int m = 0; m < Math.min(MAX_COLD_HEARTS, frozenHealthHearts); m++) {
            // is half heart if this is the last heart being rendered and we have an odd
            // number of frozen health points
            int p = heartXPositions[m];
            int q = heartYPositions[m];
            boolean isHalfHeart = m + 1 >= frozenHealthHearts && (frozenHealthPoints & 1) == 1; // is odd check
            drawHeartOverLay(matrices, p, q, isHalfHeart);
        }
        RenderSystem.setShaderTexture(0, previousTexture);
    }

    private static void drawHeartOverLay(MatrixStack matrices, int x, int y, boolean isHalfHeart) {
        int u = isHalfHeart ? 9 : 0;
        DrawableHelper.drawTexture(matrices, x, y, u, 0, 9, 10, 18, 10);
    }
}