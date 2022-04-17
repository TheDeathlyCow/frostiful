package com.github.thedeathlycow.lostinthecold.config;

import org.jetbrains.annotations.NotNull;

public class ByteKey extends BoundedKey<Byte> {

    public ByteKey(@NotNull String name, @NotNull Byte defaultValue) {
        this(name, defaultValue, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public ByteKey(@NotNull String name, @NotNull Byte defaultValue, Byte min) {
        this(name, defaultValue, min, Byte.MAX_VALUE);
    }

    public ByteKey(@NotNull String name, @NotNull Byte defaultValue, Byte min, Byte max) {
        super(name, defaultValue, Byte.class, min, max);
    }
}
