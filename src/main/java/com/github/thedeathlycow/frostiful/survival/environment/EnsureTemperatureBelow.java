package com.github.thedeathlycow.frostiful.survival.environment;

import com.github.thedeathlycow.frostiful.registry.FEnvironmentProviderTypes;
import com.github.thedeathlycow.thermoo.api.environment.component.EnvironmentComponentTypes;
import com.github.thedeathlycow.thermoo.api.environment.component.TemperatureRecordComponent;
import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProvider;
import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProviderType;
import com.github.thedeathlycow.thermoo.api.util.TemperatureRecord;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.item.v1.FabricComponentMapBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public record EnsureTemperatureBelow(
        TemperatureRecord value
) implements EnvironmentProvider {
    public static final MapCodec<EnsureTemperatureBelow> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    TemperatureRecord.CODEC
                            .fieldOf("value")
                            .forGetter(EnsureTemperatureBelow::value)
            ).apply(instance, EnsureTemperatureBelow::new)
    );

    @Override
    public void buildCurrentComponents(Level level, BlockPos pos, Holder<Biome> biome, DataComponentMap.Builder builder) {
        TemperatureRecord temperature = ((FabricComponentMapBuilder) builder).getOrDefault(
                EnvironmentComponentTypes.TEMPERATURE,
                TemperatureRecordComponent.DEFAULT
        );

        if (this.value.compareTo(temperature) < 0) {
            builder.set(EnvironmentComponentTypes.TEMPERATURE, this.value);
        }
    }

    @Override
    public EnvironmentProviderType<EnsureTemperatureBelow> getType() {
        return FEnvironmentProviderTypes.ENSURE_TEMPERATURE_BELOW;
    }
}