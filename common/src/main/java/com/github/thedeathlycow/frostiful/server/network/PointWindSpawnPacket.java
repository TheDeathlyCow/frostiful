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

package com.github.thedeathlycow.frostiful.server.network;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.util.FPacketCodecs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public record PointWindSpawnPacket(
        Vec3 position
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PointWindSpawnPacket> PACKET_ID = new CustomPacketPayload.Type<>(
            Frostiful.id("point_wind_spawn")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PointWindSpawnPacket> PACKET_CODEC = FPacketCodecs.VEC3D
            .map(PointWindSpawnPacket::new, PointWindSpawnPacket::position)
            .cast();

    public static void sendToNearbyPlayersFromServer(ServerLevel world, BlockPos spawnPos, Vec3 center) {
        var packet = new PointWindSpawnPacket(center);
        for (ServerPlayer player : PlayerLookup.tracking(world, spawnPos)) {
            ServerPlayNetworking.send(player, packet);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}
