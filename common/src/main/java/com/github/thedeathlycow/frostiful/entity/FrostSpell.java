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

import com.github.thedeathlycow.frostiful.registry.FCriteria;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.survival.system.FrostRootSystem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class FrostSpell extends SpellEntity {

    private static final double EFFECT_CLOUD_SIZE = 3.0;

    public FrostSpell(Level world, LivingEntity owner, Vec3 velocity) {
        super(FEntityTypes.FROST_SPELL, world, owner, velocity);
    }

    public FrostSpell(Level world, LivingEntity owner, Vec3 velocity, double maxDistance) {
        super(FEntityTypes.FROST_SPELL, world, owner, velocity, maxDistance);
    }

    public FrostSpell(EntityType<? extends SpellEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void applyEffectCloud() {
        Level world = level();
        if (this.isRemoved() || world.isClientSide()) {
            return;
        }

        AABB box = this.getBoundingBox().inflate(EFFECT_CLOUD_SIZE, EFFECT_CLOUD_SIZE, EFFECT_CLOUD_SIZE);
        List<LivingEntity> targets = world.getEntitiesOfClass(LivingEntity.class, box);
        List<LivingEntity> targetsFrozen = new ArrayList<>();
        for (var target : targets) {
            Entity owner = this.getOwner();
            boolean isTargetable = owner == null || !target.getUUID().equals(owner.getUUID());
            if (isTargetable && this.applySingleTargetEffect(target)) {
                targetsFrozen.add(target);
            }
        }

        if (!targetsFrozen.isEmpty() && this.getOwner() instanceof ServerPlayer serverPlayer) {
            FCriteria.FROZEN_BY_FROST_WAND.trigger(serverPlayer, targetsFrozen);
        }

        world.playSound(
                null,
                this.getX(), this.getY(), this.getZ(),
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.AMBIENT,
                2.0f, 1.0f
        );

        ServerLevel serverWorld = (ServerLevel) world;

        serverWorld.sendParticles(
                ParticleTypes.EXPLOSION,
                this.getX(), this.getY(), this.getZ(),
                10,
                2.0, 2.0, 2.0,
                0.3
        );

        this.discard();
    }

    protected boolean applySingleTargetEffect(Entity target) {
        Level world = target.level();
        if (!world.isClientSide()) {
            if (FrostRootSystem.tryRootFromFrostWand(target, this.getOwner())) {
                world.playSound(
                        null,
                        target.getX(), target.getY(), target.getZ(),
                        FSoundEvents.ENTITY_FROST_SPELL_FREEZE, SoundSource.AMBIENT,
                        1.0f, 1.0f
                );
                return true;
            }
        }
        return false;
    }
}
