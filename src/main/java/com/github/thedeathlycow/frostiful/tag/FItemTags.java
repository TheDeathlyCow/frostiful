package com.github.thedeathlycow.frostiful.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public final class FItemTags {

    public static final TagKey<Item> FUR = register("fur");

    public static final TagKey<Item> POWDER_SNOW_WALKABLE = register("powder_snow_walkable");

    public static final TagKey<Item> SUN_LICHENS = register("sun_lichens");


    private static TagKey<Item> register(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(Frostiful.MODID, id));
    }

}
