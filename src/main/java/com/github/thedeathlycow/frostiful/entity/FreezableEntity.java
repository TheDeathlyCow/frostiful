package com.github.thedeathlycow.frostiful.entity;

import net.minecraft.util.math.MathHelper;

public interface FreezableEntity {

    String FROSTIFUL_KEY = "Frostiful";

    int frostiful$getCurrentFrost();

    int frostiful$getMaxFrost();

    void frostiful$setFrost(int amount);

    boolean frostiful$canFreeze();

    default void frostiful$setFrost(int amount, boolean clamp) {
        int toSet = amount;
        if (clamp) {
            toSet = MathHelper.clamp(toSet, 0, this.frostiful$getMaxFrost());
        }
        this.frostiful$setFrost(toSet);
    }

    default void frostiful$addFrost(int amount) {
        this.frostiful$setFrost(this.frostiful$getCurrentFrost() + amount, true);
    }

    default void frostiful$removeFrost(int amount) {
        this.frostiful$setFrost(this.frostiful$getCurrentFrost() - amount, true);
    }

    default float frostiful$getFrostProgress() {
        final int maxFrost = this.frostiful$getMaxFrost();

        if (maxFrost == 0) {
            return 0.0f;
        }

        return Math.min(this.frostiful$getCurrentFrost(), maxFrost) / ((float) maxFrost);
    }

    default boolean frostiful$isFrozen() {
        return this.frostiful$getCurrentFrost() >= this.frostiful$getMaxFrost();
    }
}
