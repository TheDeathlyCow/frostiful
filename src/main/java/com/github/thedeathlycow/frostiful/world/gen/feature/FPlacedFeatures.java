package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.biome.FHasFeatureTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.Objects;

public class FPlacedFeatures {

    public static void placeFeatures() {
        BiomeModifications.addFeature(
                BiomeSelectors.tag(FHasFeatureTags.SUN_LICHEN_COVERED_ROCK),
                GenerationStep.Feature.VEGETAL_DECORATION,
                Objects.requireNonNull(FSurfacePlacedFeatures.SUN_LICHEN_COVERED_ROCK.getKey().orElse(null))
        );
    }

    public static RegistryEntry<PlacedFeature> register(String name, PlacedFeature placedFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, new Identifier(Frostiful.MODID, name), placedFeature);
    }
}
