package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultifaceGrowthFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public class SunLichenCoveringFeatures {

    @SuppressWarnings("deprecation")
    public static final RegistryEntry<ConfiguredFeature<?, ?>> SUN_LICHEN = FrostifulConfiguredFeatures.register(
            "sun_lichen",
            new ConfiguredFeature<>(
                    Feature.MULTIFACE_GROWTH,
                    new MultifaceGrowthFeatureConfig(
                            (MultifaceGrowthBlock) FrostifulBlocks.HOT_SUN_LICHEN,
                            20,
                            false,
                            true,
                            true,
                            0.5F,
                            RegistryEntryList.of(Block::getRegistryEntry, Blocks.STONE, Blocks.COBBLESTONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE)
                    )
            )
    );

    public static final RegistryEntry<PlacedFeature> PLACED_SUN_LICHEN = FrostifulPlacedFeatures.register("sun_lichen", new PlacedFeature(
                    SUN_LICHEN,
                    ImmutableList.of()
            )
    );

}
