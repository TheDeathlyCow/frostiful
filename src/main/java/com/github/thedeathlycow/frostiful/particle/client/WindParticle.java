package com.github.thedeathlycow.frostiful.particle.client;

import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class WindParticle extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    protected WindParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z);
        this.spriteProvider = spriteProvider;
        this.velocityX *= 2;
        this.setSpriteForAge(spriteProvider);
    }

    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<WindParticleEffect> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(WindParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new WindParticle(world, x, y, z, this.spriteProvider);
        }
    }
}
