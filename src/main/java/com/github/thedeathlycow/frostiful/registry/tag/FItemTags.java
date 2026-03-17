package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class FItemTags {
    public static final TagKey<Item> C_ICICLES = convention("icicles");
    public static final TagKey<Item> ENCHANTABLE_ICE_SKATES = register("enchantable/ice_skates");
    public static final TagKey<Item> ENCHANTABLE_FROST_WAND = register("enchantable/frost_wand");
    public static final TagKey<Item> FUR_ARMOR = register("fur_armor");
    public static final TagKey<Item> FUR_BOOTS = register("fur_boots");
    public static final TagKey<Item> FUR_TUFTS = register("fur_tufts");
    public static final TagKey<Item> ICE_SKATES = register("ice_skates");
    public static final TagKey<Item> ICICLES = register("icicles");
    public static final TagKey<Item> POWDER_SNOW_WALKABLE = register("powder_snow_walkable");
    public static final TagKey<Item> REPAIRS_FUR_ARMOR = register("repairs_fur_armor");
    public static final TagKey<Item> REPAIRS_FUR_LINED_CHAINMAIL_ARMOR = register("repairs_fur_lined_chainmail_armor");
    public static final TagKey<Item> REPAIRS_FROST_WAND = register("repairs_frost_wand");
    public static final TagKey<Item> SUN_LICHENS = register("sun_lichens");
    public static final TagKey<Item> SUPPORTS_HEAT_DRAIN = register("supports_heat_drain");
    public static final TagKey<Item> WARM_FOODS = register("warm_foods");

    private static TagKey<Item> register(String id) {
        return TagKey.create(Registries.ITEM, Frostiful.id(id));
    }

    private static TagKey<Item> convention(String id) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", id));
    }

    private FItemTags() {

    }
}
