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

package com.github.thedeathlycow.frostiful.neoforge;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.Biter;
import com.github.thedeathlycow.frostiful.entity.Chillager;
import com.github.thedeathlycow.frostiful.entity.frostologer.Frostologer;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod(Frostiful.MODID)
public class FrostifulNeoforgeMod {
    public FrostifulNeoforgeMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(FrostifulNeoforgeMod::registerEntityTypeAttributes);
    }

    private static void registerEntityTypeAttributes(EntityAttributeCreationEvent event) {
        event.put(FEntityTypes.FROSTOLOGER, Frostologer.createFrostologerAttributes().build());
        event.put(FEntityTypes.CHILLAGER, Chillager.createChillagerAttributes().build());
        event.put(FEntityTypes.BITER, Biter.createBiterAttributes().build());
    }
}