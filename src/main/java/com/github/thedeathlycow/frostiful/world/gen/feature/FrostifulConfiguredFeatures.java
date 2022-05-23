package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock.CoveredRockFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class FrostifulConfiguredFeatures {

    public static final RegistryEntry<ConfiguredFeature<?, ?>> SUN_LICHEN_COVERED_ROCK = register("sun_lichen_covered_rock", new ConfiguredFeature<>(FrostifulFeatures.COVERED_ROCK,
            new CoveredRockFeatureConfig(
                    SimpleBlockStateProvider.of(Blocks.STONE.getDefaultState()),
                    SimpleBlockStateProvider.of(FrostifulBlocks.SUN_LICHEN.getDefaultState()),
                    UniformIntProvider.create(0, 1)
            )));

    public static RegistryEntry<ConfiguredFeature<?, ?>> register(String name, ConfiguredFeature<?, ?> configuredFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Frostiful.MODID, name), configuredFeature);
    }
}
