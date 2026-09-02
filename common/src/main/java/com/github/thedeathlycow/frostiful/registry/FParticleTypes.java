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

package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.particle.HeatDrainParticleEffect;
import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class FParticleTypes {

    public static final ParticleType<HeatDrainParticleEffect> HEAT_DRAIN = register(
            "heat_drain",
            FabricParticleTypes.complex(
                    HeatDrainParticleEffect.CODEC,
                    HeatDrainParticleEffect.PACKET_CODEC
            )
    );
    public static final ParticleType<WindParticleEffect> WIND = register(
            "wind",
            FabricParticleTypes.complex(
                    WindParticleEffect.CODEC,
                    WindParticleEffect.PACKET_CODEC
            )
    );
    public static final ParticleType<WindParticleEffect> WIND_FLIPPED = register(
            "wind_flipped",
            FabricParticleTypes.complex(
                    WindParticleEffect.CODEC,
                    WindParticleEffect.PACKET_CODEC
            )
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful particle types");
    }

    private static <T extends ParticleOptions> ParticleType<T> register(String name, ParticleType<T> particle) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, Frostiful.id(name), particle);
    }

    private FParticleTypes() {
    }

}
