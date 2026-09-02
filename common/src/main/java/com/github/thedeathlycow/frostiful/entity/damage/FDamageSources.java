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

package com.github.thedeathlycow.frostiful.entity.damage;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;


/**
 * Extension of {@link net.minecraft.world.damagesource.DamageSources}. Interface-injected into that class
 * so that it can be used along-side it.
 */
public interface FDamageSources {

    DamageSource frostiful$fallingIcicle(Entity attacker);

    DamageSource frostiful$icicle();

    DamageSource frostiful$iceSkate(Entity attacker);

    DamageSource frostiful$brokenIce(Entity attacker);

    static FDamageSources getDamageSources(Level world) {
        return (FDamageSources) world.damageSources();
    }

}
