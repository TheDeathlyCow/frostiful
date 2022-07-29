package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;

public class FrostHelper {

    public static int addLivingFrost(LivingEntity entity, int amount) {
        return addLivingFrost(entity, amount, true);
    }

    public static int addLivingFrost(LivingEntity entity, int amount, boolean applyFrostResistance) {
        double frostModifier = 0.0D;

        if (applyFrostResistance) {
            double frostResistance = entity.getAttributeValue(FrostifulEntityAttributes.FROST_RESISTANCE);
            frostModifier = frostResistance * AttributeConfigGroup.PERCENT_FROST_REDUCTION_PER_FROST_RESISTANCE.getValue();
            frostModifier /= 100.0D;
        }

        int toAdd = MathHelper.ceil((1 - frostModifier) * amount);

        return addFrost((FreezableEntity) entity, toAdd);
    }

    public static void removeLivingFrost(LivingEntity entity, int amount) {
        removeFrost((FreezableEntity) entity, amount);
    }

    public static int addFrost(FreezableEntity entity, int amount) {
        entity.frostiful$addFrost(amount);
        return amount;
    }

    public static void removeFrost(FreezableEntity entity, int amount) {
        entity.frostiful$removeFrost(amount);
    }

    public static void setFrost(LivingEntity entity, int amount) {
        setFrost((FreezableEntity) entity, amount);
    }

    public static void setFrost(FreezableEntity entity, int amount) {
        entity.frostiful$setFrost(amount, true);
    }

    public static void applyEffects(LivingEntity entity) {
        float progress = ((FreezableEntity) entity).frostiful$getFrostProgress();
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
