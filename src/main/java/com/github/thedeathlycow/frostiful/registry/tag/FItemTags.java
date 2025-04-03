package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class FItemTags {

    public static final TagKey<Item> ICICLES = register("icicles");

    public static final TagKey<Item> POWDER_SNOW_WALKABLE = register("powder_snow_walkable");

    public static final TagKey<Item> SUN_LICHENS = register("sun_lichens");

    public static final TagKey<Item> ICE_SKATES = register("ice_skates");

    public static final TagKey<Item> WARM_FOODS = register("warm_foods");

    public static final TagKey<Item> REPAIRS_FUR_ARMOR = register("repairs_fur_armor");
    public static final TagKey<Item> REPAIRS_FUR_LINED_CHAINMAIL_ARMOR = register("repairs_fur_lined_chainmail_armor");
    public static final TagKey<Item> REPAIRS_FROST_WAND = register("repairs_frost_wand");
    public static final TagKey<Item> FUR_TUFTS = register("fur_tufts");

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, Frostiful.id(id));
    }

}
