package com.github.thedeathlycow.frostiful.server.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;

public record CoveredRockSizeConfig(IntProvider sizeX, IntProvider sizeY, IntProvider sizeZ) {
    public static final Codec<CoveredRockSizeConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IntProvider.CODEC.fieldOf("x").forGetter(CoveredRockSizeConfig::sizeX),
                    IntProvider.CODEC.fieldOf("y").forGetter(CoveredRockSizeConfig::sizeY),
                    IntProvider.CODEC.fieldOf("z").forGetter(CoveredRockSizeConfig::sizeZ)
            ).apply(instance, instance.stable(CoveredRockSizeConfig::new))
    );
}
