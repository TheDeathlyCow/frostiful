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

package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.particle.HeatDrainParticle;
import com.github.thedeathlycow.frostiful.client.particle.WindParticle;
import com.github.thedeathlycow.frostiful.registry.FParticleTypes;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;


public class FParticleFactoryRegistry {

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful particle factories");
        registerFactory(FParticleTypes.HEAT_DRAIN, HeatDrainParticle.Factory::new);
        registerFactory(FParticleTypes.WIND, WindParticle.Factory::new);
        registerFactory(FParticleTypes.WIND_FLIPPED, WindParticle.Factory::new);
    }

    private static <T extends ParticleOptions> void registerFactory(ParticleType<T> particle, ParticleProviderRegistry.PendingParticleProvider<T> factory) {
        ParticleProviderRegistry.getInstance().register(particle, factory);
    }

    private FParticleFactoryRegistry() {

    }

}
