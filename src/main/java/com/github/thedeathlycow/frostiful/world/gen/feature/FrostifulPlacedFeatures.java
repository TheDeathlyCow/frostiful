package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.biome.FrostifulHasFeatureTags;
import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.Objects;

public class FrostifulPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> SUN_LICHEN_COVERED_ROCK = register("sun_lichen_covered_rock", new PlacedFeature(FrostifulConfiguredFeatures.SUN_LICHEN_COVERED_ROCK,
            ImmutableList.of(
                    RarityFilterPlacementModifier.of(15),
                    SquarePlacementModifier.of(),
                    HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES),
                    BiomePlacementModifier.of()
            )
    ));

    public static void placeFeatures() {
        BiomeModifications.addFeature(
                BiomeSelectors.tag(FrostifulHasFeatureTags.SUN_LICHEN_COVERED_ROCK),
                GenerationStep.Feature.VEGETAL_DECORATION,
                Objects.requireNonNull(FrostifulPlacedFeatures.SUN_LICHEN_COVERED_ROCK.getKey().orElse(null))
        );
    }

    private static RegistryEntry<PlacedFeature> register(String name, PlacedFeature placedFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, new Identifier(Frostiful.MODID, name), placedFeature);
    }
}
