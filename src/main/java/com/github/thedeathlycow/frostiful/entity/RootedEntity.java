package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.FNbtHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public interface RootedEntity {

    String ROOTED_KEY = "RootedTicks";

    int frostiful$getRootedTicks();

    void frostiful$setRootedTicks(int amount);

    boolean frostiful$canRoot();

    default boolean frostiful$root() {
        if (this.frostiful$canRoot()) {
            FrostifulConfig config = Frostiful.getConfig();
            this.frostiful$setRootedTicks(config.combatConfig.getFrostWandRootTime());
            return true;
        }
        return false;
    }

    default void frostiful$breakRoot() {
        this.frostiful$setRootedTicks(0);
    }

    default boolean frostiful$isRooted() {
        return this.frostiful$getRootedTicks() > 0;
    }

    static void frostiful$addRootedTicksToNbt(RootedEntity entity, NbtCompound nbt) {
        NbtCompound frostifulNbt = FNbtHelper.getOrDefault(
                nbt,
                FreezableEntity.FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE,
                NbtCompound::getCompound,
                FNbtHelper.NEW_COMPOUND_FALLBACK
        );

        if (entity.frostiful$isRooted()) {
            frostifulNbt.putInt(ROOTED_KEY, entity.frostiful$getRootedTicks());
        }
        nbt.put(FreezableEntity.FROSTIFUL_KEY, frostifulNbt);
    }

    static void frostiful$setRootedTicksFromNbt(RootedEntity entity, NbtCompound nbt) {
        int amount = 0;
        if (nbt.contains(FreezableEntity.FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE)) {
            NbtCompound frostifulNbt = nbt.getCompound(FreezableEntity.FROSTIFUL_KEY);
            if (frostifulNbt.contains(ROOTED_KEY, NbtElement.INT_TYPE)) {
                amount = frostifulNbt.getInt(ROOTED_KEY);
            }
        }

        entity.frostiful$setRootedTicks(amount);
    }

}
