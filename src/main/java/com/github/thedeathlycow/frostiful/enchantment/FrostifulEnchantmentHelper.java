package com.github.thedeathlycow.frostiful.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;

public class FrostifulEnchantmentHelper {

    public static boolean hasHeatDrain(LivingEntity entity) {
        return getHeatDrainLevel(entity) > 0;
    }

    public static int getHeatDrainLevel(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(FrostifulEnchantments.HEAT_DRAIN, entity);
    }
}
