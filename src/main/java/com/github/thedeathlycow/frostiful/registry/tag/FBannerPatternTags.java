package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class FBannerPatternTags {

    public static final TagKey<BannerPattern> SNOWFLAKE_PATTERN_ITEM = key("pattern_item/snowflake");
    public static final TagKey<BannerPattern> ICICLE_PATTERN_ITEM = key("pattern_item/icicle");
    public static final TagKey<BannerPattern> FROSTOLOGY_PATTERN_ITEM = key("pattern_item/frostology");

    private static TagKey<BannerPattern> key(String id) {
        return TagKey.create(Registries.BANNER_PATTERN, Frostiful.location(id));
    }

    private FBannerPatternTags() {

    }
}
