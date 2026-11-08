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
import com.github.thedeathlycow.frostiful.block.transformer.BlockTransformer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class FrostifulRegistries {
    public static final ResourceKey<Registry<BlockTransformer>> BLOCK_TRANSFORMER_KEY = ResourceKey.createRegistryKey(Frostiful.id("block_transformer"));

    public static final ResourceKey<Registry<BlockTransformer.Type<?>>> BLOCK_TRANSFORMER_TYPE_KEY = ResourceKey.createRegistryKey(Frostiful.id("block_transformer_type"));

    public static final Registry<BlockTransformer.Type<?>> BLOCK_TRANSFORMER_TYPE = FabricRegistryBuilder.create(BLOCK_TRANSFORMER_TYPE_KEY).buildAndRegister();

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful registries");

        DynamicRegistries.register(BLOCK_TRANSFORMER_KEY, BlockTransformer.ELEMENT_CODEC);
    }

    private FrostifulRegistries() {

    }
}