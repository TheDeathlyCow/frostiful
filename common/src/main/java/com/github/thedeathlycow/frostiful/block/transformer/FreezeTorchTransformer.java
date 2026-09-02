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
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public final class FreezeTorchTransformer implements BlockTransformer {
    private static final FreezeTorchTransformer INSTANCE = new FreezeTorchTransformer();

    public static final MapCodec<FreezeTorchTransformer> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public Optional<BlockState> transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        Block block = original.getBlock();

        if (block instanceof BaseTorchBlock && !original.is(FBlockTags.FROZEN_TORCHES)) {
            // Some wall torches (like redstone wall torch) don't extend WallTorchBlock, and so the only way to determine
            // if they are a wall torch is to check if they don't have the wall post override tag. It's not nice, but it's
            // the only way to generally determine if a block is a wall torch.

            boolean isWallTorch = block instanceof WallTorchBlock
                    || !original.is(BlockTags.WALL_POST_OVERRIDE);

            if (isWallTorch) {
                return Optional.of(FBlocks.FROZEN_WALL_TORCH.withPropertiesOf(original));
            } else {
                return Optional.of(FBlocks.FROZEN_TORCH.withPropertiesOf(original));
            }
        }

        return Optional.empty();
    }

    public static FreezeTorchTransformer of() {
        return INSTANCE;
    }

    @Override
    public Type<FreezeTorchTransformer> getType() {
        return FBlockTransformerTypes.FREEZE_TORCH;
    }

    private FreezeTorchTransformer() {

    }
}