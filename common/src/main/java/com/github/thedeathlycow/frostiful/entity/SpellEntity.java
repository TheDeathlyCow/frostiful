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

package com.github.thedeathlycow.frostiful.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class SpellEntity extends AbstractHurtingProjectile {

    private static final String AMPLIFIER_NBT_KEY = "EffectAmplifier";
    private static final String MAX_DISTANCE_NBT_KEY = "MaxDistance";
    private double maxDistance = Double.POSITIVE_INFINITY;
    @Nullable
    private Vec3 startPosition = null;

    public SpellEntity(EntityType<? extends SpellEntity> type, Level world, LivingEntity owner, Vec3 velocity) {
        this(type, world, owner, velocity, Double.POSITIVE_INFINITY);
    }

    public SpellEntity(EntityType<? extends SpellEntity> type, Level world, LivingEntity owner, Vec3 velocity, double maxDistance) {
        super(type, owner, velocity, world);
        this.maxDistance = maxDistance;
        this.snapTo(owner.getEyePosition(), this.getYRot(), this.getXRot());
    }

    protected SpellEntity(EntityType<? extends SpellEntity> entityType, Level world) {
        super(entityType, world);
    }

    protected abstract void applyEffectCloud();

    public void tick() {
        super.tick();

        if (!level().isClientSide() && this.isAlive()) {
            if (this.startPosition == null) {
                this.startPosition = this.position();
            }

            double distTravelledSqd = this.startPosition.distanceToSqr(this.position());
            if (distTravelledSqd > this.maxDistance * this.maxDistance) {
                this.applyEffectCloud();
            }
        }
    }

    @Override
    public void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!level().isClientSide() && this.isAlive()) {
            this.applyEffectCloud();
        }
    }

    public void addAdditionalSaveData(ValueOutput writeView) {
        super.addAdditionalSaveData(writeView);
        if (!Double.isInfinite(this.maxDistance)) {
            writeView.putDouble(MAX_DISTANCE_NBT_KEY, this.maxDistance);
        }
    }

    public void readAdditionalSaveData(ValueInput readView) {
        super.readAdditionalSaveData(readView);
        this.maxDistance = readView.getDoubleOr(MAX_DISTANCE_NBT_KEY, Double.POSITIVE_INFINITY);
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!level().isClientSide()) {
            this.applyEffectCloud();
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }


}
