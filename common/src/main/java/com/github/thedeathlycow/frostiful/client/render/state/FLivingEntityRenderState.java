package com.github.thedeathlycow.frostiful.client.render.state;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import org.apache.commons.lang3.NotImplementedException;

public interface FLivingEntityRenderState {
    default boolean frostiful$isRooted() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$isRooted(boolean value) {
        throw new AssertionError("Implemented in mixin");
    }

    default BlockModelRenderState frostiful$blockModel() {
        throw new NotImplementedException();
    }
}
