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
import net.minecraft.advancements.criterion.FluidPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public record IfFluidTransformer(
        FluidPredicate predicate,
        BlockTransformer whenTrue,
        Optional<BlockTransformer> whenFalse
) implements BlockTransformer {
    public static final MapCodec<IfFluidTransformer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    FluidPredicate.CODEC
                            .fieldOf("predicate")
                            .forGetter(IfFluidTransformer::predicate),
                    BlockTransformer.ELEMENT_CODEC
                            .fieldOf("when_true")
                            .forGetter(IfFluidTransformer::whenTrue),
                    BlockTransformer.ELEMENT_CODEC
                            .optionalFieldOf("when_false")
                            .forGetter(IfFluidTransformer::whenFalse)
            ).apply(instance, IfFluidTransformer::new)
    );

    @Override
    public Optional<BlockState> transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        if (this.predicate.matches(level, pos)) {
            return this.whenTrue.transformBlockState(level, pos, original);
        } else if (this.whenFalse.isPresent()) {
            return this.whenFalse.orElseThrow().transformBlockState(level, pos, original);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Type<IfFluidTransformer> getType() {
        return FBlockTransformerTypes.IF_FLUID;
    }

    public static IfFluidTransformer ifFluid(FluidPredicate.Builder predicate, BlockTransformer whenTrue) {
        return new IfFluidTransformer(
                predicate.build(),
                whenTrue,
                Optional.empty()
        );
    }

    public static IfFluidTransformer ifFluidOrElse(FluidPredicate.Builder predicate, BlockTransformer whenTrue, BlockTransformer whenFalse) {
        return new IfFluidTransformer(
                predicate.build(),
                whenTrue,
                Optional.of(whenFalse)
        );
    }
}