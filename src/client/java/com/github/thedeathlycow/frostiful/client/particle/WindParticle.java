package com.github.thedeathlycow.frostiful.client.particle;

import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class WindParticle extends SingleQuadParticle {
    private static final Vector3f FROM = Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
    private static final Vector3f TO = new Vector3f(-1.0F, -1.0F, 0.0F);

    private final SpriteSet spriteProvider;

    private static final Quaternionf FRONT_ROTATION = new Quaternionf().rotationX(-Mth.PI);
    private static final Quaternionf BACK_ROTATION = new Quaternionf().rotationYXZ(-Mth.PI, Mth.PI, 0.0f);

    protected WindParticle(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider.first());
        this.spriteProvider = spriteProvider;
        this.xd *= 2;
        this.quadSize *= 3;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteProvider);
    }

    @Override
    public void extract(QuadParticleRenderState submittable, Camera camera, float tickProgress) {
        // flip so that both faces are rendered in the same direction in the absolute position of the world
        this.quadSize *= -1;
        this.extractRotatedQuad(submittable, camera, FRONT_ROTATION, tickProgress);

        // flip back to normal
        this.quadSize *= -1;
        this.extractRotatedQuad(submittable, camera, BACK_ROTATION, tickProgress);
    }

    @Override
    protected Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<WindParticleEffect> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        @Nullable
        public Particle createParticle(
                WindParticleEffect parameters,
                ClientLevel world,
                double x, double y, double z,
                double velocityX, double velocityY, double velocityZ,
                RandomSource random
        ) {
            return new WindParticle(world, x, y, z, this.spriteProvider);
        }
    }
}
