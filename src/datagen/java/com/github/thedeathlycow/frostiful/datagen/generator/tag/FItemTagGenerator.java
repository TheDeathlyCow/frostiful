package com.github.thedeathlycow.frostiful.datagen.generator.tag;

import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class FItemTagGenerator extends FabricTagProvider.ItemTagProvider {


    public FItemTagGenerator(
            FabricDataOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture,
            BlockTagProvider blockTagProvider
    ) {
        super(output, registriesFuture, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        generateFrostifulTags(wrapperLookup);
        generateConventionalTags(wrapperLookup);
        generateMinecraftTags(wrapperLookup);
    }

    private void generateFrostifulTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(FItemTags.ENCHANTABLE_ICE_SKATES)
                .addOptionalTag(FItemTags.ICE_SKATES);

        valueLookupBuilder(FItemTags.ENCHANTABLE_FROST_WAND)
                .add(FItems.FROST_WAND);

        valueLookupBuilder(FItemTags.FUR_ARMOR)
                .add(FItems.FUR_HELMET)
                .add(FItems.FUR_CHESTPLATE)
                .add(FItems.FUR_LEGGINGS)
                .add(FItems.FUR_BOOTS)
                .add(FItems.FUR_PADDED_CHAINMAIL_HELMET)
                .add(FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE)
                .add(FItems.FUR_PADDED_CHAINMAIL_LEGGINGS)
                .add(FItems.FUR_PADDED_CHAINMAIL_BOOTS);

        valueLookupBuilder(FItemTags.FUR_BOOTS)
                .add(FItems.FUR_BOOTS)
                .add(FItems.FUR_PADDED_CHAINMAIL_BOOTS);

        valueLookupBuilder(FItemTags.ICE_SKATES)
                .add(FItems.ICE_SKATES)
                .add(FItems.ARMORED_ICE_SKATES);

        valueLookupBuilder(FItemTags.FUR_TUFTS)
                .add(FItems.POLAR_BEAR_FUR_TUFT)
                .add(FItems.WOLF_FUR_TUFT)
                .add(FItems.OCELOT_FUR_TUFT)
                .add(Items.RABBIT_HIDE);

        valueLookupBuilder(FItemTags.ICICLES)
                .add(FItems.ICICLE)
                .addOptionalTag(commonKey("icicles"));

        getOrCreateRawBuilder(FItemTags.ICICLES)
                .addOptionalElement(Identifier.fromNamespaceAndPath("immersive_weathering", "icicle"));

        valueLookupBuilder(FItemTags.POWDER_SNOW_WALKABLE)
                .add(Items.LEATHER_BOOTS)
                .add(FItems.FUR_BOOTS)
                .add(FItems.FUR_PADDED_CHAINMAIL_BOOTS)
                .add(Items.LEATHER_HORSE_ARMOR);

        valueLookupBuilder(FItemTags.REPAIRS_FROST_WAND)
                .add(FItems.FROZEN_ROD);

        valueLookupBuilder(FItemTags.REPAIRS_FUR_ARMOR)
                .addOptionalTag(FItemTags.FUR_TUFTS);

        valueLookupBuilder(FItemTags.REPAIRS_FUR_LINED_CHAINMAIL_ARMOR)
                .add(Items.IRON_INGOT)
                .addOptionalTag(ConventionalItemTags.IRON_INGOTS);

        copy(FBlockTags.SUN_LICHENS, FItemTags.SUN_LICHENS);

        valueLookupBuilder(FItemTags.SUPPORTS_HEAT_DRAIN)
                .addOptionalTag(FItemTags.ENCHANTABLE_FROST_WAND)
                .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE);
    }

    private void generateConventionalTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS)
                .add(FItems.FROST_WAND);

        valueLookupBuilder(ConventionalItemTags.RANGED_WEAPON_TOOLS)
                .add(FItems.FROST_WAND);

        valueLookupBuilder(ConventionalItemTags.SPEAR_TOOLS)
                .add(FItems.FROST_WAND);

        valueLookupBuilder(ConventionalItemTags.GLASS_PANES)
                .add(FItems.ICE_PANE);

        valueLookupBuilder(ConventionalItemTags.GLASS_BLOCKS_COLORLESS)
                .add(FItems.ICE_PANE);

        valueLookupBuilder(commonKey("icicles"))
                .add(FItems.ICICLE);

        valueLookupBuilder(ConventionalItemTags.HUMANOID_ARMORS)
                .addOptionalTag(FItemTags.FUR_ARMOR);

        valueLookupBuilder(ConventionalItemTags.ENCHANTABLES)
                .addOptionalTag(FItemTags.ENCHANTABLE_FROST_WAND)
                .addOptionalTag(FItemTags.ENCHANTABLE_ICE_SKATES);

        valueLookupBuilder(ConventionalItemTags.RODS)
                .add(FItems.FROZEN_ROD);
    }

    private void generateMinecraftTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(ItemTags.EQUIPPABLE_ENCHANTABLE)
                .add(FItems.FROSTOLOGY_CLOAK);

        valueLookupBuilder(ItemTags.WEAPON_ENCHANTABLE)
                .add(FItems.FROST_WAND);

        valueLookupBuilder(ItemTags.ARROWS)
                .add(FItems.GLACIAL_ARROW);

        valueLookupBuilder(ItemTags.CHEST_ARMOR)
                .add(FItems.FUR_CHESTPLATE)
                .add(FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE);

        valueLookupBuilder(ItemTags.FOOT_ARMOR)
                .addOptionalTag(FItemTags.FUR_BOOTS)
                .addOptionalTag(FItemTags.ICE_SKATES);

        valueLookupBuilder(ItemTags.HEAD_ARMOR)
                .add(FItems.FUR_HELMET)
                .add(FItems.FUR_PADDED_CHAINMAIL_HELMET);

        valueLookupBuilder(ItemTags.LEG_ARMOR)
                .add(FItems.FUR_BOOTS)
                .add(FItems.FUR_PADDED_CHAINMAIL_BOOTS);

        valueLookupBuilder(ItemTags.SLABS)
                .add(FItems.PACKED_SNOW_BRICK_SLAB)
                .add(FItems.CUT_BLUE_ICE_SLAB)
                .add(FItems.CUT_PACKED_ICE_SLAB);

        valueLookupBuilder(ItemTags.SLABS)
                .add(FItems.PACKED_SNOW_BRICK_STAIRS)
                .add(FItems.CUT_BLUE_ICE_STAIRS)
                .add(FItems.CUT_PACKED_ICE_STAIRS);

        valueLookupBuilder(ItemTags.WALLS)
                .add(FItems.PACKED_SNOW_BRICK_WALL)
                .add(FItems.CUT_BLUE_ICE_WALL)
                .add(FItems.CUT_PACKED_ICE_WALL);

        valueLookupBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(FItems.FUR_HELMET)
                .add(FItems.FUR_CHESTPLATE)
                .add(FItems.FUR_LEGGINGS)
                .add(FItems.FUR_BOOTS)
                .add(FItems.ICE_SKATES)
                .add(FItems.FUR_PADDED_CHAINMAIL_HELMET)
                .add(FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE)
                .add(FItems.FUR_PADDED_CHAINMAIL_LEGGINGS)
                .add(FItems.FUR_PADDED_CHAINMAIL_BOOTS)
                .add(FItems.ARMORED_ICE_SKATES);
    }

    private static TagKey<Item> commonKey(String path) {
        return key("c", path);
    }

    private static TagKey<Item> key(String id, String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(id, path));
    }
}