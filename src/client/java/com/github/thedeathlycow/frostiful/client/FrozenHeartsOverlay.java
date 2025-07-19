package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.thermoo.api.client.HeartBarContext;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class FrozenHeartsOverlay {

    public static final Identifier HEART_OVERLAY_TEXTURE = Frostiful.id("textures/gui/cold_heart_overlay.png");

    private static final int TEXTURE_WIDTH = 18;

    private static final int TEXTURE_HEIGHT = 10;

    public static void afterHealthBar(
            DrawContext context,
            PlayerEntity player,
            HeartBarContext heartBarContext
    ) {
        FrostifulConfig config = Frostiful.getConfig();
        if (!config.clientConfig.doColdHeartOverlay() || player.thermoo$isWarm()) {
            return;
        }

        final int coldHalfHearts = getColdHalfHearts(player, heartBarContext.positions().size());
        final int coldHearts = getColdHeartsFromHalfHearts(coldHalfHearts);
        final boolean drawHalfHeartAtEnd = coldHalfHearts % 2 != 0;

        int heartsRendered = 0;

        for (Vector2i pos : heartBarContext.positions()) {
            if (heartsRendered >= coldHearts) {
                break;
            }
            boolean isHalfHeart = drawHalfHeartAtEnd && heartsRendered == coldHearts - 1;

            int u = isHalfHeart ? 9 : 0;
            context.drawTexture(
                    RenderPipelines.GUI_TEXTURED,
                    HEART_OVERLAY_TEXTURE,
                    pos.x, pos.y,
                    u, 0,
                    9, 10,
                    TEXTURE_WIDTH, TEXTURE_HEIGHT
            );
        }
    }

    public static void afterMountHealthBar(
            DrawContext context,
            PlayerEntity player,
            LivingEntity mount,
            HeartBarContext heartBarContext
    ) {
        FrostifulConfig config = Frostiful.getConfig();
        if (!config.clientConfig.doColdHeartOverlay() || mount.thermoo$isWarm()) {
            return;
        }

        final int coldHalfHearts = getColdHalfHearts(mount, heartBarContext.positions().size());
        final int coldHearts = getColdHeartsFromHalfHearts(coldHalfHearts);
        final boolean drawHalfHeartAtEnd = coldHalfHearts % 2 != 0;

        int heartsRendered = 0;

        for (Vector2i pos : heartBarContext.positions()) {
            if (heartsRendered >= coldHearts) {
                break;
            }
            boolean isHalfHeart = drawHalfHeartAtEnd && heartsRendered == coldHearts - 1;

            if (isHalfHeart) {
                // flips the half heart around, since animal hearts are backwards
                context.drawTexture(
                        RenderPipelines.GUI_TEXTURED,
                        HEART_OVERLAY_TEXTURE,
                        pos.x() + 4, pos.y(),
                        4, 0,
                        5, 10,
                        TEXTURE_WIDTH, TEXTURE_HEIGHT
                );
            } else {
                context.drawTexture(
                        RenderPipelines.GUI_TEXTURED,
                        HEART_OVERLAY_TEXTURE,
                        pos.x(), pos.y(),
                        0, 0,
                        9, 10,
                        TEXTURE_WIDTH, TEXTURE_HEIGHT
                );
            }

            heartsRendered++;
        }
    }

    private static int getColdHalfHearts(@NotNull LivingEntity entity, int maxDisplayHealth) {
        float freezingProgress = -entity.thermoo$getTemperatureScale();
        return Math.round(freezingProgress * maxDisplayHealth);
    }

    private static int getColdHeartsFromHalfHearts(int frozenHealthPoints) {
        // number of whole hearts
        return MathHelper.ceil(frozenHealthPoints / 2.0f);
    }

    private FrozenHeartsOverlay() {

    }
}
