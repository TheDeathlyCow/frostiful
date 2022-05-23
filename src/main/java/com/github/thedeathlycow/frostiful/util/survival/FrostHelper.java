package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;

public class FrostHelper {

    public static void addLivingFrost(LivingEntity entity, int amount) {
        addLivingFrost(entity, amount, true);
    }

    public static void addLivingFrost(LivingEntity entity, int amount, boolean applyFrostResistance) {
        double frostModifier = 0.0D;

        if (applyFrostResistance) {
            double frostResistance = entity.getAttributeValue(FrostifulEntityAttributes.FROST_RESISTANCE);
            frostModifier = frostResistance * AttributeConfigGroup.PERCENT_FROST_REDUCTION_PER_FROST_RESISTANCE.getValue();
            frostModifier /= 100.0D;
        }

        int toAdd = (int) ((1 - frostModifier) * amount);

        int current = entity.getFrozenTicks();
        setLivingFrost(entity, current + toAdd);
    }

    public static void removeLivingFrost(LivingEntity entity, int amount) {
        int current = entity.getFrozenTicks();
        setLivingFrost(entity, current - amount);
    }

    public static void setLivingFrost(LivingEntity entity, int amount) {
        setFrost(entity, amount);
        applyEffects(entity);
    }

    public static void addFrost(Entity entity, int amount) {
        int current = entity.getFrozenTicks();
        setFrost(entity, current + amount);
    }

    public static void removeFrost(Entity entity, int amount) {
        int current = entity.getFrozenTicks();
        setFrost(entity, current - amount);
    }

    public static void setFrost(Entity entity, int amount) {
        amount = MathHelper.clamp(amount, 0, entity.getMinFreezeDamageTicks());
        entity.setFrozenTicks(amount);
    }

    public static void applyEffects(LivingEntity entity) {
        float progress = entity.getFreezingScale();
        for (FrostStatusEffect effect : FrostStatusEffect.getPassiveFreezingEffects()) {
            StatusEffectInstance currentEffectInstance = entity.getStatusEffect(effect.effect());
            boolean shouldApplyEffect = progress >= effect.progressThreshold()
                    && (currentEffectInstance == null || currentEffectInstance.getAmplifier() < effect.amplifier());
            if (shouldApplyEffect) {
                entity.addStatusEffect(
                        effect.createEffectInstance(),
                        null
                );
            }
        }
    }

}
