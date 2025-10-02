package com.github.thedeathlycow.frostiful.client.particle;

import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class WindParticle extends BillboardParticle {

    private static final Vector3f FROM = Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
    private static final Vector3f TO = new Vector3f(-1.0F, -1.0F, 0.0F);

    private final SpriteProvider spriteProvider;

    private static final Quaternionf FRONT_ROTATION = new Quaternionf().rotationX(-MathHelper.PI);
    private static final Quaternionf BACK_ROTATION = new Quaternionf().rotationYXZ(-MathHelper.PI, MathHelper.PI, 0.0f);

    protected WindParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider.getFirst());
        this.spriteProvider = spriteProvider;
        this.velocityX *= 2;
        this.scale *= 3;
    }

    @Override
    public void tick() {
        super.tick();
        this.updateSprite(this.spriteProvider);
    }

    @Override
    public void render(BillboardParticleSubmittable submittable, Camera camera, float tickProgress) {
        // flip so that both faces are rendered in the same direction in the absolute position of the world
        this.scale *= -1;
        this.render(submittable, camera, FRONT_ROTATION, tickProgress);

        // flip back to normal
        this.scale *= -1;
        this.render(submittable, camera, BACK_ROTATION, tickProgress);
    }

    @Override
    protected RenderType getRenderType() {
        return BillboardParticle.RenderType.PARTICLE_ATLAS_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<WindParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        @Nullable
        public Particle createParticle(
                WindParticleEffect parameters,
                ClientWorld world,
                double x, double y, double z,
                double velocityX, double velocityY, double velocityZ,
                Random random
        ) {
            return new WindParticle(world, x, y, z, this.spriteProvider);
        }
    }
}
