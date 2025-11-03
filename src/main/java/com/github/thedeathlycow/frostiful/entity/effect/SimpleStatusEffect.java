package com.github.thedeathlycow.frostiful.entity.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * A simple status effect that is not instant
 * and does not have updates.
 */
public class SimpleStatusEffect extends MobEffect {
    public SimpleStatusEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }
}
