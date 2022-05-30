package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import net.minecraft.entity.Entity;
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

        return addFrost(entity, toAdd);
    }

    public static int removeLivingFrost(LivingEntity entity, int amount) {
        removeFrost(entity, amount);
        return amount;
    }

    public static int addFrost(Entity entity, int amount) {
        int current = entity.getFrozenTicks();
        return setFrost(entity, current + amount);
    }

    public static int removeFrost(Entity entity, int amount) {
        int current = entity.getFrozenTicks();
        setFrost(entity, current - amount);
        return amount;
    }

    public static int setFrost(Entity entity, int amount) {
        amount = MathHelper.clamp(amount, 0, entity.getMinFreezeDamageTicks());
        entity.setFrozenTicks(amount);
        return amount;
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
