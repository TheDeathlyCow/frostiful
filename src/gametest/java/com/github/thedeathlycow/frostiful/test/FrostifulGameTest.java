package com.github.thedeathlycow.frostiful.test;

import net.minecraft.resources.ResourceLocation;

public final class FrostifulGameTest {
    public static final String MODID = "frostiful-test";

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private FrostifulGameTest() {

    }
}