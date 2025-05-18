package com.github.thedeathlycow.frostiful.datagen.generator;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FArmorTrimPatterns;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class FRecipeProvider extends FabricRecipeProvider {
    public FRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.BRITTLE_ICE)
                        .criterion(hasItem(FItems.ICICLE), conditionsFromTag(FItemTags.ICICLES))
                        .pattern("##")
                        .pattern("##")
                        .input('#', FItems.ICICLE)
                        .offerTo(exporter);

                offerCutBlueIceRecipes();
                offerCutPackedIceRecipes();
                offerPackedSnowBrickRecipes();
                offerPackedSnowRecipes();

                createShaped(RecipeCategory.COMBAT, FItems.FROST_WAND)
                        .criterion(hasItem(FItems.GLACIAL_HEART), conditionsFromItem(FItems.GLACIAL_HEART))
                        .pattern("I#I")
                        .pattern(" R ")
                        .pattern(" R ")
                        .input('#', FItems.GLACIAL_HEART)
                        .input('I', FItems.ICICLE)
                        .input('R', FItems.FROZEN_ROD)
                        .offerTo(exporter);

                offerSmithingTrimRecipe(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE, FArmorTrimPatterns.SNOW_MAN, upgradeRecipeKey(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE));
                offerSmithingTrimRecipe(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE, FArmorTrimPatterns.FROSTY, upgradeRecipeKey(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE));
                offerSmithingTrimRecipe(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE, FArmorTrimPatterns.GLACIAL, upgradeRecipeKey(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE));

                offerFurUpgradeRecipe(Items.CHAINMAIL_HELMET, FItems.FUR_PADDED_CHAINMAIL_HELMET);
                offerFurUpgradeRecipe(Items.CHAINMAIL_CHESTPLATE, FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE);
                offerFurUpgradeRecipe(Items.CHAINMAIL_LEGGINGS, FItems.FUR_PADDED_CHAINMAIL_LEGGINGS);
                offerFurUpgradeRecipe(Items.CHAINMAIL_BOOTS, FItems.FUR_PADDED_CHAINMAIL_BOOTS);

                offerSkateUpgradeRecipe(FItems.FUR_BOOTS, FItems.ICE_SKATES);
                offerSkateUpgradeRecipe(FItems.FUR_PADDED_CHAINMAIL_BOOTS, FItems.ARMORED_ICE_SKATES);

                offerSmithingTemplateCopyingRecipe(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE, FItems.PACKED_SNOW_BLOCK);
                offerSmithingTemplateCopyingRecipe(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE, FItems.PACKED_SNOW_BLOCK);
                offerSmithingTemplateCopyingRecipe(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE, Ingredient.ofItems(FItems.CUT_BLUE_ICE, Items.BLUE_ICE));
                offerSmithingTemplateCopyingRecipe(FItems.FUR_UPGRADE_TEMPLATE, FItems.PACKED_SNOW_BLOCK);
                offerSmithingTemplateCopyingRecipe(FItems.ICE_SKATE_UPGRADE_TEMPLATE, FItems.PACKED_SNOW_BLOCK);

                createShaped(RecipeCategory.MISC, FItems.FROZEN_ROD)
                        .criterion(hasItem(Items.BLUE_ICE), conditionsFromItem(Items.BLUE_ICE))
                        .pattern("#")
                        .pattern("#")
                        .pattern("#")
                        .input('#', Items.BLUE_ICE)
                        .offerTo(exporter);

                offerFurArmorRecipes();

                offerFurPaddingRecipe(FItems.POLAR_BEAR_FUR_TUFT, 4);
                offerFurPaddingRecipe(FItems.WOLF_FUR_TUFT, 9);
                offerFurPaddingRecipe(FItems.OCELOT_FUR_TUFT, 9);
                offerFurPaddingRecipe(Items.RABBIT_HIDE, 9);

                createShaped(RecipeCategory.DECORATIONS, FItems.ICE_PANE, 16)
                        .criterion(hasItem(Items.ICE), conditionsFromItem(Items.ICE))
                        .pattern("###")
                        .pattern("###")
                        .input('#', Items.ICE)
                        .offerTo(this.exporter);

                createShapeless(RecipeCategory.COMBAT, FItems.GLACIAL_ARROW)
                        .criterion(hasItem(FItems.ICICLE), conditionsFromTag(FItemTags.ICICLES))
                        .input(FItemTags.ICICLES)
                        .input(Items.ARROW)
                        .offerTo(exporter);

                createShapeless(RecipeCategory.DECORATIONS, FItems.SNOWFLAKE_BANNER_PATTERN)
                        .criterion(hasItem(FItems.PACKED_SNOWBALL), conditionsFromItem(FItems.PACKED_SNOWBALL))
                        .input(FItems.PACKED_SNOWBALL)
                        .input(Items.PAPER)
                        .offerTo(exporter);
            }

            // <editor-fold desc="Long form recipe generators">

            private void offerCutBlueIceRecipes() {
                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE, 4)
                        .criterion(hasItem(Items.BLUE_ICE), conditionsFromItem(Items.BLUE_ICE))
                        .pattern("##")
                        .pattern("##")
                        .input('#', Items.BLUE_ICE)
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE, Items.BLUE_ICE, 4);

                createStairsRecipe(FItems.CUT_BLUE_ICE_STAIRS, Ingredient.ofItem(FItems.CUT_BLUE_ICE))
                        .criterion(hasItem(FItems.CUT_BLUE_ICE), conditionsFromItem(FItems.CUT_BLUE_ICE))
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE_STAIRS, FItems.CUT_BLUE_ICE);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE_STAIRS, Items.BLUE_ICE, 4);

                offerSlabRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_SLAB, FItems.CUT_BLUE_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_SLAB, FItems.CUT_BLUE_ICE, 2);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_SLAB, Items.BLUE_ICE, 8);

                offerWallRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_WALL, FItems.CUT_BLUE_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_WALL, FItems.CUT_BLUE_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_WALL, Items.BLUE_ICE, 4);
            }

            private void offerCutPackedIceRecipes() {
                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE, 4)
                        .criterion(hasItem(Items.PACKED_ICE), conditionsFromItem(Items.PACKED_ICE))
                        .pattern("##")
                        .pattern("##")
                        .input('#', Items.PACKED_ICE)
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE, Items.PACKED_ICE, 4);

                createStairsRecipe(FItems.CUT_PACKED_ICE_STAIRS, Ingredient.ofItem(FItems.CUT_PACKED_ICE))
                        .criterion(hasItem(FItems.CUT_PACKED_ICE), conditionsFromItem(FItems.CUT_PACKED_ICE))
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE_STAIRS, FItems.CUT_PACKED_ICE);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE_STAIRS, Items.PACKED_ICE, 4);

                offerSlabRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_SLAB, FItems.CUT_PACKED_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_SLAB, FItems.CUT_PACKED_ICE, 2);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_SLAB, Items.PACKED_ICE, 8);

                offerWallRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_WALL, FItems.CUT_PACKED_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_WALL, FItems.CUT_PACKED_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_WALL, Items.PACKED_ICE, 4);
            }

            private void offerPackedSnowBrickRecipes() {
                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BRICKS, 4)
                        .criterion(hasItem(FItems.PACKED_SNOW_BLOCK), conditionsFromItem(FItems.PACKED_SNOW_BLOCK))
                        .pattern("##")
                        .pattern("##")
                        .input('#', FItems.PACKED_SNOW_BLOCK)
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BRICKS, FItems.PACKED_SNOW_BLOCK);

                createStairsRecipe(FItems.PACKED_SNOW_BRICK_STAIRS, Ingredient.ofItem(FItems.PACKED_SNOW_BRICKS))
                        .criterion(hasItem(FItems.PACKED_SNOW_BRICKS), conditionsFromItem(FItems.PACKED_SNOW_BRICKS))
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BRICK_STAIRS, FItems.PACKED_SNOW_BRICKS);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BRICK_STAIRS, FItems.PACKED_SNOW_BLOCK);

                offerSlabRecipe(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_SLAB, FItems.PACKED_SNOW_BRICKS);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_SLAB, FItems.PACKED_SNOW_BRICKS, 2);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_SLAB, FItems.PACKED_SNOW_BLOCK, 2);

                offerWallRecipe(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_WALL, FItems.PACKED_SNOW_BRICKS);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_WALL, FItems.PACKED_SNOW_BRICKS);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_WALL, FItems.PACKED_SNOW_BLOCK);
            }

            private void offerPackedSnowRecipes() {
                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW, 12)
                        .criterion(hasItem(FItems.PACKED_SNOW_BLOCK), conditionsFromItem(FItems.PACKED_SNOW_BLOCK))
                        .pattern("###")
                        .input('#', FItems.PACKED_SNOW_BLOCK)
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BLOCK, 4)
                        .criterion(hasItem(Items.SNOW_BLOCK), conditionsFromItem(Items.SNOW_BLOCK))
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .input('#', Items.SNOW_BLOCK)
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BLOCK, 1)
                        .criterion(hasItem(FItems.PACKED_SNOWBALL), conditionsFromItem(FItems.PACKED_SNOWBALL))
                        .pattern("##")
                        .pattern("##")
                        .input('#', FItems.PACKED_SNOWBALL)
                        .offerTo(exporter, "packed_snow_block_from_packed_snowball");
            }

            private void offerFurUpgradeRecipe(Item input, Item result) {
                SmithingTransformRecipeJsonBuilder.create(
                                Ingredient.ofItem(FItems.FUR_UPGRADE_TEMPLATE),
                                Ingredient.ofItem(input),
                                Ingredient.ofItem(FItems.FUR_PADDING),
                                RecipeCategory.COMBAT,
                                result
                        )
                        .criterion("has_fur_padding", this.conditionsFromItem(FItems.FUR_PADDING))
                        .offerTo(this.exporter, getItemId(result) + "_smithing");
            }

            private void offerSkateUpgradeRecipe(Item input, Item result) {
                SmithingTransformRecipeJsonBuilder.create(
                                Ingredient.ofItem(FItems.ICE_SKATE_UPGRADE_TEMPLATE),
                                Ingredient.ofItem(input),
                                Ingredient.ofItem(Items.IRON_SWORD),
                                RecipeCategory.TRANSPORTATION,
                                result
                        )
                        .criterion("has_iron_sword", this.conditionsFromItem(Items.IRON_SWORD))
                        .offerTo(this.exporter, getItemId(result) + "_smithing");
            }

            private void offerFurArmorRecipes() {
                final String key = "has_fur_tuft";

                createShaped(RecipeCategory.COMBAT, FItems.FUR_HELMET)
                        .criterion(key, conditionsFromTag(FItemTags.FUR_TUFTS))
                        .pattern("###")
                        .pattern("# #")
                        .input('#', FItemTags.FUR_TUFTS)
                        .offerTo(exporter);

                createShaped(RecipeCategory.COMBAT, FItems.FUR_CHESTPLATE)
                        .criterion(key, conditionsFromTag(FItemTags.FUR_TUFTS))
                        .pattern("# #")
                        .pattern("###")
                        .pattern("###")
                        .input('#', FItemTags.FUR_TUFTS)
                        .offerTo(exporter);

                createShaped(RecipeCategory.COMBAT, FItems.FUR_LEGGINGS)
                        .criterion(key, conditionsFromTag(FItemTags.FUR_TUFTS))
                        .pattern("###")
                        .pattern("# #")
                        .pattern("# #")
                        .input('#', FItemTags.FUR_TUFTS)
                        .offerTo(exporter);

                createShaped(RecipeCategory.COMBAT, FItems.FUR_BOOTS)
                        .criterion(key, conditionsFromTag(FItemTags.FUR_TUFTS))
                        .pattern("# #")
                        .pattern("# #")
                        .input('#', FItemTags.FUR_TUFTS)
                        .offerTo(exporter);
            }

            private void offerFurPaddingRecipe(ItemConvertible input, int amount) {
                createShapeless(RecipeCategory.MISC, FItems.FUR_PADDING)
                        .group(Frostiful.id("fur_padding").toString())
                        .criterion(hasItem(input), conditionsFromItem(input))
                        .input(input, amount)
                        .offerTo(exporter, furPaddingFrom(input));
            }

            // </editor-fold>

            private static RegistryKey<Recipe<?>> upgradeRecipeKey(ItemConvertible item) {
                return RegistryKey.of(RegistryKeys.RECIPE, Frostiful.id(getItemPath(item) + "_smithing_trim"));
            }

            private static String getItemId(ItemConvertible item) {
                return Registries.ITEM.getId(item.asItem()).toString();
            }

            private static String furPaddingFrom(ItemConvertible item) {
                return Frostiful.MODID + ":fur_padding_from_" + getItemPath(item);
            }
        };
    }

    @Override
    public String getName() {
        return "FrostifulRecipeGenerator";
    }
}