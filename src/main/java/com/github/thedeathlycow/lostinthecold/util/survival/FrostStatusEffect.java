package com.github.thedeathlycow.lostinthecold.util.survival;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

import java.util.List;

public record FrostStatusEffect(double progressThreshold, StatusEffect effect, int duration, int amplifier) {

    // TODO: make configurable
    private static List<FrostStatusEffect> effects = List.of(
            new FrostStatusEffect(0.5, StatusEffects.MINING_FATIGUE, 8, 0),
            new FrostStatusEffect(0.75, StatusEffects.MINING_FATIGUE, 8, 1),
            new FrostStatusEffect(0.99, StatusEffects.MINING_FATIGUE, 8, 2)
    );

    public static List<FrostStatusEffect> getPassiveFreezingEffects() {
        return effects;
    }

}
