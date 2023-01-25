package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.survival.effects.TemperatureEffect;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FRegistries {

    public static final Registry<TemperatureEffect<?>> TEMPERATURE_EFFECTS =
            FabricRegistryBuilder.createSimple(
                    FRegistries.<TemperatureEffect<?>>castClass(TemperatureEffect.class),
                    Frostiful.id("temperature_effects")
            ).buildAndRegister();


    @SuppressWarnings("unchecked")
    private static <T> Class<T> castClass(Class<?> clazz) {
        return (Class<T>)clazz;
    }
}
