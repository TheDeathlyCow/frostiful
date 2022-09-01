package com.github.thedeathlycow.frostiful.enchantment.target;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.enchantment.EnchantmentTarget;

public class FrostifulEnchantmentTargets {

    public static final EnchantmentTarget FROSTIFUL_FROST_WAND = ClassTinkerers.getEnum(
            EnchantmentTarget.class,
            FrostifulEnchantmentTargetASM.FROSTIFUL_FROST_WAND
    );

}
