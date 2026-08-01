package com.github.thedeathlycow.frostiful.server.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.BlockBlobConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

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
        Holder<PlacedFeature> coveringFeature,
        float placeCoveringChance,
        BlockPredicate canPlaceOn
) implements FeatureConfiguration {
    public static final Codec<CoveredRockFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockStateProvider.CODEC
                            .fieldOf("base")
                            .forGetter(CoveredRockFeatureConfig::base),
                    CoveredRockSizeConfig.CODEC
                            .fieldOf("size")
                            .forGetter(CoveredRockFeatureConfig::size),
                    PlacedFeature.CODEC
                            .fieldOf("covering_feature")
                            .forGetter(CoveredRockFeatureConfig::coveringFeature),
                    Codec.floatRange(0.0f, 1.0f)
                            .fieldOf("place_covering_chance")
                            .forGetter(CoveredRockFeatureConfig::placeCoveringChance),
                    BlockPredicate.CODEC
                            .optionalFieldOf("can_place_on", BlockPredicate.alwaysTrue())
                            .forGetter(CoveredRockFeatureConfig::canPlaceOn)
            ).apply(instance, instance.stable(CoveredRockFeatureConfig::new))
    );
}
