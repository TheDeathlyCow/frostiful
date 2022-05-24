package com.github.thedeathlycow.frostiful.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class HeatDrainParticleFactory extends FlameParticle.Factory {
    public HeatDrainParticleFactory(SpriteProvider spriteProvider) {
        super(spriteProvider);
    }

    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ) {
        Particle particle = super.createParticle(defaultParticleType, clientWorld, posX, posY, posZ, velocityX, velocityY, velocityZ);
        assert particle != null;
        particle.setMaxAge(clientWorld.random.nextInt(1, 10));

        velocityX += (Math.random() * 2.0D - 1.0D) * 0.04;
        velocityY += (Math.random() * 2.0D - 1.0D) * 0.04;
        velocityZ += (Math.random() * 2.0D - 1.0D) * 0.04;

        velocityY = -Math.abs(velocityY);

        particle.setVelocity(velocityX, velocityY, velocityZ);
        return particle;
    }
}
