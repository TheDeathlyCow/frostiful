package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class FrozenHeartsOverlay {

    public static final Identifier HEART_OVERLAY_TEXTURE = Frostiful.id("textures/gui/cold_heart_overlay.png");

    public static void afterHealthBar(
            DrawContext context,
            PlayerEntity player,
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
            context.drawTexture(HEART_OVERLAY_TEXTURE, pos.x, pos.y, u, 0, 9, 10, 18, 10);
        }
    }

    private static int getNumColdPoints(@NotNull PlayerEntity player, int maxDisplayHealth) {
        float freezingProgress = -player.thermoo$getTemperatureScale();
        return MathHelper.ceil(freezingProgress * maxDisplayHealth);
    }

    private static int getNumColdHeartsFromPoints(int frozenHealthPoints) {
        // number of whole hearts
        return MathHelper.ceil(frozenHealthPoints / 2.0f);
    }

    private FrozenHeartsOverlay() {

    }
}
