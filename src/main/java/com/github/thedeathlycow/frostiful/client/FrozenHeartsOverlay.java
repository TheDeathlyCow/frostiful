package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import terrails.colorfulhearts.render.RenderUtils;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class FrozenHeartsOverlay {

    public static final Identifier HEART_OVERLAY_TEXTURE = new Identifier(Frostiful.MODID, "textures/gui/cold_heart_overlay.png");
    public static final int MAX_COLD_HEARTS = 20;

    public static void drawHeartOverlayBar(
            Drawer drawer,
            DrawContext context,
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

        for (int m = 0; m < Math.min(MAX_COLD_HEARTS, frozenHealthHearts); m++) {
            // is half heart if this is the last heart being rendered and we have an odd
            // number of frozen health points
            int x = heartXPositions[m];
            int y = heartYPositions[m];
            boolean isHalfHeart = m + 1 >= frozenHealthHearts && (frozenHealthPoints & 1) == 1; // is odd check

            int u = isHalfHeart ? 9 : 0;
            drawer.draw(context, HEART_OVERLAY_TEXTURE, x, y, u);
        }
    }

    @FunctionalInterface
    public interface Drawer {

        void draw(DrawContext context, Identifier texture, int x, int y, int u);

    }

}