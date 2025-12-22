package com.github.thedeathlycow.frostiful.client.particle;

import com.github.thedeathlycow.frostiful.particle.HeatDrainParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

@Environment(EnvType.CLIENT)
public class HeatDrainParticle extends RisingParticle {

    protected HeatDrainParticle(ClientLevel clientWorld, double x, double y, double z, double vx, double vy, double vz, Vec3 destination) {
        super(clientWorld, x, y, z, vx, vy, vz);

        this.xd = destination.x - this.x;
        this.yd = destination.y - this.y;
        this.zd = destination.z - this.z;

        final double slowFactor = 0.1;

        this.xd *= slowFactor * this.friction;
        this.yd *= slowFactor * this.friction;
        this.zd *= slowFactor * this.friction;
        this.lifetime = this.random.nextIntBetweenInclusive(1, 10);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.quadSize = 0.5f * random.nextFloat();
        this.gCol *= random.nextFloat(0.5f, 1.0f);
    }

    @Override
    public int getLightColor(float tickDelta) {
        int brightness = super.getLightColor(tickDelta);
        int red = brightness >> 16 & 0xFF;
        return 0x0000F0 | red << 16;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<HeatDrainParticleEffect> {

        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(HeatDrainParticleEffect parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            HeatDrainParticle heatDrainParticle = new HeatDrainParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters.destination());
            heatDrainParticle.pickSprite(this.spriteProvider);
            return heatDrainParticle;
        }
    }
}
