package com.github.thedeathlycow.frostiful.datagen.generator;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FArmorTrimPatterns;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import java.util.concurrent.CompletableFuture;

public class FRecipeProvider extends FabricRecipeProvider {
    public FRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.BRITTLE_ICE)
                        .criterion(getHasName(FItems.ICICLE), has(FItemTags.ICICLES))
                        .pattern("##")
                        .pattern("##")
                        .input('#', FItems.ICICLE)
                        .offerTo(output);

                offerCutBlueIceRecipes();
                offerCutPackedIceRecipes();
                offerPackedSnowBrickRecipes();
                offerPackedSnowRecipes();

                shaped(RecipeCategory.COMBAT, FItems.FROST_WAND)
                        .criterion(getHasName(FItems.GLACIAL_HEART), has(FItems.GLACIAL_HEART))
                        .pattern("I#I")
                        .pattern(" R ")
                        .pattern(" R ")
                        .input('#', FItems.GLACIAL_HEART)
                        .input('I', FItems.ICICLE)
                        .input('R', FItems.FROZEN_ROD)
                        .offerTo(output);

                trimSmithing(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE, FArmorTrimPatterns.SNOW_MAN, upgradeRecipeKey(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE));
                trimSmithing(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE, FArmorTrimPatterns.FROSTY, upgradeRecipeKey(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE));
                trimSmithing(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE, FArmorTrimPatterns.GLACIAL, upgradeRecipeKey(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE));

                offerFurUpgradeRecipe(Items.CHAINMAIL_HELMET, FItems.FUR_PADDED_CHAINMAIL_HELMET);
                offerFurUpgradeRecipe(Items.CHAINMAIL_CHESTPLATE, FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE);
                offerFurUpgradeRecipe(Items.CHAINMAIL_LEGGINGS, FItems.FUR_PADDED_CHAINMAIL_LEGGINGS);
                offerFurUpgradeRecipe(Items.CHAINMAIL_BOOTS, FItems.FUR_PADDED_CHAINMAIL_BOOTS);

                offerSkateUpgradeRecipe(FItems.FUR_BOOTS, FItems.ICE_SKATES);
                offerSkateUpgradeRecipe(FItems.FUR_PADDED_CHAINMAIL_BOOTS, FItems.ARMORED_ICE_SKATES);

                copySmithingTemplate(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE, FItems.PACKED_SNOW_BLOCK);
                copySmithingTemplate(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE, FItems.PACKED_SNOW_BLOCK);
                copySmithingTemplate(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE, Ingredient.of(FItems.CUT_BLUE_ICE, Items.BLUE_ICE));
                copySmithingTemplate(FItems.FUR_UPGRADE_TEMPLATE, FItems.PACKED_SNOW_BLOCK);
                copySmithingTemplate(FItems.ICE_SKATE_UPGRADE_TEMPLATE, FItems.PACKED_SNOW_BLOCK);

                shaped(RecipeCategory.MISC, FItems.FROZEN_ROD)
                        .criterion(getHasName(Items.BLUE_ICE), has(Items.BLUE_ICE))
                        .pattern("#")
                        .pattern("#")
                        .pattern("#")
                        .input('#', Items.BLUE_ICE)
                        .offerTo(output);

                offerFurArmorRecipes();

                offerFurPaddingRecipe(FItems.POLAR_BEAR_FUR_TUFT, 4);
                offerFurPaddingRecipe(FItems.WOLF_FUR_TUFT, 9);
                offerFurPaddingRecipe(FItems.OCELOT_FUR_TUFT, 9);
                offerFurPaddingRecipe(Items.RABBIT_HIDE, 9);

                shaped(RecipeCategory.DECORATIONS, FItems.ICE_PANE, 16)
                        .criterion(getHasName(Items.ICE), has(Items.ICE))
                        .pattern("###")
                        .pattern("###")
                        .input('#', Items.ICE)
                        .offerTo(this.output);

                shapeless(RecipeCategory.COMBAT, FItems.GLACIAL_ARROW)
                        .criterion(getHasName(FItems.ICICLE), has(FItemTags.ICICLES))
                        .input(FItemTags.ICICLES)
                        .input(Items.ARROW)
                        .offerTo(output);

                shapeless(RecipeCategory.DECORATIONS, FItems.SNOWFLAKE_BANNER_PATTERN)
                        .criterion(getHasName(FItems.PACKED_SNOWBALL), has(FItems.PACKED_SNOWBALL))
                        .input(FItems.PACKED_SNOWBALL)
                        .input(Items.PAPER)
                        .offerTo(output);
            }

            // <editor-fold desc="Long form recipe generators">

            private void offerCutBlueIceRecipes() {
                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE, 4)
                        .criterion(getHasName(Items.BLUE_ICE), has(Items.BLUE_ICE))
                        .pattern("##")
                        .pattern("##")
                        .input('#', Items.BLUE_ICE)
                        .offerTo(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE, Items.BLUE_ICE, 4);

                stairBuilder(FItems.CUT_BLUE_ICE_STAIRS, Ingredient.of(FItems.CUT_BLUE_ICE))
                        .criterion(getHasName(FItems.CUT_BLUE_ICE), has(FItems.CUT_BLUE_ICE))
                        .offerTo(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE_STAIRS, FItems.CUT_BLUE_ICE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE_STAIRS, Items.BLUE_ICE, 4);

                slab(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_SLAB, FItems.CUT_BLUE_ICE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_SLAB, FItems.CUT_BLUE_ICE, 2);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_SLAB, Items.BLUE_ICE, 8);

                wall(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_WALL, FItems.CUT_BLUE_ICE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_WALL, FItems.CUT_BLUE_ICE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_WALL, Items.BLUE_ICE, 4);
            }

            private void offerCutPackedIceRecipes() {
                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE, 4)
                        .criterion(getHasName(Items.PACKED_ICE), has(Items.PACKED_ICE))
                        .pattern("##")
                        .pattern("##")
                        .input('#', Items.PACKED_ICE)
                        .offerTo(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE, Items.PACKED_ICE, 4);

                stairBuilder(FItems.CUT_PACKED_ICE_STAIRS, Ingredient.of(FItems.CUT_PACKED_ICE))
                        .criterion(getHasName(FItems.CUT_PACKED_ICE), has(FItems.CUT_PACKED_ICE))
                        .offerTo(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE_STAIRS, FItems.CUT_PACKED_ICE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE_STAIRS, Items.PACKED_ICE, 4);

                slab(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_SLAB, FItems.CUT_PACKED_ICE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_SLAB, FItems.CUT_PACKED_ICE, 2);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_SLAB, Items.PACKED_ICE, 8);

                wall(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_WALL, FItems.CUT_PACKED_ICE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_WALL, FItems.CUT_PACKED_ICE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_WALL, Items.PACKED_ICE, 4);
            }

            private void offerPackedSnowBrickRecipes() {
                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BRICKS, 4)
                        .criterion(getHasName(FItems.PACKED_SNOW_BLOCK), has(FItems.PACKED_SNOW_BLOCK))
                        .pattern("##")
                        .pattern("##")
                        .input('#', FItems.PACKED_SNOW_BLOCK)
                        .offerTo(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BRICKS, FItems.PACKED_SNOW_BLOCK);

                stairBuilder(FItems.PACKED_SNOW_BRICK_STAIRS, Ingredient.of(FItems.PACKED_SNOW_BRICKS))
                        .criterion(getHasName(FItems.PACKED_SNOW_BRICKS), has(FItems.PACKED_SNOW_BRICKS))
                        .offerTo(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BRICK_STAIRS, FItems.PACKED_SNOW_BRICKS);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BRICK_STAIRS, FItems.PACKED_SNOW_BLOCK);

                slab(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_SLAB, FItems.PACKED_SNOW_BRICKS);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_SLAB, FItems.PACKED_SNOW_BRICKS, 2);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_SLAB, FItems.PACKED_SNOW_BLOCK, 2);

                wall(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_WALL, FItems.PACKED_SNOW_BRICKS);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_WALL, FItems.PACKED_SNOW_BRICKS);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, FItems.PACKED_SNOW_BRICK_WALL, FItems.PACKED_SNOW_BLOCK);
            }

            private void offerPackedSnowRecipes() {
                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW, 12)
                        .criterion(getHasName(FItems.PACKED_SNOW_BLOCK), has(FItems.PACKED_SNOW_BLOCK))
                        .pattern("###")
                        .input('#', FItems.PACKED_SNOW_BLOCK)
                        .offerTo(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BLOCK, 4)
                        .criterion(getHasName(Items.SNOW_BLOCK), has(Items.SNOW_BLOCK))
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .input('#', Items.SNOW_BLOCK)
                        .offerTo(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BLOCK, 1)
                        .criterion(getHasName(FItems.PACKED_SNOWBALL), has(FItems.PACKED_SNOWBALL))
                        .pattern("##")
                        .pattern("##")
                        .input('#', FItems.PACKED_SNOWBALL)
                        .offerTo(output, "packed_snow_block_from_packed_snowball");
            }

            private void offerFurUpgradeRecipe(Item input, Item result) {
                SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(FItems.FUR_UPGRADE_TEMPLATE),
                                Ingredient.of(input),
                                Ingredient.of(FItems.FUR_PADDING),
                                RecipeCategory.COMBAT,
                                result
                        )
                        .criterion("has_fur_padding", this.has(FItems.FUR_PADDING))
                        .offerTo(this.output, getItemId(result) + "_smithing");
            }

            private void offerSkateUpgradeRecipe(Item input, Item result) {
                SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(FItems.ICE_SKATE_UPGRADE_TEMPLATE),
                                Ingredient.of(input),
                                Ingredient.of(Items.IRON_SWORD),
                                RecipeCategory.TRANSPORTATION,
                                result
                        )
                        .criterion("has_iron_sword", this.has(Items.IRON_SWORD))
                        .offerTo(this.output, getItemId(result) + "_smithing");
            }

            private void offerFurArmorRecipes() {
                final String key = "has_fur_tuft";

                shaped(RecipeCategory.COMBAT, FItems.FUR_HELMET)
                        .criterion(key, has(FItemTags.FUR_TUFTS))
                        .pattern("###")
                        .pattern("# #")
                        .input('#', FItemTags.FUR_TUFTS)
                        .offerTo(output);

                shaped(RecipeCategory.COMBAT, FItems.FUR_CHESTPLATE)
                        .criterion(key, has(FItemTags.FUR_TUFTS))
                        .pattern("# #")
                        .pattern("###")
                        .pattern("###")
                        .input('#', FItemTags.FUR_TUFTS)
                        .offerTo(output);

                shaped(RecipeCategory.COMBAT, FItems.FUR_LEGGINGS)
                        .criterion(key, has(FItemTags.FUR_TUFTS))
                        .pattern("###")
                        .pattern("# #")
                        .pattern("# #")
                        .input('#', FItemTags.FUR_TUFTS)
                        .offerTo(output);

                shaped(RecipeCategory.COMBAT, FItems.FUR_BOOTS)
                        .criterion(key, has(FItemTags.FUR_TUFTS))
                        .pattern("# #")
                        .pattern("# #")
                        .input('#', FItemTags.FUR_TUFTS)
                        .offerTo(output);
            }

            private void offerFurPaddingRecipe(ItemLike input, int amount) {
                shapeless(RecipeCategory.MISC, FItems.FUR_PADDING)
                        .group(Frostiful.id("fur_padding").toString())
                        .criterion(getHasName(input), has(input))
                        .input(input, amount)
                        .offerTo(output, furPaddingFrom(input));
            }

            // </editor-fold>

            private static ResourceKey<Recipe<?>> upgradeRecipeKey(ItemLike item) {
                return ResourceKey.create(Registries.RECIPE, Frostiful.id(getItemName(item) + "_smithing_trim"));
            }

            private static String getItemId(ItemLike item) {
                return BuiltInRegistries.ITEM.getKey(item.asItem()).toString();
            }

            private static String furPaddingFrom(ItemLike item) {
                return Frostiful.MODID + ":fur_padding_from_" + getItemName(item);
            }
        };
    }

    @Override
    public String getName() {
        return "FrostifulRecipeGenerator";
    }
}