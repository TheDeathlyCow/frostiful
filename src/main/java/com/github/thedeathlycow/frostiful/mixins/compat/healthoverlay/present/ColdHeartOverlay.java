package com.github.thedeathlycow.frostiful.mixins.compat.healthoverlay.present;

import com.github.thedeathlycow.frostiful.client.FrozenHeartsOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terrails.healthoverlay.ModConfiguration;
import terrails.healthoverlay.heart.Heart;
import terrails.healthoverlay.render.HeartRenderer;

import java.util.List;
import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(value = HeartRenderer.class, remap = false)
public abstract class ColdHeartOverlay {
    @Shadow private List<Heart> hearts;
    @Shadow private int lastHealth;
    @Shadow @Final private Random random;
    @Shadow @Final private MinecraftClient client;
    @Shadow private int displayHealth;
    private final int[] heartYPositions = new int[FrozenHeartsOverlay.MAX_COLD_HEARTS];
    private final int[] heartXPositions = new int[FrozenHeartsOverlay.MAX_COLD_HEARTS];

    @Inject(method = "renderPlayerHearts", at = @At("TAIL"))
    private void drawColdHeartOverlayBar(MatrixStack poseStack, PlayerEntity player, CallbackInfo ci) {
        // Had to replicate the iteration initialization and logic, in order to make it work, because Mixin witchery
        // is a science I don't excel into enough for that
        int currentHealth = lastHealth;
        long tickCount = this.client.inGameHud.getTicks();
        int maxHealth = Math.max((int)player.getMaxHealth(), Math.max(this.displayHealth, currentHealth));
        int regenerationIndex = (player.hasStatusEffect(StatusEffects.REGENERATION)) ?
                (int)tickCount % MathHelper.ceil(maxHealth + 5.0F) :
                -1;
        int xPos = this.client.getWindow().getScaledWidth() / 2 - 91;
        int yPos = this.client.getWindow().getScaledHeight() - 39;
        int maxIndex = Math.min(hearts.size(), FrozenHeartsOverlay.MAX_COLD_HEARTS);
        for (int index = 0; index < maxIndex; index++) {
            int regenOffset = index < 10 && index == regenerationIndex ? -2 : 0;
            int absorptionOffset = index > 9 ? -10 : 0;
            int yPosition = yPos + regenOffset + absorptionOffset;
            int xPosition = xPos + index % 10 * 8;
            if (ModConfiguration.absorptionOverHealth || index < 10) {
                if (currentHealth <= 4) {
                    yPosition += this.random.nextInt(2);
                }

                if (index == regenerationIndex) {
                    yPosition -= 2;
                }
            }
            // Because of some different conception of X/Y coordinates, the two names ended up switched between
            // the two mods, don't ask me why
            heartXPositions[index] = xPosition;
            heartYPositions[index] = yPosition;
        }
        FrozenHeartsOverlay.drawHeartOverlayBar(poseStack, player, heartXPositions, heartYPositions);
    }
}
