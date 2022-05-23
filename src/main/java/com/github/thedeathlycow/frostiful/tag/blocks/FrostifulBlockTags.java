package com.github.thedeathlycow.frostiful.tag.blocks;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulBlockTags {

    public static final TagKey<Block> ICICLE_GROWABLE = register("icicle_growable");
    public static final TagKey<Block> COVERED_ROCKS_CANNOT_REPLACE = register("covered_rocks_cannot_replace");

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(Frostiful.MODID, id));
    }
}
