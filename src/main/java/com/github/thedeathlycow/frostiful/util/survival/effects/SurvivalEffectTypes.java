package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SurvivalEffectTypes {

    public static final Map<Identifier, SurvivalEffectType<?>> VALUES = new HashMap<>();

    public static void createSurvivalEffectTypes() {
        create("status_effect", new StatusEffectSurvivalEffectType());
        create("scaling_attribute_modifier", new ScalingAttributeModifierSurvivalEffectType());
        create("function", new FunctionSurvivalEffectType());
    }

    public static void create(Identifier id, SurvivalEffectType<?> type) {
        VALUES.put(id, type);
    }

    private static void create(String name, SurvivalEffectType<?> type) {
        create(new Identifier(Frostiful.MODID, name), type);
    }
}
