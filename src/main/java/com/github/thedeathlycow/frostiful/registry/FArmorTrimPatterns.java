package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;

public class FArmorTrimPatterns {

    public static final ResourceKey<TrimPattern> FROSTY = of("frosty");

    public static final ResourceKey<TrimPattern> GLACIAL = of("glacial");

    public static final ResourceKey<TrimPattern> SNOW_MAN = of("snow_man");

    public static void bootstrap(BootstrapContext<TrimPattern> registry) {
        Frostiful.LOGGER.debug("Bootstrap Frostiful armor trim patterns");
        register(registry, FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE, FROSTY);
        register(registry, FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE, GLACIAL);
        register(registry, FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE, SNOW_MAN);
    }

    private static void register(BootstrapContext<TrimPattern> registry, Item template, ResourceKey<TrimPattern> key) {
        TrimPattern armorTrimPattern = new TrimPattern(
                key.location(),
                BuiltInRegistries.ITEM.wrapAsHolder(template),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())),
                false
        );
        registry.register(key, armorTrimPattern);
    }
    private static ResourceKey<TrimPattern> of(String id) {
        return ResourceKey.create(Registries.TRIM_PATTERN, Frostiful.id(id));
    }

    private FArmorTrimPatterns() {

    }
}
