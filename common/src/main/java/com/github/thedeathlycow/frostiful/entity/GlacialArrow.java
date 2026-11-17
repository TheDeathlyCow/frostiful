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
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FMobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class GlacialArrow extends AbstractArrow {
    private int freezingDuration = FrostifulConfigYACL.temperatureSourceSettings().glacialArrowFreezingEffectDuration();

    private static final String FREEZING_DURATION_NBT_KEY = "freezing_duration";

    public GlacialArrow(EntityType<? extends GlacialArrow> entityType, Level world) {
        super(entityType, world);
    }

    public GlacialArrow(Level world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FEntityTypes.GLACIAL_ARROW, x, y, z, world, stack, shotFrom);
    }

    public GlacialArrow(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FEntityTypes.GLACIAL_ARROW, owner, world, stack, shotFrom);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(FItems.GLACIAL_ARROW);
    }

    @Override
    public void tick() {
        super.tick();

        Level world = level();
        if (world.isClientSide() && !this.isInGround()) {
            world.addParticle(
                    ParticleTypes.SNOWFLAKE,
                    this.getX(), this.getY(), this.getZ(),
                    0.0D, 0.0D, 0.0D
            );
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        target.addEffect(new MobEffectInstance(FMobEffects.FREEZING, this.freezingDuration), this);
    }

    @Override
    public void readAdditionalSaveData(ValueInput readView) {
        super.readAdditionalSaveData(readView);
        this.freezingDuration = readView.getIntOr(FREEZING_DURATION_NBT_KEY, 20);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput writeView) {
        super.addAdditionalSaveData(writeView);
        writeView.putInt(FREEZING_DURATION_NBT_KEY, this.freezingDuration);
    }
}
