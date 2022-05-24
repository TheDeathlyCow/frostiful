package com.github.thedeathlycow.frostiful.entity.effect;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulStatusEffects {

    public static final StatusEffect FROZEN = new SimpleStatusEffect(StatusEffectCategory.HARMFUL, 0xADD8E6);

    public static void registerStatusEffects() {
        register("frozen", FROZEN);
    }

    private static void register(String name, StatusEffect entry) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Frostiful.MODID, name), entry);
    }
}
