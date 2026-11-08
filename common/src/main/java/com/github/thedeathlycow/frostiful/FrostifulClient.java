/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

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
