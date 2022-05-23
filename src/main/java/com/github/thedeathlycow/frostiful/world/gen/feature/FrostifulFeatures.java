package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock.CoveredRockFeature;
import com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock.CoveredRockFeatureConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class FrostifulFeatures {

    public static final Feature<CoveredRockFeatureConfig> COVERED_ROCK = register("covered_rock", new CoveredRockFeature(CoveredRockFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, new Identifier(Frostiful.MODID, name), feature);
    }
}
