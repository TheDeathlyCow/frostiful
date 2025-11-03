package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;

public class FBannerPatterns {

    public static final ResourceKey<BannerPattern> SNOWFLAKE = key("snowflake");
    public static final ResourceKey<BannerPattern> ICICLE = key("icicle");
    public static final ResourceKey<BannerPattern> FROSTOLOGY = key("frostology");

    public static void bootstrap(BootstrapContext<BannerPattern> registry) {
        BannerPatterns.register(registry, SNOWFLAKE);
        BannerPatterns.register(registry, ICICLE);
        BannerPatterns.register(registry, FROSTOLOGY);
    }

    private static ResourceKey<BannerPattern> key(String id) {
        return ResourceKey.create(Registries.BANNER_PATTERN, Frostiful.location(id));
    }

    private FBannerPatterns() {

    }

}
