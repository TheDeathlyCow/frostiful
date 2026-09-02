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
import net.minecraft.resources.ResourceKey;

public final class FBlockTransformers {
    public static final ResourceKey<BlockTransformer> FROSTOLOGER_BLIZZARD_FREEZE = createKey("frostologer_blizzard_freeze");
    public static final ResourceKey<BlockTransformer> BLOW_OUT_FROM_WIND = createKey("blow_out_from_wind");

    private static ResourceKey<BlockTransformer> createKey(String name) {
        return ResourceKey.create(FrostifulRegistries.BLOCK_TRANSFORMER_KEY, Frostiful.id(name));
    }

    private FBlockTransformers() {

    }
}