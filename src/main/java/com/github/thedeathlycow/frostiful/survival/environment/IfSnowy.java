package com.github.thedeathlycow.frostiful.survival.environment;

import com.github.thedeathlycow.frostiful.registry.FEnvironmentProviderTypes;
import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProvider;
import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProviderType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public record IfSnowy(
        Holder<EnvironmentProvider> whenTrue
) implements EnvironmentProvider {
    public static final MapCodec<IfSnowy> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    EnvironmentProvider.ENTRY_CODEC
                            .fieldOf("when_true")
                            .forGetter(IfSnowy::whenTrue)
            ).apply(instance, IfSnowy::new)
    );

    @Override
    public void buildCurrentComponents(Level level, BlockPos pos, Holder<Biome> biome, DataComponentMap.Builder builder) {
        if (isSnowy(level, pos, biome)) {
            whenTrue.value().buildCurrentComponents(level, pos, biome, builder);
        }
    }

    @Override
    public EnvironmentProviderType<IfSnowy> getType() {
        return FEnvironmentProviderTypes.IF_SNOWY;
    }

    private static boolean isSnowy(Level level, BlockPos pos, Holder<Biome> biomeHolder) {
        Biome biome = biomeHolder.value();

        return biome.getBaseTemperature() < 0.15f
                || biome.getPrecipitationAt(pos) == Biome.Precipitation.SNOW
                || biome.shouldSnow(level, pos);
    }
}