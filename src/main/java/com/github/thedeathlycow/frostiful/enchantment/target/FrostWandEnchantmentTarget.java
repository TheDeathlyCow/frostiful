package com.github.thedeathlycow.frostiful.enchantment.target;

import com.github.thedeathlycow.frostiful.item.FrostWandItem;
import net.minecraft.item.Item;
import net.moddingplayground.frame.api.enchantment.v0.target.CustomEnchantmentTarget;

public class FrostWandEnchantmentTarget extends CustomEnchantmentTarget {
    @Override
    public boolean method_8177(Item item) {
        return item instanceof FrostWandItem;
    }

    public boolean isAcceptableItem(Item item) {
        return this.method_8177(item);
    }
}
