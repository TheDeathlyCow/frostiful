package com.github.thedeathlycow.frostiful.client.render.state;

import com.github.thedeathlycow.frostiful.item.component.CapeComponent;

public interface FPlayerRendererState {
    default CapeComponent frostiful$cape() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$cape(CapeComponent cape) {
        throw new AssertionError("Implemented in mixin");
    }
}