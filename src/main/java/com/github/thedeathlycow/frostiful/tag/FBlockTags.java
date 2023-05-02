package com.github.thedeathlycow.frostiful.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.block.Block;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.Registry;

public class FBlockTags {

    public static final TagKey<Block> ICICLE_GROWABLE = register("icicle_growable");
    public static final TagKey<Block> COVERED_ROCKS_CANNOT_REPLACE = register("covered_rocks_cannot_replace");
    public static final TagKey<Block> COVERED_ROCK_COVERING_REPLACEABLE = register("covered_rock_covering_replaceable");
    public static final TagKey<Block> SUN_LICHENS = register("sun_lichens");
    public static final TagKey<Block> FROSTOLOGER_CANNOT_FREEZE = register("frostologer_cannot_freeze");
    public static final TagKey<Block> FROZEN_TORCHES = register("frozen_torches");
    public static final TagKey<Block> HAS_OPEN_FLAME = register("has_open_flame");
    public static final TagKey<Block> IS_OPEN_FLAME = register("is_open_flame");
    public static final TagKey<Block> HOT_FLOOR = register("hot_floor");

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, Frostiful.id(id));
    }
}
