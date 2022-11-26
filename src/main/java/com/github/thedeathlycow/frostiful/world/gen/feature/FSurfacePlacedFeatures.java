package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class FSurfacePlacedFeatures {

    public static final RegistryEntry<PlacedFeature> SUN_LICHEN_COVERED_ROCK = FPlacedFeatures.register("sun_lichen_covered_rock", new PlacedFeature(
            FSurfaceConfiguredFeatures.SUN_LICHEN_COVERED_ROCK,
            ImmutableList.of(
                    RarityFilterPlacementModifier.of(15),
                    SquarePlacementModifier.of(),
                    HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES),
                    BiomePlacementModifier.of()
            )
    ));

}
