package com.github.thedeathlycow.frostiful.neoforge;

import com.github.thedeathlycow.frostiful.block.BlockAddedEvent;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.ModInitializer;
import net.minecraft.world.level.block.state.BlockState;

public class FrostifulNeoforge implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        BlockAddedEvent.EVENT.register((key, block) -> {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                if (state.getOcclusionShape() == null) {
                    state.initCache();
                }
            }
        });
    }
}