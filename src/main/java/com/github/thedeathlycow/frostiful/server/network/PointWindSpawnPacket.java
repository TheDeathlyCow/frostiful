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
            Frostiful.location("point_wind_spawn")
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
