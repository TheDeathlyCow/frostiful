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

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.ItemSettings;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ThrownIcicle extends AbstractArrow {

    public ThrownIcicle(EntityType<? extends ThrownIcicle> entityType, Level world) {
        super(entityType, world);
    }

    public ThrownIcicle(Level world, double x, double y, double z, ItemStack stack) {
        super(FEntityTypes.THROWN_ICICLE, x, y, z, world, stack, stack);
    }

    public ThrownIcicle(Level world, LivingEntity owner, ItemStack stack) {
        super(FEntityTypes.THROWN_ICICLE, owner, world, stack, null);
    }

    @Override
    public void tick() {
        super.tick();
        Level world = level();
        if (world.isClientSide() && !this.isInGround()) {
            world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {

        if (entityHitResult.getEntity().getType() == FEntityTypes.FROSTOLOGER) {
            return;
        }

        ItemSettings config = FrostifulConfigYACL.itemSettings();

        float damage = entityHitResult.getEntity().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
                ? config.thrownIcicleVulnerableTypesDamage()
                : config.thrownIcicleDamage();
        this.setBaseDamage(damage);

        super.onHitEntity(entityHitResult);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        int freezeAmount = FrostifulConfigYACL.temperatureSourceSettings().thrownIcicleTemperatureChange();

        target.thermoo$addTemperature(
                freezeAmount,
                target.level().thermoo$temperatureSources().create(
                        TemperatureSources.ACTIVE,
                        this
                )
        );
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return FSoundEvents.ENTITY_THROWN_ICICLE_HIT;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(FItems.ICICLE);
    }
}
