package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.Frostiful;
import dev.yumi.commons.event.Event;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface BlockAddedEvent {
    Event<Identifier, BlockAddedEvent> EVENT = Frostiful.EVENT_MANAGER.create(
            BlockAddedEvent.class,
            listeners -> (key, block) -> {
                for (BlockAddedEvent listener : listeners) {
                    listener.onBlockAdded(key, block);
                }
            }
    );

    void onBlockAdded(ResourceKey<Block> key, Block block);
}
