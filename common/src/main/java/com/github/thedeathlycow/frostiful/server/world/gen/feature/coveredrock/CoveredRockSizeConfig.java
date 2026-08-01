package com.github.thedeathlycow.frostiful.server.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;

public record CoveredRockSizeConfig(IntProvider sizeX, IntProvider sizeY, IntProvider sizeZ) {
    public static final Codec<CoveredRockSizeConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IntProviders.CODEC.fieldOf("x").forGetter(CoveredRockSizeConfig::sizeX),
                    IntProviders.CODEC.fieldOf("y").forGetter(CoveredRockSizeConfig::sizeY),
                    IntProviders.CODEC.fieldOf("z").forGetter(CoveredRockSizeConfig::sizeZ)
            ).apply(instance, instance.stable(CoveredRockSizeConfig::new))
    );
}
