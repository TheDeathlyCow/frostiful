package com.github.thedeathlycow.frostiful;

import com.github.thedeathlycow.frostiful.client.FrozenHeartsOverlay;
import com.github.thedeathlycow.frostiful.client.config.FrostifulClientConfig;
import com.github.thedeathlycow.frostiful.client.mixin.accessor.SpecialModelRenderersAccessor;
import com.github.thedeathlycow.frostiful.client.network.PointWindSpawnPacketListener;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.registry.FEntityRenderers;
import com.github.thedeathlycow.frostiful.client.registry.FParticleFactoryRegistry;
import com.github.thedeathlycow.frostiful.client.render.entity.FrostWandItemRenderer;
import com.github.thedeathlycow.frostiful.compat.FoodIntegration;
import com.github.thedeathlycow.frostiful.server.network.PointWindSpawnPacket;
import com.github.thedeathlycow.thermoo.api.client.v1.StatusBarOverlayRenderEvents;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.client.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FrostifulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        FrostifulClientConfig.initialize();
        FParticleFactoryRegistry.initialize();
        FEntityModelLayers.initialize();
        FEntityRenderers.initialize();

        SpecialModelRenderersAccessor.getIdMapper().put(Frostiful.id("frost_wand"), FrostWandItemRenderer.Unbaked.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(
                PointWindSpawnPacket.PACKET_ID,
                new PointWindSpawnPacketListener()
        );
        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.register(FrozenHeartsOverlay::afterHealthBar);
        StatusBarOverlayRenderEvents.AFTER_MOUNT_HEALTH_BAR.register(FrozenHeartsOverlay::afterMountHealthBar);
        ItemTooltipCallback.EVENT.register(FoodIntegration::appendWarmthTooltip);

        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
