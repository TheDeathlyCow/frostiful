package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock.CoveredRockFeatureConfig;
import com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock.CoveredRockSizeConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class FSurfaceConfiguredFeatures {

    public static final RegistryEntry<ConfiguredFeature<?, ?>> SUN_LICHEN_COVERED_ROCK = FConfiguredFeatures.register("sun_lichen_covered_rock", new ConfiguredFeature<>(FFeatures.COVERED_ROCK,
            new CoveredRockFeatureConfig(
                    SimpleBlockStateProvider.of(Blocks.COBBLESTONE.getDefaultState()),
                    new CoveredRockSizeConfig(
                            UniformIntProvider.create(0, 2),
                            UniformIntProvider.create(0, 2),
                            UniformIntProvider.create(0, 2)
                    ),
                    SunLichenCoveringFeatures.PLACED_SUN_LICHEN,
                    0.3f
            )));

}
