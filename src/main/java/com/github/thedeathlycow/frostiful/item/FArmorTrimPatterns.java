package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class FArmorTrimPatterns {

    public static final RegistryKey<ArmorTrimPattern> FROSTY = of("frosty");

    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        register(registry, FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE, FROSTY);
    }

    private static void register(Registerable<ArmorTrimPattern> registry, Item template, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(
                key.getValue(),
                Registries.ITEM.getEntry(template),
                Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())),
                false
        );
        registry.register(key, armorTrimPattern);
    }
    private static RegistryKey<ArmorTrimPattern> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, Frostiful.id(id));
    }

    private FArmorTrimPatterns() {

    }
}
