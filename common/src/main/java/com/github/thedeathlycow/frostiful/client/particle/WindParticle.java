/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.client.particle;

import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;


public class WindParticle extends SingleQuadParticle {
    private final SpriteSet spriteProvider;

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
    public void extract(QuadParticleRenderState particleTypeRenderState, Camera camera, float partialTickTime) {
        var rotation = new Quaternionf();

        rotation.identity();
        this.extractRotatedQuad(particleTypeRenderState, camera, rotation, partialTickTime);

        rotation.rotationY(Mth.PI);
        // flip the u coordinates to mirror the sprite properly
        this.extractRotatedQuad(particleTypeRenderState, camera, rotation, partialTickTime, this.getU1(), this.getU0(), this.getV0(), this.getV1());
    }

    @Override
    protected Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    protected void extractRotatedQuad(
            final QuadParticleRenderState particleTypeRenderState,
            final Camera camera,
            final Quaternionf rotation,
            final float partialTickTime,
            final float u0, final float u1,
            final float v0, final float v1
    ) {
        Vec3 pos = camera.position();
        float x = (float)(Mth.lerp(partialTickTime, this.xo, this.x) - pos.x());
        float y = (float)(Mth.lerp(partialTickTime, this.yo, this.y) - pos.y());
        float z = (float)(Mth.lerp(partialTickTime, this.zo, this.z) - pos.z());

        particleTypeRenderState.add(
                this.getLayer(),
                x, y, z,
                rotation.x, rotation.y, rotation.z, rotation.w,
                this.getQuadSize(partialTickTime),
                u0, u1,
                v0, v1,
                ARGB.colorFromFloat(this.alpha, this.rCol, this.gCol, this.bCol),
                this.getLightCoords(partialTickTime)
        );
    }

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
