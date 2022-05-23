package com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

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
