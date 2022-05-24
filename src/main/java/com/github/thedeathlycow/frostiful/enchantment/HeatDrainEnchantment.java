package com.github.thedeathlycow.frostiful.enchantment;

import com.github.thedeathlycow.frostiful.config.group.CombatConfigGroup;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class HeatDrainEnchantment extends Enchantment {

    public HeatDrainEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.WEAPON, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        int heatDrained = CombatConfigGroup.HEAT_DRAIN_PER_LEVEL.getValue() * level;

        if (target instanceof LivingEntity livingTarget) {
            heatDrained = FrostHelper.addLivingFrost(livingTarget, heatDrained);
        } else {
            heatDrained = FrostHelper.addFrost(target, heatDrained);
        }

        FrostHelper.removeLivingFrost(user, heatDrained);
    }
}
