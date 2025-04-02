package com.github.thedeathlycow.frostiful.client.render.state;

import net.minecraft.util.Identifier;

public interface FPlayerRendererState {
    default Identifier frostiful$capeTexture() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$capeTexture(Identifier id) {
        throw new AssertionError("Implemented in mixin");
    }
}