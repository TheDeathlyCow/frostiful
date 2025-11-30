package com.github.thedeathlycow.frostiful.util;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Contract;

public class FNbtHelper {

    public static final Fallback<CompoundTag> NEW_COMPOUND_FALLBACK = CompoundTag::new;

    public static <T> T getOrDefault(CompoundTag source, String key, byte type, NbtGetter<T> getter, Fallback<T> fallback) {
        return source.contains(key, type) ? getter.get(source, key) : fallback.get();
    }

    @FunctionalInterface
    public interface NbtGetter<T> {

        T get(CompoundTag nbt, String key);

    }

    @FunctionalInterface
    public interface Fallback<T> {
        @Contract("-> new")
        T get();
    }
}
