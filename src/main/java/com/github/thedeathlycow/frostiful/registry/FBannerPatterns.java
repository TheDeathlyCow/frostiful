package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class FBannerPatterns {

    public static final RegistryKey<BannerPattern> SNOWFLAKE = key("snowflake");

    public static void register(Registry<BannerPattern> registry) {
        Registry.register(registry, SNOWFLAKE, new BannerPattern("frostiful_snowflake"));
    }

    private static RegistryKey<BannerPattern> key(String id) {
        return RegistryKey.of(RegistryKeys.BANNER_PATTERN, Frostiful.id(id));
    }

    private FBannerPatterns() {

    }

}
