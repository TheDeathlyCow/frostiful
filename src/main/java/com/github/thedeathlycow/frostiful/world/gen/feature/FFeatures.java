package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock.CoveredRockFeature;
import com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock.CoveredRockFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;

public class FFeatures {

    public static final Feature<CoveredRockFeatureConfig> COVERED_ROCK = new CoveredRockFeature(CoveredRockFeatureConfig.CODEC);

    private static void register(String name, Feature<?> feature) {
        Registry.register(Registries.FEATURE, Frostiful.id(name), feature);
    }

    public static void registerAll() {
        register("covered_rock", COVERED_ROCK);
    }
}
