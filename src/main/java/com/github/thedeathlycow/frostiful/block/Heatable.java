package com.github.thedeathlycow.frostiful.block;

import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.Optional;

public interface Heatable {

    ImmutableBiMap<Block, Block> getHeatLevelIncreases();

    default ImmutableBiMap<Block, Block> getHeatLevelDecreases() {
        return this.getHeatLevelIncreases().inverse();
    }

    default Optional<Block> getNextBlock(Block current) {
        ImmutableBiMap<Block, Block> heatLevelIncreases = this.getHeatLevelIncreases();
        Block next = heatLevelIncreases.get(current);
        return next != null ? Optional.of(next) : Optional.empty();
    }

    default Optional<Block> getPreviousBlock(Block current) {
        ImmutableBiMap<Block, Block> heatLevelDecreases = this.getHeatLevelDecreases();
        Block previous = heatLevelDecreases.get(current);
        return previous != null ? Optional.of(previous) : Optional.empty();
    }

    default Optional<BlockState> getNextState(BlockState current) {
        Optional<Block> next = this.getNextBlock(current.getBlock());
        if (next.isPresent()) {
            BlockState nextState = next.get().getStateWithProperties(current);
            return Optional.of(nextState);
        } else {
            return Optional.empty();
        }
    }

    default Optional<BlockState> getPreviousState(BlockState current) {
        Optional<Block> previous = this.getPreviousBlock(current.getBlock());
        if (previous.isPresent()) {
            BlockState nextState = previous.get().getStateWithProperties(current);
            return Optional.of(nextState);
        } else {
            return Optional.empty();
        }
    }

}
