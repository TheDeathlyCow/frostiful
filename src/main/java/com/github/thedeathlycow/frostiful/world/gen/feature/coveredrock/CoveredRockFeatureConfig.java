package com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

/**
 * The config for covered rock features
 *
 * @param base The base block of the rock, e.g. stone
 * @param covering The block that covers the block, e.g. lichen
 * @param size The size of the rock. Rock places blocks from -size to +size.
 */
public record CoveredRockFeatureConfig(BlockStateProvider base, BlockStateProvider covering,
                                       IntProvider size) implements FeatureConfig {
    public static final Codec<CoveredRockFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockStateProvider.TYPE_CODEC.fieldOf("base").forGetter(CoveredRockFeatureConfig::base),
                    BlockStateProvider.TYPE_CODEC.fieldOf("covering").forGetter(CoveredRockFeatureConfig::covering),
                    IntProvider.VALUE_CODEC.fieldOf("size").forGetter(CoveredRockFeatureConfig::size)
            ).apply(instance, instance.stable(CoveredRockFeatureConfig::new))
    );
}
