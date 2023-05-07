package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class FConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> SUN_LICHEN_COVERED_ROCK = of("sun_lichen_covered_rock");

    private static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Frostiful.id(id));
    }

}
