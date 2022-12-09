package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SurvivalEffectTypes {

    public static final Map<Identifier, SurvivalEffectType<?>> VALUES = new HashMap<>();

    public static final SurvivalEffectType<?> STATUS_EFFECT = create("status_effect", new StatusEffectSurvivalEffectType());
    public static final SurvivalEffectType<?> SCALING_ATTRIBUTE_MODIFIER = create("scaling_attribute_modifier", new ScalingAttributeModifierSurvivalEffectType());

    public static SurvivalEffectType<?> create(Identifier id, SurvivalEffectType<?> type) {
        return VALUES.put(id, type);
    }

    private static SurvivalEffectType<?> create(String name, SurvivalEffectType<?> type) {
        return create(new Identifier(Frostiful.MODID, name), type);
    }
}
