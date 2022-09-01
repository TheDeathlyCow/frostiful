package com.github.thedeathlycow.frostiful.enchantment.target;

import com.github.thedeathlycow.frostiful.item.FrostWandItem;
import com.github.thedeathlycow.frostiful.mixins.asm.EnchantmentTargetMixin;
import net.minecraft.item.Item;

public class FrostWandEnchantmentTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof FrostWandItem;
    }
}
