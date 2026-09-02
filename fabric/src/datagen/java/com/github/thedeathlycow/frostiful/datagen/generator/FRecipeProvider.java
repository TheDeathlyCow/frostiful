/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.datagen.generator;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FArmorTrimPatterns;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
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
    public FRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.BRITTLE_ICE)
                        .unlockedBy(getHasName(FItems.ICICLE), has(FItemTags.ICICLES))
                        .pattern("##")
                        .pattern("##")
                        .define('#', FItems.ICICLE)
                        .save(output);

                offerCutBlueIceRecipes();
                offerCutPackedIceRecipes();
                offerPackedSnowBrickRecipes();
                offerPackedSnowRecipes();

                shaped(RecipeCategory.COMBAT, FItems.FROST_WAND)
                        .unlockedBy(getHasName(FItems.GLACIAL_HEART), has(FItems.GLACIAL_HEART))
                        .pattern("I#I")
                        .pattern(" R ")
                        .pattern(" R ")
                        .define('#', FItems.GLACIAL_HEART)
                        .define('I', FItems.ICICLE)
                        .define('R', FItems.FROZEN_ROD)
                        .save(output);

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
                        .unlockedBy(getHasName(Items.BLUE_ICE), has(Items.BLUE_ICE))
                        .pattern("#")
                        .pattern("#")
                        .pattern("#")
                        .define('#', Items.BLUE_ICE)
                        .save(output);

                offerFurArmorRecipes();

                offerFurPaddingRecipe(FItems.POLAR_BEAR_FUR_TUFT, 4);
                offerFurPaddingRecipe(FItems.WOLF_FUR_TUFT, 9);
                offerFurPaddingRecipe(FItems.OCELOT_FUR_TUFT, 9);
                offerFurPaddingRecipe(Items.RABBIT_HIDE, 9);

                shaped(RecipeCategory.DECORATIONS, FItems.ICE_PANE, 16)
                        .unlockedBy(getHasName(Items.ICE), has(Items.ICE))
                        .pattern("###")
                        .pattern("###")
                        .define('#', Items.ICE)
                        .save(this.output);

                shapeless(RecipeCategory.COMBAT, FItems.GLACIAL_ARROW)
                        .unlockedBy(getHasName(FItems.ICICLE), has(FItemTags.ICICLES))
                        .requires(FItemTags.ICICLES)
                        .requires(Items.ARROW)
                        .save(output);

                shapeless(RecipeCategory.DECORATIONS, FItems.SNOWFLAKE_BANNER_PATTERN)
                        .unlockedBy(getHasName(FItems.PACKED_SNOWBALL), has(FItems.PACKED_SNOWBALL))
                        .requires(FItems.PACKED_SNOWBALL)
                        .requires(Items.PAPER)
                        .save(output);
            }

            // <editor-fold desc="Long form recipe generators">

            private void offerCutBlueIceRecipes() {
                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE, 4)
                        .unlockedBy(getHasName(Items.BLUE_ICE), has(Items.BLUE_ICE))
                        .pattern("##")
                        .pattern("##")
                        .define('#', Items.BLUE_ICE)
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE, Items.BLUE_ICE, 4);

                stairBuilder(FItems.CUT_BLUE_ICE_STAIRS, Ingredient.of(FItems.CUT_BLUE_ICE))
                        .unlockedBy(getHasName(FItems.CUT_BLUE_ICE), has(FItems.CUT_BLUE_ICE))
                        .save(output);
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
                        .unlockedBy(getHasName(Items.PACKED_ICE), has(Items.PACKED_ICE))
                        .pattern("##")
                        .pattern("##")
                        .define('#', Items.PACKED_ICE)
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE, Items.PACKED_ICE, 4);

                stairBuilder(FItems.CUT_PACKED_ICE_STAIRS, Ingredient.of(FItems.CUT_PACKED_ICE))
                        .unlockedBy(getHasName(FItems.CUT_PACKED_ICE), has(FItems.CUT_PACKED_ICE))
                        .save(output);
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
                        .unlockedBy(getHasName(FItems.PACKED_SNOW_BLOCK), has(FItems.PACKED_SNOW_BLOCK))
                        .pattern("##")
                        .pattern("##")
                        .define('#', FItems.PACKED_SNOW_BLOCK)
                        .save(output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BRICKS, FItems.PACKED_SNOW_BLOCK);

                stairBuilder(FItems.PACKED_SNOW_BRICK_STAIRS, Ingredient.of(FItems.PACKED_SNOW_BRICKS))
                        .unlockedBy(getHasName(FItems.PACKED_SNOW_BRICKS), has(FItems.PACKED_SNOW_BRICKS))
                        .save(output);
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
                        .unlockedBy(getHasName(FItems.PACKED_SNOW_BLOCK), has(FItems.PACKED_SNOW_BLOCK))
                        .pattern("###")
                        .define('#', FItems.PACKED_SNOW_BLOCK)
                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BLOCK, 4)
                        .unlockedBy(getHasName(Items.SNOW_BLOCK), has(Items.SNOW_BLOCK))
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', Items.SNOW_BLOCK)
                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, FItems.PACKED_SNOW_BLOCK, 1)
                        .unlockedBy(getHasName(FItems.PACKED_SNOWBALL), has(FItems.PACKED_SNOWBALL))
                        .pattern("##")
                        .pattern("##")
                        .define('#', FItems.PACKED_SNOWBALL)
                        .save(output, "packed_snow_block_from_packed_snowball");
            }

            private void offerFurUpgradeRecipe(Item define, Item result) {
                SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(FItems.FUR_UPGRADE_TEMPLATE),
                                Ingredient.of(define),
                                Ingredient.of(FItems.FUR_PADDING),
                                RecipeCategory.COMBAT,
                                result
                        )
                        .unlocks("has_fur_padding", this.has(FItems.FUR_PADDING))
                        .save(this.output, getItemId(result) + "_smithing");
            }

            private void offerSkateUpgradeRecipe(Item define, Item result) {
                SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(FItems.ICE_SKATE_UPGRADE_TEMPLATE),
                                Ingredient.of(define),
                                Ingredient.of(Items.IRON_SWORD),
                                RecipeCategory.TRANSPORTATION,
                                result
                        )
                        .unlocks("has_iron_sword", this.has(Items.IRON_SWORD))
                        .save(this.output, getItemId(result) + "_smithing");
            }

            private void offerFurArmorRecipes() {
                final String key = "has_fur_tuft";

                shaped(RecipeCategory.COMBAT, FItems.FUR_HELMET)
                        .unlockedBy(key, has(FItemTags.FUR_TUFTS))
                        .pattern("###")
                        .pattern("# #")
                        .define('#', FItemTags.FUR_TUFTS)
                        .save(output);

                shaped(RecipeCategory.COMBAT, FItems.FUR_CHESTPLATE)
                        .unlockedBy(key, has(FItemTags.FUR_TUFTS))
                        .pattern("# #")
                        .pattern("###")
                        .pattern("###")
                        .define('#', FItemTags.FUR_TUFTS)
                        .save(output);

                shaped(RecipeCategory.COMBAT, FItems.FUR_LEGGINGS)
                        .unlockedBy(key, has(FItemTags.FUR_TUFTS))
                        .pattern("###")
                        .pattern("# #")
                        .pattern("# #")
                        .define('#', FItemTags.FUR_TUFTS)
                        .save(output);

                shaped(RecipeCategory.COMBAT, FItems.FUR_BOOTS)
                        .unlockedBy(key, has(FItemTags.FUR_TUFTS))
                        .pattern("# #")
                        .pattern("# #")
                        .define('#', FItemTags.FUR_TUFTS)
                        .save(output);
            }

            private void offerFurPaddingRecipe(ItemLike define, int amount) {
                shapeless(RecipeCategory.MISC, FItems.FUR_PADDING)
                        .group(Frostiful.id("fur_padding").toString())
                        .unlockedBy(getHasName(define), has(define))
                        .requires(define, amount)
                        .save(output, furPaddingFrom(define));
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