package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.impl.client.HeartOverlayImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2i;
import terrails.colorfulhearts.api.fabric.ColorfulHeartsApi;
import terrails.colorfulhearts.api.fabric.event.FabHeartEvents;
import terrails.colorfulhearts.api.heart.Hearts;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

import java.util.Arrays;

public class ColorfulHeartsIntegration implements ColorfulHeartsApi {

    private final MinecraftClient client = MinecraftClient.getInstance();

    public ColorfulHeartsIntegration() {
        OverlayHeart frozenHearts = Hearts.OVERLAY_HEARTS.get(new Identifier("frozen"));
        if (frozenHearts != null) {
            FabHeartEvents.PRE_RENDER.register(event -> {
                ClientPlayerEntity player = client.player;
                if (player != null && player.thermoo$getTemperatureScale() <= -0.99) {
                    event.setOverlayHeart(frozenHearts);
                }
            });
        }

        FabHeartEvents.SINGLE_RENDER.register(event -> HeartOverlayImpl.INSTANCE.setHeartPosition(event.getIndex(), event.getX(), event.getY()));

        FabHeartEvents.POST_RENDER.register(event -> {
            Vector2i[] heartPositions = HeartOverlayImpl.INSTANCE.getHeartPositions();
            int displayHealth = Math.min(event.getHealth(), heartPositions.length);
            int maxDisplayHealth = Math.min(MathHelper.ceil(event.getMaxHealth()), heartPositions.length);

            StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.invoker()
                    .render(
                            event.getGuiGraphics(),
                            event.getPlayer(),
                            heartPositions,
                            displayHealth,
                            maxDisplayHealth
                    );
            Arrays.fill(HeartOverlayImpl.INSTANCE.getHeartPositions(), null);
        });
    }
}
