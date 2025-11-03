package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class FItemGroups {
    public static final CreativeModeTab FROSTIFUL = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            Frostiful.location("main"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(FItems.FROST_WAND))
                    .title(Component.translatable("itemGroup.frostiful.frostiful"))
                    .displayItems((context, entries) -> {
                        entries.accept(new ItemStack(FItems.FROST_WAND));
                        entries.accept(new ItemStack(FItems.INERT_FROSTOLOGY_CLOAK));
                        addEnchantedFrostologyCloak(context, entries);
                        entries.accept(new ItemStack(FItems.FROZEN_ROD));
                        entries.accept(new ItemStack(FItems.GLACIAL_HEART));

                        entries.accept(new ItemStack(FItems.FUR_HELMET));
                        entries.accept(new ItemStack(FItems.FUR_CHESTPLATE));
                        entries.accept(new ItemStack(FItems.FUR_LEGGINGS));
                        entries.accept(new ItemStack(FItems.FUR_BOOTS));
                        entries.accept(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_HELMET));
                        entries.accept(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE));
                        entries.accept(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_LEGGINGS));
                        entries.accept(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_BOOTS));
                        entries.accept(new ItemStack(FItems.ICE_SKATES));
                        entries.accept(new ItemStack(FItems.ARMORED_ICE_SKATES));

                        entries.accept(new ItemStack(FItems.POLAR_BEAR_FUR_TUFT));
                        entries.accept(new ItemStack(FItems.WOLF_FUR_TUFT));
                        entries.accept(new ItemStack(FItems.OCELOT_FUR_TUFT));
                        entries.accept(new ItemStack(FItems.FUR_PADDING));

                        entries.accept(new ItemStack(FItems.COLD_SUN_LICHEN));
                        entries.accept(new ItemStack(FItems.COOL_SUN_LICHEN));
                        entries.accept(new ItemStack(FItems.WARM_SUN_LICHEN));
                        entries.accept(new ItemStack(FItems.HOT_SUN_LICHEN));

                        entries.accept(new ItemStack(FItems.PACKED_SNOW));
                        entries.accept(new ItemStack(FItems.PACKED_SNOW_BLOCK));
                        entries.accept(new ItemStack(FItems.PACKED_SNOW_BRICKS));
                        entries.accept(new ItemStack(FItems.PACKED_SNOW_BRICK_STAIRS));
                        entries.accept(new ItemStack(FItems.PACKED_SNOW_BRICK_SLAB));
                        entries.accept(new ItemStack(FItems.PACKED_SNOW_BRICK_WALL));
                        entries.accept(new ItemStack(FItems.PACKED_SNOWBALL));

                        entries.accept(new ItemStack(FItems.ICE_PANE));
                        entries.accept(new ItemStack(FItems.BRITTLE_ICE));
                        entries.accept(new ItemStack(FItems.CUT_PACKED_ICE));
                        entries.accept(new ItemStack(FItems.CUT_PACKED_ICE_STAIRS));
                        entries.accept(new ItemStack(FItems.CUT_PACKED_ICE_SLAB));
                        entries.accept(new ItemStack(FItems.CUT_PACKED_ICE_WALL));
                        entries.accept(new ItemStack(FItems.CUT_BLUE_ICE));
                        entries.accept(new ItemStack(FItems.CUT_BLUE_ICE_STAIRS));
                        entries.accept(new ItemStack(FItems.CUT_BLUE_ICE_SLAB));
                        entries.accept(new ItemStack(FItems.CUT_BLUE_ICE_WALL));

                        entries.accept(new ItemStack(FItems.FUR_UPGRADE_TEMPLATE));
                        entries.accept(new ItemStack(FItems.ICE_SKATE_UPGRADE_TEMPLATE));
                        entries.accept(new ItemStack(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE));
                        entries.accept(new ItemStack(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE));
                        entries.accept(new ItemStack(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE));
                        entries.accept(new ItemStack(FItems.SNOWFLAKE_BANNER_PATTERN));
                        entries.accept(new ItemStack(FItems.ICICLE_BANNER_PATTERN));
                        entries.accept(new ItemStack(FItems.FROSTOLOGY_BANNER_PATTERN));

                        entries.accept(new ItemStack(FItems.ICICLE));
                        entries.accept(new ItemStack(FItems.GLACIAL_ARROW));
                        entries.accept(new ItemStack(FItems.FROZEN_TORCH));

                        entries.accept(new ItemStack(FItems.ICY_TRIAL_SPAWNER));
                        entries.accept(new ItemStack(FItems.ICY_VAULT));
                        entries.accept(new ItemStack(FItems.CASTLE_KEY));
                        entries.accept(new ItemStack(FItems.OMINOUS_CASTLE_KEY));

                        entries.accept(new ItemStack(FItems.FROSTOLOGER_SPAWN_EGG));
                        entries.accept(new ItemStack(FItems.CHILLAGER_SPAWN_EGG));
                        entries.accept(new ItemStack(FItems.BITER_SPAWN_EGG));
                    }).build()
    );


    private static void addEnchantedFrostologyCloak(CreativeModeTab.ItemDisplayParameters context, CreativeModeTab.Output entries) {
        ItemStack frostologyCloak = new ItemStack(FItems.FROSTOLOGY_CLOAK);
        context.holders()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(Enchantments.BINDING_CURSE)
                .ifPresent(bindingCurse -> frostologyCloak.enchant(bindingCurse, 1));
        entries.accept(frostologyCloak);
    }

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful item groups");
    }

    private FItemGroups() {
    }
}
