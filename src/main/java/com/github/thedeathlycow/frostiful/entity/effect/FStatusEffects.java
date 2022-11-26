package com.github.thedeathlycow.frostiful.entity.effect;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FStatusEffects {

    public static final String WARMTH_MODIFIER_ID = "c374d1af-dbea-4d2c-bbb1-7d1702a0ab6b";

    public static final StatusEffect WARMTH = new SimpleStatusEffect(StatusEffectCategory.BENEFICIAL, 0xE3963E)
            .addAttributeModifier(FEntityAttributes.FROST_RESISTANCE, WARMTH_MODIFIER_ID, 1.0, EntityAttributeModifier.Operation.ADDITION);

    public static void registerStatusEffects() {
        register("warmth", WARMTH);
    }

    private static void register(String name, StatusEffect entry) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Frostiful.MODID, name), entry);
    }
}
