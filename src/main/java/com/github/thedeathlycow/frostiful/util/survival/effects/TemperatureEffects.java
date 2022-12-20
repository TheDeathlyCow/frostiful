package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FRegistries;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TemperatureEffects {

    public static final TemperatureEffect<?> STATUS_EFFECT = new StatusEffectTemperatureEffect();
    public static final TemperatureEffect<?> SCALING_ATTRIBUTE_MODIFIER = new ScalingAttributeModifierTemperatureEffect();
    public static final TemperatureEffect<?> EMPTY = new EmptyTemperatureEffect();

    public static void registerAll() {
        register("status_effect", STATUS_EFFECT);
        register("scaling_attribute_modifier", SCALING_ATTRIBUTE_MODIFIER);
        register("empty", EMPTY);
    }

    private static void register(String name, TemperatureEffect<?> temperatureEffect) {
        Registry.register(
                FRegistries.TEMPERATURE_EFFECT_REGISTRY,
                new Identifier(Frostiful.MODID, name),
                temperatureEffect);
    }
}
