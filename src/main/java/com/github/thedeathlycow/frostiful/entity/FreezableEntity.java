package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.util.FNbtHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.MathHelper;

public interface FreezableEntity {

    String FROSTIFUL_KEY = "Frostiful";
    String WET_KEY = "TicksWet";

    int frostiful$getCurrentFrost();

    int frostiful$getMaxFrost();

    void frostiful$setFrost(int amount);

    boolean frostiful$canFreeze();

    void frostiful$setWetTicks(int amount);

    int frostiful$getWetTicks();

    default boolean frostiful$isWet() {
        return this.frostiful$getWetTicks() > 0;
    }

    default int frostiful$getMaxWetTicks() {
        return 20 * 30;
    }

    default float frostiful$getWetnessScale() {
        return MathHelper.clamp(
                ((float)this.frostiful$getWetTicks()) / this.frostiful$getMaxWetTicks(),
                0.0f, 1.0f
        );
    }

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
        return Math.min(this.frostiful$getCurrentFrost(), maxFrost) / ((float) maxFrost);
    }

    default boolean frostiful$isFrozen() {
        return this.frostiful$getCurrentFrost() >= this.frostiful$getMaxFrost();
    }

    static void frostiful$addDataToNbt(FreezableEntity entity, NbtCompound nbt) {
        NbtCompound frostifulNbt = FNbtHelper.getOrDefault(
                nbt,
                FreezableEntity.FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE,
                NbtCompound::getCompound,
                FNbtHelper.NEW_COMPOUND_FALLBACK
        );


        if (entity.frostiful$isWet()) {
            frostifulNbt.putInt(WET_KEY, entity.frostiful$getWetTicks());
        }


        nbt.put(FROSTIFUL_KEY, frostifulNbt);
    }

    static void frostiful$setDataFromNbt(FreezableEntity entity, NbtCompound nbt) {
        if (nbt.contains(FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE)) {

            NbtCompound frostifulNbt = nbt.getCompound(FROSTIFUL_KEY);
            if (frostifulNbt.contains(WET_KEY, NbtElement.INT_TYPE)) {
                int wetTicks = frostifulNbt.getInt(WET_KEY);
                entity.frostiful$setWetTicks(wetTicks);
            }

        }
    }
}
