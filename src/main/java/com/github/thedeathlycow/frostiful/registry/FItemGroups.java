package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class FItemGroups {


    public static final ItemGroup FROSTIFUL = FabricItemGroup.builder()
            .icon(() -> new ItemStack(FItems.FROST_WAND))
            .displayName(Text.translatable("itemGroup.frostiful.frostiful"))
            .entries((context, entries) -> {
                entries.add(new ItemStack(FItems.FROST_WAND));
                entries.add(new ItemStack(FItems.FUR_HELMET));
                entries.add(new ItemStack(FItems.FUR_CHESTPLATE));
                entries.add(new ItemStack(FItems.FUR_LEGGINGS));
                entries.add(new ItemStack(FItems.FUR_BOOTS));
                entries.add(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_HELMET));
                entries.add(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE));
                entries.add(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_LEGGINGS));
                entries.add(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_BOOTS));

                entries.add(new ItemStack(FItems.FROSTOLOGY_CLOAK));
                entries.add(new ItemStack(FItems.ICE_SKATES));
                entries.add(new ItemStack(FItems.ARMORED_ICE_SKATES));

                entries.add(new ItemStack(FItems.POLAR_BEAR_FUR_TUFT));
                entries.add(new ItemStack(FItems.WOLF_FUR_TUFT));
                entries.add(new ItemStack(FItems.OCELOT_FUR_TUFT));
                entries.add(new ItemStack(FItems.FUR_PADDING));

                entries.add(new ItemStack(FItems.COLD_SUN_LICHEN));
                entries.add(new ItemStack(FItems.COOL_SUN_LICHEN));
                entries.add(new ItemStack(FItems.WARM_SUN_LICHEN));
                entries.add(new ItemStack(FItems.HOT_SUN_LICHEN));

                entries.add(new ItemStack(FItems.PACKED_SNOW));
                entries.add(new ItemStack(FItems.PACKED_SNOW_BLOCK));
                entries.add(new ItemStack(FItems.PACKED_SNOW_BRICKS));
                entries.add(new ItemStack(FItems.PACKED_SNOW_BRICK_STAIRS));
                entries.add(new ItemStack(FItems.PACKED_SNOW_BRICK_SLAB));
                entries.add(new ItemStack(FItems.PACKED_SNOW_BRICK_WALL));
                entries.add(new ItemStack(FItems.PACKED_SNOWBALL));

                entries.add(new ItemStack(FItems.ICE_PANE));
                entries.add(new ItemStack(FItems.CUT_PACKED_ICE));
                entries.add(new ItemStack(FItems.CUT_PACKED_ICE_STAIRS));
                entries.add(new ItemStack(FItems.CUT_PACKED_ICE_SLAB));
                entries.add(new ItemStack(FItems.CUT_PACKED_ICE_WALL));
                entries.add(new ItemStack(FItems.CUT_BLUE_ICE));
                entries.add(new ItemStack(FItems.CUT_BLUE_ICE_STAIRS));
                entries.add(new ItemStack(FItems.CUT_BLUE_ICE_SLAB));
                entries.add(new ItemStack(FItems.CUT_BLUE_ICE_WALL));

                entries.add(new ItemStack(FItems.FUR_UPGRADE_TEMPLATE));
                entries.add(new ItemStack(FItems.ICE_SKATE_UPGRADE_TEMPLATE));
                entries.add(new ItemStack(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE));
                entries.add(new ItemStack(FItems.SNOWFLAKE_BANNER_PATTERN));

                entries.add(new ItemStack(FItems.ICICLE));
                entries.add(new ItemStack(FItems.FROST_TIPPED_ARROW));
                entries.add(new ItemStack(FItems.FROZEN_TORCH));

                entries.add(new ItemStack(FItems.FROSTOLOGER_SPAWN_EGG));
                entries.add(new ItemStack(FItems.CHILLAGER_SPAWN_EGG));
                entries.add(new ItemStack(FItems.BITER_SPAWN_EGG));
            }).build();

    public static void registerAll() {
        Registry.register(Registries.ITEM_GROUP, Frostiful.id("main"), FROSTIFUL);
    }

    private FItemGroups() {
    }
}
