package com.github.thedeathlycow.frostiful.item.enchantment;

import com.github.thedeathlycow.frostiful.registry.FEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class FEnchantmentHelper {

    public static boolean hasHeatDrain(@NotNull LivingEntity entity) {
        return getHeatDrainLevel(entity) > 0;
    }

    public static int getHeatDrainLevel(@NotNull LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(FEnchantments.ENERVATION, entity);
    }

    public static int getIceBreakerLevel(@NotNull LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(FEnchantments.ICE_BREAKER, entity);
    }

    private FEnchantmentHelper() {
    }
}
