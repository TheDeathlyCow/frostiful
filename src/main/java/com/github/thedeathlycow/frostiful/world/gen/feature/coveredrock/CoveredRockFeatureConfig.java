package com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryEntry;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

/**
 * The config for covered rock features
 *
 * @param base            The base block of the rock, e.g. stone
 * @param size            The size of the rock.
 * @param coveringFeature The feature that will cover the rock
 */
public record CoveredRockFeatureConfig(
        BlockStateProvider base,
        CoveredRockSizeConfig size,
        RegistryEntry<PlacedFeature> coveringFeature,
        float placeCoveringChance
) implements FeatureConfig {
    public static final Codec<CoveredRockFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockStateProvider.TYPE_CODEC.fieldOf("base").forGetter(CoveredRockFeatureConfig::base),
                    CoveredRockSizeConfig.CODEC.fieldOf("size").forGetter(CoveredRockFeatureConfig::size),
                    PlacedFeature.REGISTRY_CODEC.fieldOf("covering_feature").forGetter(CoveredRockFeatureConfig::coveringFeature),
                    Codec.floatRange(0.0f, 1.0f).fieldOf("place_covering_chance").forGetter(CoveredRockFeatureConfig::placeCoveringChance)
            ).apply(instance, instance.stable(CoveredRockFeatureConfig::new))
    );
}
