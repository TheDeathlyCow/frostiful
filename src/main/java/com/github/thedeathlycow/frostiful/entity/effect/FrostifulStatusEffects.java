package com.github.thedeathlycow.frostiful.entity.effect;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulStatusEffects {

    public static StatusEffect FROZEN = new FrozenStatusEffect(StatusEffectCategory.HARMFUL, 0xADD8E6).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "523d2bc4-1080-4103-a00a-9de47f85f913", 0.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    public static void registerStatusEffects() {
        register("frozen", FROZEN);
    }

    private static void register(String name, StatusEffect entry) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Frostiful.MODID, name), entry);
    }
}
