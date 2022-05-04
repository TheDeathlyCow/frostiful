package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.ConfigKeys;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.simple.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;

public class FrostHelper {

    public static void addFrost(LivingEntity entity, int amount) {

        final Config config = Frostiful.getConfig();
        double frostResistance = entity.getAttributeValue(FrostifulEntityAttributes.FROST_RESISTANCE);
        double frostModifier = frostResistance * config.get(ConfigKeys.PERCENT_FROST_REDUCTION_PER_FROST_RESISTANCE);
        frostModifier /= 100.0D;

        int toAdd = (int) ((1 - frostModifier) * amount);

        int current = entity.getFrozenTicks();
        setLivingFrost(entity, current + toAdd);
    }

    public static void removeFrost(LivingEntity entity, int amount) {
        int current = entity.getFrozenTicks();
        setLivingFrost(entity, current - amount);
    }

    public static void setLivingFrost(LivingEntity entity, int amount) {
        setFrost(entity, amount);
        applyEffects(entity);
    }

    public static void setFrost(Entity entity, int amount) {
        amount = MathHelper.clamp(amount, 0, entity.getMinFreezeDamageTicks());
        entity.setFrozenTicks(amount);
    }

    private static void applyEffects(LivingEntity entity) {
        double progress = getFrostProgress(entity);
        for (FrostStatusEffect effect : FrostStatusEffect.getPassiveFreezingEffects()) {
            StatusEffectInstance effectInstance = entity.getStatusEffect(effect.effect());
            boolean applyEffect = progress >= effect.progressThreshold()
                    && (effectInstance == null || effectInstance.getAmplifier() < effect.amplifier());
            if (applyEffect) {
                entity.addStatusEffect(
                        new StatusEffectInstance(effect.effect(), effect.duration(), effect.amplifier(), true, true),
                        null
                );
            }
        }
    }

    public static double getFrostProgress(Entity entity) {
        return ((double) entity.getFrozenTicks()) / entity.getMinFreezeDamageTicks();
    }

}
