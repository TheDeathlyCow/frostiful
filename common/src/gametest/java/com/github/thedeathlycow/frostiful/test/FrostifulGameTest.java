package com.github.thedeathlycow.frostiful.test;

import net.minecraft.resources.Identifier;

public final class FrostifulGameTest {
    public static final String MODID = "frostiful_test";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    private FrostifulGameTest() {

    }
}