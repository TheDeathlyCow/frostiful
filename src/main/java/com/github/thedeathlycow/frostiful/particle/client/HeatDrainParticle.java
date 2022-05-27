package com.github.thedeathlycow.frostiful.particle.client;

import com.github.thedeathlycow.frostiful.particle.HeatDrainParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class HeatDrainParticle extends AbstractSlowingParticle {

    protected HeatDrainParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz, Vec3d destination) {
        super(clientWorld, x, y, z, vx, vy, vz);

        this.velocityX = destination.x - this.x;
        this.velocityY = destination.y - this.y;
        this.velocityZ = destination.z - this.z;

        final double velocityMultiplier = 0.1;

        this.velocityX *= velocityMultiplier;
        this.velocityY *= velocityMultiplier;
        this.velocityZ *= velocityMultiplier;
        this.maxAge = this.random.nextInt(1, 10);
        this.scale = 0.6f * this.random.nextFloat(0.5f, 1.0f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<HeatDrainParticleEffect> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(HeatDrainParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            HeatDrainParticle heatDrainParticle = new HeatDrainParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters.getDestination());
            heatDrainParticle.setSprite(this.spriteProvider);
            return heatDrainParticle;
        }
    }
}
