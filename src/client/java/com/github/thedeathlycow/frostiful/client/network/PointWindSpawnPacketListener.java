package com.github.thedeathlycow.frostiful.client.network;

import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.server.network.PointWindSpawnPacket;
import com.github.thedeathlycow.frostiful.survival.wind.PointWindSpawnStrategy;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class PointWindSpawnPacketListener implements ClientPlayNetworking.PlayPayloadHandler<PointWindSpawnPacket> {

    private static final int PARTICLE_COUNT = 50;

    private static final ParticleOptions[] WIND_PARTICLES = new ParticleOptions[]{
            new WindParticleEffect(true),
            new WindParticleEffect(false),
            ParticleTypes.SNOWFLAKE,
            ParticleTypes.SNOWFLAKE,
            ParticleTypes.SNOWFLAKE
    };

    @Override
    public void receive(PointWindSpawnPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            displayWind(context.client().level, payload.position());
        });
    }

    private static void displayWind(ClientLevel world, Vec3 pos) {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            for (ParticleOptions particleEffect : WIND_PARTICLES) {
                addParticle(particleEffect, world, pos);
            }
        }

        world.playLocalSound(
                pos.x, pos.y, pos.z,
                FSoundEvents.ENTITY_WIND_BLOW,
                SoundSource.AMBIENT,
                0.75f,
                0.9f + world.getRandom().nextFloat() / 3,
                true
        );
    }

    private static void addParticle(ParticleOptions particleEffect, ClientLevel world, Vec3 origin) {
        double vx = world.getRandom().nextGaussian() * 0.02;
        double vy = world.getRandom().nextGaussian() * 0.02;
        double vz = world.getRandom().nextGaussian() * 0.02;
        Vec3 rPos = PointWindSpawnStrategy.randomParticlePos(origin, world.getRandom());
        world.addParticle(
                particleEffect,
                rPos.x, rPos.y, rPos.z,
                vx, vy, vz
        );
    }


}
