package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;

public final class FEnvironmentAttributes {
    public static final EnvironmentAttribute<Boolean> IS_WINDY = register(
            "gameplay/is_windy",
            EnvironmentAttribute.builder(AttributeTypes.BOOLEAN)
                    .defaultValue(false)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful environment attributes");
    }

    private static  <V> EnvironmentAttribute<V> register(String name, EnvironmentAttribute.Builder<V> builder) {
        return Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Frostiful.id(name), builder.build());
    }

    private FEnvironmentAttributes() {

    }
}