package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class FrozenHeartsOverlay {

    public static final ResourceLocation HEART_OVERLAY_TEXTURE = Frostiful.id("textures/gui/cold_heart_overlay.png");

    private static final int TEXTURE_WIDTH = 18;

    private static final int TEXTURE_HEIGHT = 10;

    public static void afterHealthBar(
            GuiGraphics context,
            Player player,
            Vector2i[] heartPositions,
            int displayHealth,
            int maxDisplayHealth
    ) {
        FrostifulConfig config = Frostiful.getConfig();
        if (!config.clientConfig.doColdHeartOverlay() || player.thermoo$isWarm()) {
            return;
        }

        int frozenHealthPoints = getNumColdPoints(player, maxDisplayHealth);
        int frozenHealthHearts = getNumColdHeartsFromPoints(frozenHealthPoints);
        for (int i = 0; i < frozenHealthHearts; i++) {
            Vector2i pos = heartPositions[i];
            if (pos == null) {
                continue;
            }
            // is half heart if this is the last heart being rendered and we have an odd
            // number of frozen health points
            boolean isHalfHeart = i + 1 >= frozenHealthHearts && (frozenHealthPoints & 1) == 1; // is odd check

            int u = isHalfHeart ? 9 : 0;
            context.blit(
                    HEART_OVERLAY_TEXTURE,
                    pos.x, pos.y,
                    u, 0,
                    9, 10,
                    TEXTURE_WIDTH, TEXTURE_HEIGHT
            );
        }
    }

    public static void afterMountHealthBar(
            GuiGraphics context,
            Player player,
            LivingEntity mount,
            Vector2i[] mountHeartPositions,
            int displayMountHealth,
            int maxDisplayMountHealth
    ) {
        FrostifulConfig config = Frostiful.getConfig();
        if (!config.clientConfig.doColdHeartOverlay() || mount.thermoo$isWarm()) {
            return;
        }

        int frozenHealthPoints = getNumColdPoints(mount, maxDisplayMountHealth);
        int frozenHealthHearts = getNumColdHeartsFromPoints(frozenHealthPoints);
        for (int i = 0; i < frozenHealthHearts; i++) {
            Vector2i pos = mountHeartPositions[i];
            if (pos == null) {
                continue;
            }
            boolean isHalfHeart = i + 1 >= frozenHealthHearts && (frozenHealthPoints & 1) == 1; // is odd check

            if (isHalfHeart) {
                // flips the half heart around, since animal hearts are backwards
                context.blit(
                        HEART_OVERLAY_TEXTURE,
                        pos.x + 4, pos.y,
                        4, 0,
                        5, 10,
                        TEXTURE_WIDTH, TEXTURE_HEIGHT
                );
            } else {
                context.blit(
                        HEART_OVERLAY_TEXTURE,
                        pos.x, pos.y,
                        0, 0,
                        9, 10,
                        TEXTURE_WIDTH, TEXTURE_HEIGHT
                );
            }
        }
    }

    private static int getNumColdPoints(@NotNull LivingEntity entity, int maxDisplayHealth) {
        float freezingProgress = -entity.thermoo$getTemperatureScale();
        return Math.round(freezingProgress * maxDisplayHealth);
    }

    private static int getNumColdHeartsFromPoints(int frozenHealthPoints) {
        // number of whole hearts
        return Mth.ceil(frozenHealthPoints / 2.0f);
    }

    private FrozenHeartsOverlay() {

    }
}
