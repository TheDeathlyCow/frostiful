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

package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public final class ExtinguishFlameIfFireTransformer implements BlockTransformer {
    private static final ExtinguishFlameIfFireTransformer INSTANCE = new ExtinguishFlameIfFireTransformer();

    public static final MapCodec<ExtinguishFlameIfFireTransformer> CODEC = MapCodec.unit(() -> INSTANCE);

    public static ExtinguishFlameIfFireTransformer of() {
        return INSTANCE;
    }

    @Override
    public Optional<BlockState> transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        if (original.is(FBlockTags.IS_OPEN_FLAME)) {
            return Optional.of(original.getFluidState().createLegacyBlock());
        } else if (
                original.is(FBlockTags.HAS_OPEN_FLAME)
                        && original.hasProperty(BlockStateProperties.LIT)
                        && original.getValue(BlockStateProperties.LIT)
        ) {
            return Optional.of(original.setValue(BlockStateProperties.LIT, false));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Type<ExtinguishFlameIfFireTransformer> getType() {
        return FBlockTransformerTypes.EXTINGUISH_FLAME_IF_FIRE;
    }

    private ExtinguishFlameIfFireTransformer() {

    }
}