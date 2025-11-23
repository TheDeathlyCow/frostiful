package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.item.attribute.FrostResistanceComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.UnaryOperator;

public final class FDataComponentTypes {
    public static final DataComponentType<FrostResistanceComponent> FROST_RESISTANCE = register(
            "frost_resistance",
            builder -> builder
                    .persistent(FrostResistanceComponent.CODEC)
                    .networkSynchronized(FrostResistanceComponent.PACKET_CODEC)
                    .cacheEncoding()
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful item components");
    }

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Frostiful.id(id),
                builderOperator.apply(DataComponentType.builder()).build()
        );
    }

    private FDataComponentTypes() {

    }
}