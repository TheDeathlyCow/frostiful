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

package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.registry.FBlockProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class BrittleIceBlock extends HalfTransparentBlock {

    public static final MapCodec<BrittleIceBlock> CODEC = simpleCodec(BrittleIceBlock::new);

    public static final IntegerProperty CRACKING = FBlockProperties.CRACKING;

    public static final BooleanProperty FROZEN = FBlockProperties.FROZEN;

    public BrittleIceBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(CRACKING, 0)
                        .setValue(FROZEN, false)
        );
    }

    @Override
    public MapCodec<BrittleIceBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CRACKING, FROZEN);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClientSide() && BrittleIce.canCrackIce(entity)) {
            world.scheduleTick(pos, this, BrittleIce.getCrackDelay(world.getRandom()));
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.is(this)) {
            return;
        }
        BrittleIce.crack(this, state, world, pos, random);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState base = super.getStateForPlacement(ctx);

        if (base == null) {
            return null;
        }

        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return base.setValue(FROZEN, fluidState.is(Fluids.WATER));
    }
}