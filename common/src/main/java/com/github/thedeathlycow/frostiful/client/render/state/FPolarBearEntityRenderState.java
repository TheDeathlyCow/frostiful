package com.github.thedeathlycow.frostiful.client.render.state;

public interface FPolarBearEntityRenderState {
    default boolean frostiful$wasSheared() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$wasSheared(boolean value) {
        throw new AssertionError("Implemented in mixin");
    }
}