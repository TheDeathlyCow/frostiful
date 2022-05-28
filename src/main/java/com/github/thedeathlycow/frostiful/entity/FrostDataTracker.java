package com.github.thedeathlycow.frostiful.entity;

public interface FrostDataTracker {

    int frostiful$getFrost();

    void frostiful$setFrost(int frost);

    int frostiful$getMaxFrost();

    void frostiful$applyEffects();

    boolean frostiful$canApplyFrost();

    float frostiful$getFrostProgress();
}
