package com.github.thedeathlycow.frostiful.enchantment.target;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.moddingplayground.frame.api.enchantment.v0.FrameEnchantmentTargetsEntrypoint;
import net.moddingplayground.frame.api.enchantment.v0.target.EnchantmentTargetInfo;
import net.moddingplayground.frame.impl.enchantment.EnchantmentTargetManager;

public class FrostifulEnchantmentTargetsEntryPoint implements FrameEnchantmentTargetsEntrypoint {

    static EnchantmentTargetInfo FROST_WAND = null;

    @Override
    public void registerEnchantmentTargets(EnchantmentTargetManager manager) {
        FROST_WAND = manager.register(
                Frostiful.id("frost_wand"),
                "com.github.thedeathlycow.frostiful.enchantment.target.FrostWandEnchantmentTarget"
        );
    }
}
