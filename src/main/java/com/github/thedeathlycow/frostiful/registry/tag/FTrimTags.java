package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.armortrim.TrimPattern;

public class FTrimTags {

    public static final TagKey<TrimPattern> CUSTOM_PATTERNS = pattern("custom_patterns");

    private static TagKey<TrimPattern> pattern(String id) {
        return TagKey.create(Registries.TRIM_PATTERN, Frostiful.id(id));
    }

    private FTrimTags() {

    }

}
