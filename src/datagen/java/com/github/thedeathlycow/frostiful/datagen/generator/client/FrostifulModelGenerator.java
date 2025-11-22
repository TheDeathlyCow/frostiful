package com.github.thedeathlycow.frostiful.datagen.generator.client;

import com.github.thedeathlycow.frostiful.client.render.entity.FrostWandItemRenderer;
import com.github.thedeathlycow.frostiful.registry.FArmorMaterials;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class FrostifulModelGenerator extends FabricModelProvider {
    private static final ResourceLocation HELMET_TRIM_ASSET_ID_PREFIX = ItemModelGenerators.prefixForSlotTrim("helmet");
    private static final ResourceLocation CHESTPLATE_TRIM_ASSET_ID_PREFIX = ItemModelGenerators.prefixForSlotTrim("chestplate");
    private static final ResourceLocation LEGGINGS_TRIM_ASSET_ID_PREFIX = ItemModelGenerators.prefixForSlotTrim("leggings");
    private static final ResourceLocation BOOTS_TRIM_ASSET_ID_PREFIX = ItemModelGenerators.prefixForSlotTrim("boots");

    public FrostifulModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        // not generating block states atm
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        this.registerFrostWand(FItems.FROST_WAND, itemModelGenerator);
        itemModelGenerator.generateTrimmableItem(FItems.FUR_HELMET, FArmorMaterials.FUR_ASSET, HELMET_TRIM_ASSET_ID_PREFIX, false);
        itemModelGenerator.generateTrimmableItem(FItems.FUR_CHESTPLATE, FArmorMaterials.FUR_ASSET, CHESTPLATE_TRIM_ASSET_ID_PREFIX, false);
        itemModelGenerator.generateTrimmableItem(FItems.FUR_LEGGINGS, FArmorMaterials.FUR_ASSET, LEGGINGS_TRIM_ASSET_ID_PREFIX, false);
        itemModelGenerator.generateTrimmableItem(FItems.FUR_BOOTS, FArmorMaterials.FUR_ASSET, BOOTS_TRIM_ASSET_ID_PREFIX, false);
        itemModelGenerator.generateTrimmableItem(FItems.ICE_SKATES, FArmorMaterials.FUR_ASSET, BOOTS_TRIM_ASSET_ID_PREFIX, false);

        itemModelGenerator.generateTrimmableItem(FItems.FUR_PADDED_CHAINMAIL_HELMET, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, HELMET_TRIM_ASSET_ID_PREFIX, false);
        itemModelGenerator.generateTrimmableItem(FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, CHESTPLATE_TRIM_ASSET_ID_PREFIX, false);
        itemModelGenerator.generateTrimmableItem(FItems.FUR_PADDED_CHAINMAIL_LEGGINGS, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, LEGGINGS_TRIM_ASSET_ID_PREFIX, false);
        itemModelGenerator.generateTrimmableItem(FItems.FUR_PADDED_CHAINMAIL_BOOTS, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, BOOTS_TRIM_ASSET_ID_PREFIX, false);
        itemModelGenerator.generateTrimmableItem(FItems.ARMORED_ICE_SKATES, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, BOOTS_TRIM_ASSET_ID_PREFIX, false);

        itemModelGenerator.declareCustomModelItem(FItems.FUR_PADDING);
        itemModelGenerator.declareCustomModelItem(FItems.FUR_UPGRADE_TEMPLATE);
        itemModelGenerator.declareCustomModelItem(FItems.ICE_SKATE_UPGRADE_TEMPLATE);
        itemModelGenerator.declareCustomModelItem(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE);
        itemModelGenerator.declareCustomModelItem(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE);
        itemModelGenerator.declareCustomModelItem(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE);

        itemModelGenerator.declareCustomModelItem(FItems.GLACIAL_HEART);
        itemModelGenerator.declareCustomModelItem(FItems.INERT_FROSTOLOGY_CLOAK);
        itemModelGenerator.declareCustomModelItem(FItems.FROSTOLOGY_CLOAK);
        itemModelGenerator.declareCustomModelItem(FItems.FROZEN_ROD);

        itemModelGenerator.declareCustomModelItem(FItems.POLAR_BEAR_FUR_TUFT);
        itemModelGenerator.declareCustomModelItem(FItems.WOLF_FUR_TUFT);
        itemModelGenerator.declareCustomModelItem(FItems.OCELOT_FUR_TUFT);

        itemModelGenerator.declareCustomModelItem(FItems.ICICLE);

        itemModelGenerator.declareCustomModelItem(FItems.COLD_SUN_LICHEN);
        itemModelGenerator.declareCustomModelItem(FItems.COOL_SUN_LICHEN);
        itemModelGenerator.declareCustomModelItem(FItems.WARM_SUN_LICHEN);
        itemModelGenerator.declareCustomModelItem(FItems.HOT_SUN_LICHEN);

        itemModelGenerator.declareCustomModelItem(FItems.GLACIAL_ARROW);
        itemModelGenerator.declareCustomModelItem(FItems.FROZEN_TORCH);
        itemModelGenerator.declareCustomModelItem(FItems.PACKED_SNOW);
        itemModelGenerator.declareCustomModelItem(FItems.PACKED_SNOWBALL);

        itemModelGenerator.declareCustomModelItem(FItems.PACKED_SNOW_BLOCK);
        itemModelGenerator.declareCustomModelItem(FItems.PACKED_SNOW_BRICKS);
        itemModelGenerator.declareCustomModelItem(FItems.PACKED_SNOW_BRICK_STAIRS);
        itemModelGenerator.declareCustomModelItem(FItems.PACKED_SNOW_BRICK_SLAB);
        itemModelGenerator.declareCustomModelItem(FItems.PACKED_SNOW_BRICK_WALL);

        itemModelGenerator.declareCustomModelItem(FItems.ICE_PANE);
        itemModelGenerator.declareCustomModelItem(FItems.CUT_PACKED_ICE);
        itemModelGenerator.declareCustomModelItem(FItems.CUT_PACKED_ICE_STAIRS);
        itemModelGenerator.declareCustomModelItem(FItems.CUT_PACKED_ICE_SLAB);
        itemModelGenerator.declareCustomModelItem(FItems.CUT_PACKED_ICE_WALL);
        itemModelGenerator.declareCustomModelItem(FItems.CUT_BLUE_ICE);
        itemModelGenerator.declareCustomModelItem(FItems.CUT_BLUE_ICE_STAIRS);
        itemModelGenerator.declareCustomModelItem(FItems.CUT_BLUE_ICE_SLAB);
        itemModelGenerator.declareCustomModelItem(FItems.CUT_BLUE_ICE_WALL);
        itemModelGenerator.declareCustomModelItem(FItems.BRITTLE_ICE);

        itemModelGenerator.declareCustomModelItem(FItems.SNOWFLAKE_BANNER_PATTERN);
        itemModelGenerator.declareCustomModelItem(FItems.ICICLE_BANNER_PATTERN);
        itemModelGenerator.declareCustomModelItem(FItems.FROSTOLOGY_BANNER_PATTERN);
        itemModelGenerator.declareCustomModelItem(FItems.ICY_TRIAL_SPAWNER);
        itemModelGenerator.declareCustomModelItem(FItems.ICY_VAULT);
        itemModelGenerator.declareCustomModelItem(FItems.CASTLE_KEY);
        itemModelGenerator.declareCustomModelItem(FItems.OMINOUS_CASTLE_KEY);

        itemModelGenerator.generateFlatItem(FItems.FROSTOLOGER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(FItems.CHILLAGER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(FItems.BITER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
    }

    private void registerFrostWand(Item item, ItemModelGenerators itemModelGenerator) {
        ItemModel.Unbaked sprite = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));

        ItemModel.Unbaked inHand = ItemModelUtils.specialModel(
                ModelLocationUtils.getModelLocation(item, "_in_hand"),
                new FrostWandItemRenderer.Unbaked()
        );

        itemModelGenerator.itemModelOutput.accept(item, ItemModelGenerators.createFlatModelDispatch(sprite, inHand));
    }
}