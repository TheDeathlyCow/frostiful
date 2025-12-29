package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.survival.wind.WindBehavior;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeType;

public final class FAttributeTypes {
    public static final AttributeType<WindBehavior> WIND_BEHAVIOR = register(
            "wind_behavior",
            AttributeType.ofNotInterpolated(WindBehavior.CODEC)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful attribute types");
    }

    private static <V> AttributeType<V> register(String name, AttributeType<V> type) {
        return Registry.register(BuiltInRegistries.ATTRIBUTE_TYPE, Frostiful.id(name), type);
    }

    private FAttributeTypes() {

    }
}