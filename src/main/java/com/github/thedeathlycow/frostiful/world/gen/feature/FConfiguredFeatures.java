package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class FConfiguredFeatures {

    public static RegistryEntry<ConfiguredFeature<?, ?>> register(String name, ConfiguredFeature<?, ?> configuredFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Frostiful.MODID, name), configuredFeature);
    }
}
