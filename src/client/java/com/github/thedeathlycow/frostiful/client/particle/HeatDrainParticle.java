package com.github.thedeathlycow.frostiful.client.particle;

import com.github.thedeathlycow.frostiful.particle.HeatDrainParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

@Environment(EnvType.CLIENT)
public class HeatDrainParticle extends AbstractSlowingParticle {
    protected HeatDrainParticle(
            ClientWorld clientWorld,
            double x, double y, double z,
            double vx, double vy, double vz,
            SpriteProvider spriteProvider,
            Vec3d destination
    ) {
        super(clientWorld, x, y, z, vx, vy, vz, spriteProvider.getFirst());

        this.velocityX = destination.x - this.x;
        this.velocityY = destination.y - this.y;
        this.velocityZ = destination.z - this.z;

        final double slowFactor = 0.1;

        this.velocityX *= slowFactor * this.velocityMultiplier;
        this.velocityY *= slowFactor * this.velocityMultiplier;
        this.velocityZ *= slowFactor * this.velocityMultiplier;
        this.maxAge = this.random.nextBetween(1, 10);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.scale = 0.5f * random.nextFloat();
        this.green *= random.nextFloat(0.5f, 1.0f);
    }

    @Override
    public int getBrightness(float tickDelta) {
        int brightness = super.getBrightness(tickDelta);
        int red = brightness >> 16 & 0xFF;
        return 0x0000F0 | red << 16;
    }

    @Override
    protected RenderType getRenderType() {
        return BillboardParticle.RenderType.PARTICLE_ATLAS_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<HeatDrainParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(
                HeatDrainParticleEffect parameters,
                ClientWorld world,
                double x, double y, double z,
                double velocityX, double velocityY, double velocityZ,
                Random random
        ) {
            return new HeatDrainParticle(
                    world,
                    x, y, z,
                    velocityX, velocityY, velocityZ,
                    this.spriteProvider,
                    parameters.destination()
            );
        }
    }
}
