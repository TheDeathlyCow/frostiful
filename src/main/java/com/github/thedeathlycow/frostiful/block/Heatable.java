package com.github.thedeathlycow.frostiful.block;

import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.Optional;

public interface Heatable {

    ImmutableBiMap<Block, Block> HEAT_LEVEL_INCREASES = new ImmutableBiMap.Builder<Block, Block>()
            .put(FBlocks.COLD_SUN_LICHEN, FBlocks.COOL_SUN_LICHEN)
            .put(FBlocks.COOL_SUN_LICHEN, FBlocks.WARM_SUN_LICHEN)
            .put(FBlocks.WARM_SUN_LICHEN, FBlocks.HOT_SUN_LICHEN)
            .build();

    ImmutableBiMap<Block, Block> HEAT_LEVEL_DECREASES = HEAT_LEVEL_INCREASES.inverse();

    int getHeatLevel();

    static Optional<Block> getNextBlock(Block current) {
        Block next = HEAT_LEVEL_INCREASES.get(current);
        return next != null ? Optional.of(next) : Optional.empty();
    }

    static Optional<Block> getPreviousBlock(Block current) {
        Block previous = HEAT_LEVEL_DECREASES.get(current);
        return previous != null ? Optional.of(previous) : Optional.empty();
    }

    static Optional<BlockState> getNextState(BlockState current) {
        Optional<Block> next = getNextBlock(current.getBlock());
        if (next.isPresent()) {
            BlockState nextState = next.get().getStateWithProperties(current);
            return Optional.of(nextState);
        } else {
            return Optional.empty();
        }
    }

    static Optional<BlockState> getPreviousState(BlockState current) {
        Optional<Block> previous = getPreviousBlock(current.getBlock());
        if (previous.isPresent()) {
            BlockState nextState = previous.get().getStateWithProperties(current);
            return Optional.of(nextState);
        } else {
            return Optional.empty();
        }
    }

}
