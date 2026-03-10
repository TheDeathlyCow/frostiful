package com.github.thedeathlycow.frostiful.test;

import net.minecraft.resources.ResourceLocation;
import org.sinytra.fabric.gametest_api.generated.GeneratedEntryPoint;

public class FrostifulGameTest {
    public static final String MODID = "frostiful_test";
    public static final String EMPTY_STRUCTURE = "empty";
    public static final String EMPTY_STRUCTURE_MODID = GeneratedEntryPoint.MOD_ID;


    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}