package com.github.thedeathlycow.frostiful.enchantment;

import com.github.thedeathlycow.frostiful.enchantment.target.FrostifulEnchantmentTargets;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

public class IceBreakerEnchantment extends Enchantment {

    protected IceBreakerEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, FrostifulEnchantmentTargets.FROSTIFUL_FROST_WAND, slotTypes);
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

}
