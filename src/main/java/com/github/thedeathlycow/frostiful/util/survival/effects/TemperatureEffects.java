package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class TemperatureEffects {

    public static final Map<Identifier, TemperatureEffect<?>> VALUES = new HashMap<>();

    public static void createEffectTypes() {
        create("status_effect", new StatusEffectTemperatureEffect());
        create("scaling_attribute_modifier", new ScalingAttributeModifierTemperatureEffect());
    }

    public static TemperatureEffect<?> create(Identifier id, TemperatureEffect<?> type) {
        return VALUES.put(id, type);
    }

    private static TemperatureEffect<?> create(String name, TemperatureEffect<?> type) {
        return create(new Identifier(Frostiful.MODID, name), type);
    }
}
