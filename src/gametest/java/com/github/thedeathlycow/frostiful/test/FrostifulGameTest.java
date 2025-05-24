package com.github.thedeathlycow.frostiful.test;

import net.minecraft.util.Identifier;

public final class FrostifulGameTest {
    public static final String MODID = "frostiful-test";

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }

    private FrostifulGameTest() {

    }
}