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

package com.github.thedeathlycow.frostiful.fabric;

import com.github.thedeathlycow.frostiful.entity.Biter;
import com.github.thedeathlycow.frostiful.entity.Chillager;
import com.github.thedeathlycow.frostiful.entity.frostologer.Frostologer;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class FrostifulFabric implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        FabricDefaultAttributeRegistry.register(FEntityTypes.FROSTOLOGER, Frostologer.createFrostologerAttributes());
        FabricDefaultAttributeRegistry.register(FEntityTypes.CHILLAGER, Chillager.createChillagerAttributes());
        FabricDefaultAttributeRegistry.register(FEntityTypes.BITER, Biter.createBiterAttributes());
    }
}