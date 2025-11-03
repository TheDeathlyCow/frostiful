package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.tag.FHasFeatureTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FPlacedFeatures {

    public static final ResourceKey<PlacedFeature> SUN_LICHEN_COVERED_ROCK = of("sun_lichen_covered_rock");
    public static final ResourceKey<PlacedFeature> ICICLE_CLUSTER = of("icicle_cluster");
    public static final ResourceKey<PlacedFeature> BRITTLE_ICE = of("brittle_ice");

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful placed features");

        BiomeModification modification = BiomeModifications.create(Frostiful.location("vegetation"));

        modification.add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(FHasFeatureTags.SUN_LICHEN_COVERED_ROCK),
                (biomeSelectionContext, biomeModificationContext) -> {
                    biomeModificationContext.getGenerationSettings().addFeature(
                            GenerationStep.Decoration.VEGETAL_DECORATION,
                            FPlacedFeatures.SUN_LICHEN_COVERED_ROCK
                    );
                }
        );

        modification.add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(FHasFeatureTags.ICICLE_CLUSTER),
                (biomeSelectionContext, biomeModificationContext) -> {
                    biomeModificationContext.getGenerationSettings().addFeature(
                            GenerationStep.Decoration.UNDERGROUND_DECORATION,
                            FPlacedFeatures.ICICLE_CLUSTER
                    );
                }
        );

        modification.add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(FHasFeatureTags.BRITTLE_ICE),
                (biomeSelectionContext, biomeModificationContext) -> {
                    biomeModificationContext.getGenerationSettings().addFeature(
                            GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                            FPlacedFeatures.BRITTLE_ICE
                    );
                }
        );
    }

    private static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Frostiful.location(id));
    }

    private FPlacedFeatures() {

    }
}
