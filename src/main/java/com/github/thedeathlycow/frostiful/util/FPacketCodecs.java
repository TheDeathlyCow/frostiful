package com.github.thedeathlycow.frostiful.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.Vec3d;

public class FPacketCodecs {

    public static final PacketCodec<ByteBuf, Vec3d> VEC3D = new PacketCodec<>() {
        public Vec3d decode(ByteBuf byteBuf) {
            return readVector3f(byteBuf);
        }

        public void encode(ByteBuf byteBuf, Vec3d vec) {
            writeVector3f(byteBuf, vec);
        }
    };

    public static Vec3d readVector3f(ByteBuf buf) {
        return new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void writeVector3f(ByteBuf buf, Vec3d vector) {
        buf.writeDouble(vector.getX());
        buf.writeDouble(vector.getY());
        buf.writeDouble(vector.getZ());
    }

    private FPacketCodecs() {
    }

}
