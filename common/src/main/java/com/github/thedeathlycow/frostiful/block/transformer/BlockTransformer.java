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

import com.github.thedeathlycow.frostiful.registry.FrostifulRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface BlockTransformer {
    Codec<BlockTransformer> ELEMENT_CODEC = FrostifulRegistries.BLOCK_TRANSFORMER_TYPE.byNameCodec()
            .dispatch("type", BlockTransformer::getType, Type::codec);

    Codec<Holder<@NotNull BlockTransformer>> HOLDER_CODEC = RegistryFileCodec.create(
            FrostifulRegistries.BLOCK_TRANSFORMER_KEY,
            ELEMENT_CODEC
    );

    Optional<BlockState> transformBlockState(ServerLevel level, BlockPos pos, BlockState original);

    Type<? extends BlockTransformer> getType();

    static BlockTransformer identity() {
        return IdentityBlockTransformer.INSTANCE;
    }

    static BlockTransformer simple(Block block) {
        return new SimpleBlockTransformer(BlockStateProvider.simple(block));
    }

    record Type<T extends BlockTransformer>(MapCodec<T> codec) {

    }
}