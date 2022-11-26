package com.github.thedeathlycow.frostiful.enchantment.target;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class FEnchantmentTargetEarlyRiser implements Runnable {

    static final String FROSTIFUL_FROST_WAND = "FROSTIFUL_FROST_WAND";

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        final String enchantmentTarget = remapper.mapClassName(
                "intermediary",
                "net.minecraft.class_1886"
        );

        ClassTinkerers.enumBuilder(enchantmentTarget)
                .addEnumSubclass(
                        FROSTIFUL_FROST_WAND,
                        "com.github.thedeathlycow.frostiful.enchantment.target.FrostWandEnchantmentTarget"
                )
                .build();
    }
}
