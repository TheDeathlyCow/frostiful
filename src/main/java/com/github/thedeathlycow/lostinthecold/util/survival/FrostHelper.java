package com.github.thedeathlycow.lostinthecold.util.survival;

import com.github.thedeathlycow.datapack.config.config.Config;
import com.github.thedeathlycow.lostinthecold.attributes.LostInTheColdEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.ConfigKeys;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;

public class FrostHelper {

    public static void addFrost(LivingEntity entity, int amount) {

        final Config config = LostInTheCold.getConfig();
        double frostResistance = entity.getAttributeValue(LostInTheColdEntityAttributes.FROST_RESISTANCE);
        double frostModifier = frostResistance * config.get(ConfigKeys.PERCENT_FROST_REDUCTION_PER_FROST_RESISTANCE);
        frostModifier /= 100.0D;

        int toAdd = (int) ((1 - frostModifier) * amount);

        int current = entity.getFrozenTicks();
        setFrost(entity, current + toAdd);

        double progress = getFrostProgress(entity);

        StatusEffectInstance effect = entity.getStatusEffect(StatusEffects.MINING_FATIGUE);

        if (progress >= 0.5 && (effect == null || effect.getDuration() <= 1)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 8, 0, true, true), null);
        }
    }

    public static void removeFrost(LivingEntity entity, int amount) {
        int current = entity.getFrozenTicks();
        setFrost(entity, current - amount);
    }

    public static void setFrost(Entity entity, int amount) {
        amount = MathHelper.clamp(amount, 0, entity.getMinFreezeDamageTicks());
        entity.setFrozenTicks(amount);
    }

    public static double getFrostProgress(Entity entity) {
        return ((double) entity.getFrozenTicks()) / entity.getMinFreezeDamageTicks();
    }

}
