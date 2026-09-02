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
import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureChange;
import com.github.thedeathlycow.thermoo.api.core.v2.source.BuiltinTemperatureSources;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class FreezingWindEntity extends WindEntity {
    private int temperatureChange;

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, Level world) {
        super(type, world);
        this.temperatureChange = FrostifulConfigYACL.temperatureSourceSettings().freezingWindTemperatureChange();
    }

    @Override
    public void onEntityCollision(LivingEntity entity) {
        super.onEntityCollision(entity);
        freezeEntity(entity, this.temperatureChange, this);
    }

    public static void freezeEntity(LivingEntity entity, int temperatureChange, @Nullable FreezingWindEntity source) {
        if (entity.getType() == EntityType.PLAYER) {
            BuiltinTemperatureSources sources = entity.level().thermoo$temperatureSources();

            TemperatureChange changeContext = source != null
                    ? sources.create(TemperatureSources.ACTIVE, source)
                    : sources.active();

            entity.thermoo$addTemperature(temperatureChange, changeContext);
        }
    }

    protected ParticleOptions getDustParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput readView) {
        super.readAdditionalSaveData(readView);
        this.temperatureChange = readView.getIntOr("temperature_change", FrostifulConfigYACL.temperatureSourceSettings().freezingWindTemperatureChange());
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput writeView) {
        super.addAdditionalSaveData(writeView);

        writeView.putInt("temperature_change", this.temperatureChange);
    }
}
