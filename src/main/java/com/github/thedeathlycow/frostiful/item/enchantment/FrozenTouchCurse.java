package com.github.thedeathlycow.frostiful.item.enchantment;

import com.github.thedeathlycow.frostiful.registry.FEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public final class FrozenTouchCurse extends EnervationEnchantment {

    public FrozenTouchCurse(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, slotTypes);
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    public boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != FEnchantments.ENERVATION;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity livingTarget) {
            // swap target and user
            super.onTargetDamaged(livingTarget, user, level);
        }
    }

}
