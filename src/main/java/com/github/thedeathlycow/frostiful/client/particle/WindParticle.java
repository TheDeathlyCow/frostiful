package com.github.thedeathlycow.frostiful.client.particle;

import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class WindParticle extends TextureSheetParticle {

    private static final Vector3f FROM = Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
    private static final Vector3f TO = new Vector3f(-1.0F, -1.0F, 0.0F);

    private final SpriteSet spriteProvider;

    private static final Quaternionf FRONT_ROTATION = new Quaternionf().rotationX(-Mth.PI);
    private static final Quaternionf BACK_ROTATION = new Quaternionf().rotationYXZ(-Mth.PI, Mth.PI, 0.0f);

    protected WindParticle(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider) {
        super(clientWorld, x, y, z);
        this.spriteProvider = spriteProvider;
        this.xd *= 2;
        this.quadSize *= 3;
        this.setSpriteFromAge(spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteProvider);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.buildGeometry(vertexConsumer, camera, tickDelta, true, (quaternion) -> {
            quaternion.mul(FRONT_ROTATION);
        });
        this.buildGeometry(vertexConsumer, camera, tickDelta, false, (quaternion) -> {
            quaternion.mul(BACK_ROTATION);
        });
    }

    private void buildGeometry(
            VertexConsumer vertexConsumer,
            Camera camera,
            float tickDelta,
            boolean flip,
            Consumer<Quaternionf> rotator
    ) {
        Vec3 cameraPos = camera.getPosition();
        float dx = (float) (Mth.lerp(tickDelta, this.xo, this.x) - cameraPos.x());
        float dy = (float) (Mth.lerp(tickDelta, this.yo, this.y) - cameraPos.y());
        float dz = (float) (Mth.lerp(tickDelta, this.zo, this.z) - cameraPos.z());
        var quaternion = new Quaternionf().setAngleAxis(0.0f, FROM.x(), FROM.y(), FROM.z());
        rotator.accept(quaternion);
        TO.rotate(quaternion);
        var points = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)
        };

        float size = this.getQuadSize(tickDelta) * (flip ? -1 : 1);

        for (int i = 0; i < 4; ++i) {
            Vector3f point = points[i];
            point.rotate(quaternion);
            point.mul(size);
            point.add(dx, dy, dz);
        }

        int brightness = this.getLightColor(tickDelta);
        this.vertex(vertexConsumer, points[0], this.getU1(), this.getV1(), brightness);
        this.vertex(vertexConsumer, points[1], this.getU1(), this.getV0(), brightness);
        this.vertex(vertexConsumer, points[2], this.getU0(), this.getV0(), brightness);
        this.vertex(vertexConsumer, points[3], this.getU0(), this.getV1(), brightness);
    }

    private void vertex(VertexConsumer vertexConsumer, Vector3f pos, float u, float v, int light) {
        vertexConsumer.addVertex(pos.x(), pos.y(), pos.z())
                .setUv(u, v)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(light);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<WindParticleEffect> {

        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(WindParticleEffect parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new WindParticle(world, x, y, z, this.spriteProvider);
        }
    }
}
