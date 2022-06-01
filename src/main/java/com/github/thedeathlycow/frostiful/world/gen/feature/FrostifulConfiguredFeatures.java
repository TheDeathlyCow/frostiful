package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock.CoveredRockFeatureConfig;
import com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock.CoveredRockSizeConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class FrostifulConfiguredFeatures {

    @SuppressWarnings("deprecation")
    public static final RegistryEntry<ConfiguredFeature<?, ?>> SUN_LICHEN_COVERED_ROCK = register("sun_lichen_covered_rock", new ConfiguredFeature<>(FrostifulFeatures.COVERED_ROCK,
            new CoveredRockFeatureConfig(
                    SimpleBlockStateProvider.of(Blocks.STONE.getDefaultState()),
                    SimpleBlockStateProvider.of(FrostifulBlocks.HOT_SUN_LICHEN.getDefaultState()),
                    new CoveredRockSizeConfig(
                            UniformIntProvider.create(0, 1),
                            UniformIntProvider.create(0, 2),
                            UniformIntProvider.create(0, 1)
                    ),
                    RegistryEntryList.of(Block::getRegistryEntry, Blocks.STONE, Blocks.DIRT, Blocks.GRASS_BLOCK),
                    0.3f
            )));

    public static RegistryEntry<ConfiguredFeature<?, ?>> register(String name, ConfiguredFeature<?, ?> configuredFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Frostiful.MODID, name), configuredFeature);
    }
}
