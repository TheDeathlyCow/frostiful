package com.github.thedeathlycow.frostiful.enchantment;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;

public class FEnchantmentHelper {

    public static boolean hasHeatDrain(LivingEntity entity) {
        return getHeatDrainLevel(entity) > 0;
    }

    public static int getHeatDrainLevel(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(FEnchantments.ENERVATION, entity);
    }

    public static float getIceBreakerBonusDamage(LivingEntity user) {
        FrostifulConfig config = Frostiful.getConfig();
        int level = EnchantmentHelper.getEquipmentLevel(FEnchantments.ICE_BREAKER, user);
        return level * config.combatConfig.getIceBreakerDamagePerLevel();
    }
}
