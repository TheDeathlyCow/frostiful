package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;

public final class FArmorTrimPatterns {
    public static final ResourceKey<TrimPattern> FROSTY = key("frosty");
    public static final ResourceKey<TrimPattern> GLACIAL = key("glacial");
    public static final ResourceKey<TrimPattern> SNOW_MAN = key("snow_man");

    public static void bootstrap(BootstrapContext<TrimPattern> registry) {
        TrimPatterns.register(registry, FROSTY);
        TrimPatterns.register(registry, GLACIAL);
        TrimPatterns.register(registry, SNOW_MAN);
    }

    private static ResourceKey<TrimPattern> key(String id) {
        return ResourceKey.create(Registries.TRIM_PATTERN, Frostiful.location(id));
    }

    private FArmorTrimPatterns() {

    }
}