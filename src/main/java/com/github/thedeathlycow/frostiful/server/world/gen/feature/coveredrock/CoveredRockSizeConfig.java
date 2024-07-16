package com.github.thedeathlycow.frostiful.server.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;

public record CoveredRockSizeConfig(IntProvider sizeX, IntProvider sizeY, IntProvider sizeZ) {
    public static final Codec<CoveredRockSizeConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IntProvider.VALUE_CODEC.fieldOf("x").forGetter(CoveredRockSizeConfig::sizeX),
                    IntProvider.VALUE_CODEC.fieldOf("y").forGetter(CoveredRockSizeConfig::sizeY),
                    IntProvider.VALUE_CODEC.fieldOf("z").forGetter(CoveredRockSizeConfig::sizeZ)
            ).apply(instance, instance.stable(CoveredRockSizeConfig::new))
    );
}
