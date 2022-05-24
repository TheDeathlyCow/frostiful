package com.github.thedeathlycow.frostiful.enchantment;

import com.github.thedeathlycow.frostiful.config.group.CombatConfigGroup;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class HeatDrainEnchantment extends Enchantment {

    public HeatDrainEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.WEAPON, slotTypes);
    }

    public void onTargetAttacked(LivingEntity user, LivingEntity target, int level) {
        final int heatDrained = CombatConfigGroup.HEAT_DRAIN_PER_LEVEL.getValue() * level;
        FrostHelper.addLivingFrost(target, heatDrained);
        FrostHelper.removeLivingFrost(user, heatDrained);
    }
}
