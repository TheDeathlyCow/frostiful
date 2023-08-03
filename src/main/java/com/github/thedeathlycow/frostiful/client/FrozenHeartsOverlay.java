package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FrozenHeartsOverlay {

    public static final FrozenHeartsOverlay INSTANCE = new FrozenHeartsOverlay();

    public static final Identifier HEART_OVERLAY_TEXTURE = new Identifier(Frostiful.MODID, "textures/gui/cold_heart_overlay.png");
    public static final int MAX_COLD_HEARTS = 20;

    private final int[] heartXPositions = new int[MAX_COLD_HEARTS];
    private final int[] heartYPositions = new int[MAX_COLD_HEARTS];

    public void setHeartPos(int index, int xPos, int yPos) {
        if (index >= 0 && index < MAX_COLD_HEARTS) {
            this.heartXPositions[index] = xPos;
            this.heartYPositions[index] = yPos;
        }
    }

    public int getNumColdPoints(@NotNull PlayerEntity player) {
        float freezingProgress = player.thermoo$getTemperatureScale();
        if (freezingProgress >= 0f) {
            return 0;
        }
        freezingProgress = -freezingProgress;

        final float playerMaxHealth = player.getMaxHealth();

        // number of half cold hearts
        int frozenHealthPoints;
        // match the number of cold hearts to the display if hearts render is altered
        if (FrostifulIntegrations.isHeartsRenderOverridden()) {
            // 20 is the (expected) maximum number of health points that the render mod will display
            frozenHealthPoints = (int) (freezingProgress * Math.min(MAX_COLD_HEARTS, playerMaxHealth));
        } else {
            // max cold hearts is multiplied by 2 to covert to points
            frozenHealthPoints = (int) (freezingProgress * Math.min(MAX_COLD_HEARTS * 2.0f, playerMaxHealth));
        }
        return frozenHealthPoints;
    }

    public int getNumColdHeartsFromPoints(int frozenHealthPoints) {
        // number of whole hearts
        int frozenHealthHearts = MathHelper.ceil(frozenHealthPoints / 2.0f);

        return Math.min(MAX_COLD_HEARTS, frozenHealthHearts);
    }

    public void drawHeartOverlayBar(
            DrawContext context,
            PlayerEntity player
    ) {

        FrostifulConfig config = Frostiful.getConfig();
        if (!config.clientConfig.doColdHeartOverlay()) {
            return;
        }
        int frozenHealthPoints = getNumColdPoints(player);
        int frozenHealthHearts = getNumColdHeartsFromPoints(frozenHealthPoints);
        for (int m = 0; m < frozenHealthHearts; m++) {
            // is half heart if this is the last heart being rendered and we have an odd
            // number of frozen health points
            int x = heartXPositions[m];
            int y = heartYPositions[m];
            boolean isHalfHeart = m + 1 >= frozenHealthHearts && (frozenHealthPoints & 1) == 1; // is odd check

            int u = isHalfHeart ? 9 : 0;
            context.drawTexture(HEART_OVERLAY_TEXTURE, x, y, u, 0, 9, 10, 18, 10);
        }
    }

    private FrozenHeartsOverlay() {
    }
}