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
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record FirstOfBlockTransformer(
        List<BlockTransformer> transformers
) implements BlockTransformer {
    public static final MapCodec<FirstOfBlockTransformer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockTransformer.ELEMENT_CODEC
                            .listOf()
                            .fieldOf("transformers")
                            .forGetter(FirstOfBlockTransformer::transformers)
            ).apply(instance, FirstOfBlockTransformer::new)
    );

    public static FirstOfBlockTransformer of(List<BlockTransformer> transformers) {
        return new FirstOfBlockTransformer(transformers);
    }

    public static FirstOfBlockTransformer of(BlockTransformer... transformers) {
        return of(Arrays.asList(transformers));
    }

    @Override
    public Optional<BlockState> transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        for (BlockTransformer transformer : transformers) {
            Optional<BlockState> result = transformer.transformBlockState(level, pos, original);
            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.empty();
    }

    @Override
    public Type<FirstOfBlockTransformer> getType() {
        return FBlockTransformerTypes.FIRST_OF;
    }
}