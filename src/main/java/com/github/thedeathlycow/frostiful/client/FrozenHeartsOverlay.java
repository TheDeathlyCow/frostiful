package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FrozenHeartsOverlay {

    private static final Identifier HEART_OVERLAY_TEXTURE = new Identifier(Frostiful.MODID, "textures/gui/cold_heart_overlay.png");
    public static final int MAX_COLD_HEARTS = 20;

    public static void drawHeartOverlayBar(
            MatrixStack matrices,
            PlayerEntity player,
            int[] heartXPositions,
            int[] heartYPositions
    ) {

        FrostifulConfig config = Frostiful.getConfig();
        if (!config.clientConfig.doColdHeartOverlay()) {
            return;
        }

        float freezingProgress = player.thermoo$getTemperatureScale();
        if (freezingProgress > 0) {
            return;
        }
        freezingProgress = -freezingProgress;

        final float playerMaxHealth = player.getMaxHealth();

        // number of half cold hearts
        // max cold hearts is multiplied by 2 to covert to points
        int frozenHealthPoints = (int) (freezingProgress * Math.min(MAX_COLD_HEARTS * 2.0f, playerMaxHealth));

        // match the number of cold hearts to the display if HealthOverlay is loaded
        if (FabricLoader.getInstance().isModLoaded("healthoverlay")) {
            // 20 is the maximum number of health points that healthoverlay will display
            frozenHealthPoints = (int) (freezingProgress * Math.min(20f, playerMaxHealth));
        }

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