package com.github.thedeathlycow.frostiful.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public class FPacketCodecs {

    public static final StreamCodec<ByteBuf, Vec3> VEC3D = new StreamCodec<>() {
        public Vec3 decode(ByteBuf byteBuf) {
            return readVector3f(byteBuf);
        }

        public void encode(ByteBuf byteBuf, Vec3 vec) {
            writeVector3f(byteBuf, vec);
        }
    };

    public static Vec3 readVector3f(ByteBuf buf) {
        return new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void writeVector3f(ByteBuf buf, Vec3 vector) {
        buf.writeDouble(vector.x());
        buf.writeDouble(vector.y());
        buf.writeDouble(vector.z());
    }

    private FPacketCodecs() {
    }

}
