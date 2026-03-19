package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.survival.environment.EnsureTemperatureBelow;
import com.github.thedeathlycow.frostiful.survival.environment.IfSnowy;
import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistries;
import com.github.thedeathlycow.thermoo.api.environment.v2.provider.EnvironmentProvider;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;

public final class FEnvironmentProviderTypes {
    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful environment provider types");
        register("if_snowy", IfSnowy.CODEC);
        register("ensure_temperature_below", EnsureTemperatureBelow.CODEC);
    }

    private static <T extends EnvironmentProvider> MapCodec<T> register(String name, MapCodec<T> codec) {
        return Registry.register(ThermooRegistries.ENVIRONMENT_PROVIDER_TYPE, Frostiful.id(name), codec);
    }

    private FEnvironmentProviderTypes() {

    }
}