package com.github.thedeathlycow.frostiful.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/**
 * A simple status effect that is not instant
 * and does not have updates.
 */
public class SimpleStatusEffect extends StatusEffect {
    public SimpleStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }
}
