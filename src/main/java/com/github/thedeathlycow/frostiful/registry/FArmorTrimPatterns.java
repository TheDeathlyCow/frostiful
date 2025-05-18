package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.item.equipment.trim.ArmorTrimPatterns;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public final class FArmorTrimPatterns {
    public static final RegistryKey<ArmorTrimPattern> FROSTY = key("frosty");
    public static final RegistryKey<ArmorTrimPattern> GLACIAL = key("glacial");
    public static final RegistryKey<ArmorTrimPattern> SNOW_MAN = key("snow_man");

    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        ArmorTrimPatterns.register(registry, FROSTY);
        ArmorTrimPatterns.register(registry, GLACIAL);
        ArmorTrimPatterns.register(registry, SNOW_MAN);
    }

    private static RegistryKey<ArmorTrimPattern> key(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, Frostiful.id(id));
    }

    private FArmorTrimPatterns() {

    }
}