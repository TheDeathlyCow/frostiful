package com.github.thedeathlycow.lostinthecold.tag.blocks;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LostInTheColdBlockTags {

    public static final TagKey<Block> SNOW_ACCUMULATE_NEXT_TO = register("snow_accumulate_next_to");

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(LostInTheCold.MODID, id));
    }
}
