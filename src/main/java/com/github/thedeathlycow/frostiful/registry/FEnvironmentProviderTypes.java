package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.survival.environment.EnsureTemperatureBelow;
import com.github.thedeathlycow.frostiful.survival.environment.IfSnowy;
import com.github.thedeathlycow.thermoo.api.ThermooRegistries;
import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProvider;
import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProviderType;
import net.minecraft.core.Registry;

public final class FEnvironmentProviderTypes {
    public static final EnvironmentProviderType<IfSnowy> IF_SNOWY = register(
            "if_snowy",
            new EnvironmentProviderType<>(IfSnowy.CODEC)
    );

    public static final EnvironmentProviderType<EnsureTemperatureBelow> ENSURE_TEMPERATURE_BELOW = register(
            "ensure_temperature_below",
            new EnvironmentProviderType<>(EnsureTemperatureBelow.CODEC)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful environment provider types");
    }

    private static <T extends EnvironmentProvider> EnvironmentProviderType<T> register(
            String name,
            EnvironmentProviderType<T> type
    ) {
        return Registry.register(ThermooRegistries.ENVIRONMENT_PROVIDER_TYPE, Frostiful.id(name), type);
    }

    private FEnvironmentProviderTypes() {

    }
}