package com.github.thedeathlycow.frostiful.server.network;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PointWindSpawnPacket {

    public static final Identifier ID = Frostiful.id("point_wind_spawn");

    private static final double DISTANCE = 32;

    public static void sendToNearbyPlayersFromServer(ServerWorld serverWorld, Vec3d position) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeDouble(position.x);
        buf.writeDouble(position.y);
        buf.writeDouble(position.z);

        for (ServerPlayerEntity player : serverWorld.getPlayers()) {
            BlockPos playerPos = player.getBlockPos();
            if (playerPos.isWithinDistance(position, DISTANCE)) {
                ServerPlayNetworking.send(player, ID, buf);
            }
        }
    }

    private PointWindSpawnPacket() {

    }

}
