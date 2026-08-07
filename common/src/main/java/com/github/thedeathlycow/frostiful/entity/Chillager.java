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

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

public class Chillager extends Pillager {
    public Chillager(EntityType<? extends Chillager> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createChillagerAttributes() {
        return Pillager.createAttributes()
                .add(ThermooAttributes.MIN_TEMPERATURE, 45.0)
                .add(ThermooAttributes.FROST_RESISTANCE, 10.0);
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            amount *= FrostifulConfigYACL.entitySettings().chillagerFireDamageMultiplier();
        }

        return super.hurtServer(world, source, amount);
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt lightning) {
        if (level.getDifficulty() != Difficulty.PEACEFUL) {
            Frostiful.LOGGER.info("Chillager {} was struck by lightning {}.", this, lightning);
            this.convertTo(
                    FEntityTypes.FROSTOLOGER,
                    ConversionParams.single(this, false, true) ,
                    frostologer -> {
                        frostologer.populateDefaultEquipmentSlots(level.getRandom(), level.getCurrentDifficultyAt(frostologer.blockPosition()));
                    }
            );
        } else {
            super.thunderHit(level, lightning);
        }
    }

    @Override
    public ItemStack getProjectile(ItemStack stack) {
        if (stack.getItem() instanceof ProjectileWeaponItem rangedWeaponItem) {
            ItemStack itemStack = ProjectileWeaponItem.getHeldProjectile(
                    this,
                    rangedWeaponItem.getSupportedHeldProjectiles()
            );

            return itemStack.isEmpty() ? new ItemStack(FItems.GLACIAL_ARROW) : itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FSoundEvents.ENTITY_CHILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FSoundEvents.ENTITY_CHILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FSoundEvents.ENTITY_CHILLAGER_HURT;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return FSoundEvents.ENTITY_CHILLAGER_CELEBRATE;
    }

}
