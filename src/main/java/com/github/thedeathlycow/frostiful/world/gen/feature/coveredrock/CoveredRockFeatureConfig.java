package com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

/**
 * The config for covered rock features
 *
 * @param base                The base block of the rock, e.g. stone
 * @param covering            The block that covers the block, e.g. lichen
 * @param size                The size of the rock.
 * @param canPlaceOn          Blocks that the lichen can be placed on
 * @param placeCoveringChance The chance to place the covering. Note that lichen blocks will be spread.
 */
public record CoveredRockFeatureConfig(BlockStateProvider base, BlockStateProvider covering,
                                       CoveredRockSizeConfig size, RegistryEntryList<Block> canPlaceOn,
                                       float placeCoveringChance) implements FeatureConfig {
    public static final Codec<CoveredRockFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockStateProvider.TYPE_CODEC.fieldOf("base").forGetter(CoveredRockFeatureConfig::base),
                    BlockStateProvider.TYPE_CODEC.fieldOf("covering").forGetter(CoveredRockFeatureConfig::covering),
                    CoveredRockSizeConfig.CODEC.fieldOf("size").forGetter(CoveredRockFeatureConfig::size),
                    RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("can_place_on").forGetter(CoveredRockFeatureConfig::canPlaceOn),
                    Codec.floatRange(0.0f, 1.0f).fieldOf("place_covering_chance").forGetter(CoveredRockFeatureConfig::placeCoveringChance)
            ).apply(instance, instance.stable(CoveredRockFeatureConfig::new))
    );
}
