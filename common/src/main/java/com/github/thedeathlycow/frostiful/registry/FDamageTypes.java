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
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class FDamageTypes {
    public static final ResourceKey<DamageType> FALLING_ICICLE =  ResourceKey.create(Registries.DAMAGE_TYPE, Frostiful.id("falling_icicle"));
    public static final ResourceKey<DamageType> ICICLE =  ResourceKey.create(Registries.DAMAGE_TYPE, Frostiful.id("icicle"));
    public static final ResourceKey<DamageType> ICE_SKATE =  ResourceKey.create(Registries.DAMAGE_TYPE, Frostiful.id("ice_skate"));
    public static final ResourceKey<DamageType> BROKEN_ICE =  ResourceKey.create(Registries.DAMAGE_TYPE, Frostiful.id("broken_ice"));
    public static final ResourceKey<DamageType> MELT =  ResourceKey.create(Registries.DAMAGE_TYPE, Frostiful.id("melt"));

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(BROKEN_ICE, new DamageType("frostiful.brokenIce", 0.1f));
        context.register(FALLING_ICICLE, new DamageType("frostiful.fallingIcicle", 0.1f));
        context.register(ICE_SKATE, new DamageType("frostiful.ice_skate", 0.1f));
        context.register(ICICLE, new DamageType("frostiful.icicle", 0f));
        context.register(MELT, new DamageType("frostiful.melt", 0.1f));
    }

    private FDamageTypes() {
    }
}
