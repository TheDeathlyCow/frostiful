package com.github.thedeathlycow.frostiful.server.network;

import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.frostiful.survival.wind.PointWindSpawnStrategy;
import com.github.thedeathlycow.frostiful.survival.wind.WindSpawnStrategies;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class PointWindSpawnPacketListener {

    private static final int PARTICLE_COUNT = 50;

    private static final ParticleEffect[] WIND_PARTICLES = new ParticleEffect[]{
            new WindParticleEffect(true),
            new WindParticleEffect(false),
            ParticleTypes.SNOWFLAKE
    };

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        Vec3d pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        ClientWorld world = client.world;

        client.execute(() -> {
            displayWind(world, pos);
        });
    }

    private static void displayWind(ClientWorld world, Vec3d pos) {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            for (ParticleEffect particleEffect : WIND_PARTICLES) {
                addParticle(particleEffect, world, pos);
            }
        }

        world.playSound(
                pos.x, pos.y, pos.z,
                FSoundEvents.ENTITY_WIND_BLOW,
                SoundCategory.AMBIENT,
                0.75f,
                0.9f + world.random.nextFloat() / 3,
                true
        );
    }

    private static void addParticle(ParticleEffect particleEffect, ClientWorld world, Vec3d origin) {
        double vx = world.random.nextGaussian() * 0.02;
        double vy = world.random.nextGaussian() * 0.02;
        double vz = world.random.nextGaussian() * 0.02;
        Vec3d rPos = PointWindSpawnStrategy.randomParticlePos(origin, world.random);
        world.addParticle(
                particleEffect,
                rPos.x, rPos.y, rPos.z,
                vx, vy, vz
        );
    }

    private PointWindSpawnPacketListener() {

    }

}
