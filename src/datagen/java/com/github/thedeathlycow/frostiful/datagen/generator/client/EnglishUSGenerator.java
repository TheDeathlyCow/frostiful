package com.github.thedeathlycow.frostiful.datagen.generator.client;

import com.github.thedeathlycow.frostiful.FrostifulModMenu;
import com.github.thedeathlycow.frostiful.client.config.section.DisplaySettings;
import com.github.thedeathlycow.frostiful.config.DifficultySetting;
import com.github.thedeathlycow.frostiful.config.Translate;
import com.github.thedeathlycow.frostiful.config.section.*;
import com.github.thedeathlycow.frostiful.datagen.generator.loot.FChestLootGenerator;
import com.github.thedeathlycow.frostiful.item.FrostedBanner;
import com.github.thedeathlycow.frostiful.item.component.SimpleTooltipComponent;
import com.github.thedeathlycow.frostiful.registry.*;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.survival.wind.WindSpawnMethod;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class EnglishUSGenerator extends FabricLanguageProvider {
    private static final Map<DyeColor, String> COLOR_NAMES = Util.make(new EnumMap<>(DyeColor.class), map -> {
        map.put(DyeColor.WHITE, "White");
        map.put(DyeColor.LIGHT_GRAY, "Light Gray");
        map.put(DyeColor.GRAY, "Gray");
        map.put(DyeColor.BLACK, "Black");
        map.put(DyeColor.BROWN, "Brown");
        map.put(DyeColor.RED, "Red");
        map.put(DyeColor.ORANGE, "Orange");
        map.put(DyeColor.YELLOW, "Yellow");
        map.put(DyeColor.LIME, "Lime");
        map.put(DyeColor.GREEN, "Green");
        map.put(DyeColor.CYAN, "Cyan");
        map.put(DyeColor.LIGHT_BLUE, "Light Blue");
        map.put(DyeColor.BLUE, "Blue");
        map.put(DyeColor.PURPLE, "Purple");
        map.put(DyeColor.MAGENTA, "Magenta");
    });

    public EnglishUSGenerator(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
        HolderLookup<BannerPattern> bannerPatterns = provider.lookupOrThrow(Registries.BANNER_PATTERN);
        HolderLookup<DamageType> damageTypes = provider.lookupOrThrow(Registries.DAMAGE_TYPE);

        builder.add("itemGroup.frostiful", "Frostiful");

        builder.add(FItems.FROST_WAND, "Frost Wand");
        builder.add(FItems.FUR_HELMET, "Fur Hood");
        builder.add(FItems.FUR_CHESTPLATE, "Fur Cloak");
        builder.add(FItems.FUR_LEGGINGS, "Fur Pants");
        builder.add(FItems.FUR_BOOTS, "Fur Boots");

        builder.add(FItems.FUR_PADDED_CHAINMAIL_HELMET, "Fur Padded Chainmail Helmet");
        builder.add(FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE, "Fur Padded Chainmail Chestplate");
        builder.add(FItems.FUR_PADDED_CHAINMAIL_LEGGINGS, "Fur Padded Chainmail Leggings");
        builder.add(FItems.FUR_PADDED_CHAINMAIL_BOOTS, "Fur Padded Chainmail Boots");

        builder.add(FItems.FUR_PADDING, "Fur Padding");
        builder.add(FItems.GLACIAL_ARROW, "Glacial Arrow");
        builder.add(FItems.POLAR_BEAR_FUR_TUFT, "Tuft of Polar Bear Fur");
        builder.add(FItems.WOLF_FUR_TUFT, "Tuft of Wolf Fur");
        builder.add(FItems.OCELOT_FUR_TUFT, "Tuft of Ocelot Fur");

        builder.add(FItems.FROSTOLOGER_SPAWN_EGG, "Frostologer Spawn Egg");
        builder.add(FItems.CHILLAGER_SPAWN_EGG, "Chillager Spawn Egg");
        builder.add(FItems.BITER_SPAWN_EGG, "Biter Spawn Egg");

        builder.add(FItems.PACKED_SNOWBALL, "Packed Snowball");

        builder.add(FItems.GLACIAL_HEART, "Glacial Heart");
        builder.add(FItems.INERT_FROSTOLOGY_CLOAK, "Inert Cloak of Frostology");
        builder.add(SimpleTooltipComponent.INERT_KEY, "Inert");
        builder.add(FItems.FROSTOLOGY_CLOAK, "Cloak of Frostology");
        builder.add(SimpleTooltipComponent.ICE_LIKE_KEY, "Reborn in Ice");
        builder.add(FItems.FROZEN_ROD, "Frozen Rod");

        builder.add(FItems.ICE_SKATES, "Ice Skates");
        builder.add(FItems.ARMORED_ICE_SKATES, "Armored Ice Skates");

        builder.add(FItems.CASTLE_KEY, "Castle Key");
        builder.add(FItems.OMINOUS_CASTLE_KEY, "Ominous Castle Key");

        builder.add("item.frostiful.warming.tooltip", "Warming \uD83D\uDD25");

        builder.add(potionItem(Items.POTION, FPotions.FREEZING), "Potion of Freezing");
        builder.add(potionItem(Items.SPLASH_POTION, FPotions.FREEZING), "Splash Potion of Freezing");
        builder.add(potionItem(Items.LINGERING_POTION, FPotions.FREEZING), "Lingering Potion of Freezing");
        builder.add(potionItem(Items.TIPPED_ARROW, FPotions.FREEZING), "Arrow of Freezing");

        builder.add(FItems.FUR_UPGRADE_TEMPLATE, "Fur Padded Chainmail Upgrade");
        builder.add(FItems.ICE_SKATE_UPGRADE_TEMPLATE, "Ice Skate Upgrade");

        smithingTemplate(
                builder,
                "fur_upgrade",
                "Chainmail Armor",
                "Fur Padding",
                "Place a piece of chainmail armor here",
                "Put some Fur Padding here"
        );

        smithingTemplate(
                builder,
                "ice_skate_upgrade",
                "Fur and Fur Padded Boots",
                "Iron Sword",
                "Place a Fur or Fur Padded boot here",
                "Put an Iron Sword here"
        );

        builder.add(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE, "Frosty Armor Trim");
        trimPattern(builder, FArmorTrimPatterns.FROSTY, "Frosty Armor Trim");

        builder.add(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE, "Glacial Armor Trim");
        trimPattern(builder, FArmorTrimPatterns.GLACIAL, "Glacial Armor Trim");

        builder.add(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE, "Snow Man Armor Trim");
        trimPattern(builder, FArmorTrimPatterns.SNOW_MAN, "Snow Man Armor Trim");

        builder.add(FItems.SNOWFLAKE_BANNER_PATTERN, "Snowflake Banner Pattern");
        builder.add(FItems.ICICLE_BANNER_PATTERN, "Icicle Banner Pattern");
        builder.add(FItems.FROSTOLOGY_BANNER_PATTERN, "Frostology Banner Pattern");

        builder.add(FChestLootGenerator.FILLED_MAP_TRANSLATION_KEY, "Snowy Castle Explorer Map");

        builder.add(FBlockTags.C_ICICLES, "Icicles");
        builder.add(FItemTags.C_ICICLES, "Icicles");
        builder.add(FItemTags.CHILLAGER_LORD_CLOAK, "Cloak of the Chillager Lord");
        builder.add(FItemTags.ICICLES, "Icicles");
        builder.add(FItemTags.FUR_TUFTS, "Fur Tufts");
        builder.add(FItemTags.WARM_FOODS, "Warm Foods");
        builder.add(FItemTags.ICE_SKATES, "Ice Skates");
        builder.add(FItemTags.SUN_LICHENS, "Sun Lichens");
        builder.add(FItemTags.FUR_BOOTS, "Fur Boots");
        builder.add(FItemTags.FUR_ARMOR, "Fur Armor");
        builder.add(FItemTags.POWDER_SNOW_WALKABLE, "Powder Snow Walkable");
        builder.add(FItemTags.ENCHANTABLE_FROST_WAND, "Enchantable Frost Wand");
        builder.add(FItemTags.ENCHANTABLE_ICE_SKATES, "Enchantable Ice Skates");
        builder.add(FItemTags.SUPPORTS_HEAT_DRAIN, "Supports Enervation and Curse of Frozen Touch");
        builder.add(FItemTags.REPAIRS_FROST_WAND, "Repairs Frost Wand");
        builder.add(FItemTags.REPAIRS_FUR_ARMOR, "Repairs Fur Armor");
        builder.add(FItemTags.REPAIRS_FUR_LINED_CHAINMAIL_ARMOR, "Repairs Fur Lined Chainmail Armor");

        builder.add(FBlocks.ICICLE, "Icicle");

        builder.add(FBlocks.COLD_SUN_LICHEN, "Cold Sun Lichen");
        builder.add(FBlocks.COOL_SUN_LICHEN, "Cool Sun Lichen");
        builder.add(FBlocks.WARM_SUN_LICHEN, "Warm Sun Lichen");
        builder.add(FBlocks.HOT_SUN_LICHEN, "Hot Sun Lichen");

        builder.add(FBlocks.FROZEN_TORCH, "Frozen Torch");

        builder.add(FBlocks.PACKED_SNOW, "Packed Snow");
        builder.add(FBlocks.PACKED_SNOW_BLOCK, "Packed Snow Block");
        builder.add(FBlocks.PACKED_SNOW_BRICKS, "Packed Snow Bricks");
        builder.add(FBlocks.PACKED_SNOW_BRICK_STAIRS, "Packed Snow Stairs");
        builder.add(FBlocks.PACKED_SNOW_BRICK_SLAB, "Packed Snow Slab");
        builder.add(FBlocks.PACKED_SNOW_BRICK_WALL, "Packed Snow Wall");

        builder.add(FBlocks.ICE_PANE, "Ice Pane");

        builder.add(FBlocks.CUT_PACKED_ICE, "Cut Packed Ice");
        builder.add(FBlocks.CUT_PACKED_ICE_STAIRS, "Cut Packed Ice Stairs");
        builder.add(FBlocks.CUT_PACKED_ICE_SLAB, "Cut Packed Ice Slab");
        builder.add(FBlocks.CUT_PACKED_ICE_WALL, "Cut Packed Ice Wall");

        builder.add(FBlocks.CUT_BLUE_ICE, "Cut Blue Ice");
        builder.add(FBlocks.CUT_BLUE_ICE_STAIRS, "Cut Blue Ice Stairs");
        builder.add(FBlocks.CUT_BLUE_ICE_SLAB, "Cut Blue Ice Slab");
        builder.add(FBlocks.CUT_BLUE_ICE_WALL, "Cut Blue Ice Wall");

        builder.add(FBlocks.ICY_TRIAL_SPAWNER, "Icy Trial Spawner");
        builder.add(FBlocks.ICY_VAULT, "Icy Vault");

        builder.add(FBlocks.BRITTLE_ICE, "Brittle Ice");

        builder.add(FrostedBanner.FROSTED_BANNER_TRANSLATION_KEY, "Frosted Banner");

        bannerColorVariants(builder, bannerPatterns.getOrThrow(FBannerPatterns.SNOWFLAKE), "Snowflake");
        bannerColorVariants(builder, bannerPatterns.getOrThrow(FBannerPatterns.ICICLE), "Icicle");
        bannerColorVariants(builder, bannerPatterns.getOrThrow(FBannerPatterns.FROSTOLOGY), "Frostology");

        builder.add(FEntityTypes.FROST_SPELL, "Frost Spell");
        builder.add(FEntityTypes.PACKED_SNOWBALL, "Packed Snowball");
        builder.add(FEntityTypes.GLACIAL_ARROW, "Glacial Arrow");
        builder.add(FEntityTypes.FROSTOLOGER, "Frostologer");
        builder.add(FEntityTypes.CHILLAGER, "Chillager");
        builder.add(FEntityTypes.BITER, "Biter");
        builder.add(FEntityTypes.THROWN_ICICLE, "Thrown Icicle");
        builder.add(FEntityTypes.FREEZING_WIND, "Freezing Wind");

        builder.add(FEntityAttributes.ICE_BREAKER_DAMAGE, "Ice Breaker Damage");

        builder.add(rootCommand("single"), "Rooted %s for %s ticks");
        builder.add(rootCommand("multiple"), "Rooted %s targets for %s ticks");

        builder.add(gameruleCategory(FGameRules.SURVIVAL_CATEGORY), "Frostiful");
        gameRule(builder, FGameRules.ENABLE_ENVIRONMENT_FREEZING, "Enable environmental freezing", "If enabled, players will be slowly frozen by the environment over time. Players wearing a Cloak of Frostology are unaffected by this.");

        addAdvancement(builder, FAdvancements.ROOT, "Frostiful", "A freezing temperature mod");
        addAdvancement(builder, FAdvancements.FIND_CHILLAGER_OUTPOST, "Making Camp", "Find a Chillager Outpost");
        addAdvancement(builder, FAdvancements.FIND_FROSTOLOGER_CASTLE, "Packed In", "Find the Frostologer's Castle");
        addAdvancement(builder, FAdvancements.KILL_FROSTOLOGER, "Just Getting Warmed Up", "Defeat the Frostologer");
        addAdvancement(builder, FAdvancements.OBTAIN_FROSTOLOGY_CLOAK, "I'm the Frostologer Now!", "Obtain the Cloak of Frostology");
        addAdvancement(builder, FAdvancements.FREEZE_CREEPERS, "Stop Right There!", "Freeze 3 creepers with one shot using the Frost Wand");
        addAdvancement(builder, FAdvancements.TRIM_WITH_PACKED_SNOW_PATTERNS, "Frosty the Snow Man", "Apply both the Frosty and Snow Man trim patterns at least once");
        addAdvancement(builder, FAdvancements.TRIM_WITH_GLACIAL_PATTERN, "King in the North", "Apply the Glacial trim pattern at least once");
        addAdvancement(builder, FAdvancements.BRUSH_POLAR_BEAR, "If not friend, why friend shaped?", "Brush a Polar Bear for its fur");
        addAdvancement(builder, FAdvancements.CRAFT_ANY_FUR_ARMOR, "Winter Wardrobe", "Keep yourself warm with Fur Armor");
        addAdvancement(builder, FAdvancements.CHAIN_FUR_ARMOR, "Armored Furry", "Obtain a full suit of Fur Padded Chainmail Armor");
        addAdvancement(builder, FAdvancements.OBTAIN_ICE_SKATES, "Sword Shoes", "Craft a pair of Ice Skates in the Smithing Table");
        addAdvancement(builder, FAdvancements.WARM_BY_LIGHT, "A Warm and Beautiful Light", "Use light to keep warm");
        addAdvancement(builder, FAdvancements.ADD_LOG_TO_CAMPFIRE, "Timber Hearth", "Add a log to a campfire");
        addAdvancement(builder, FAdvancements.STEP_ON_SUN_LICHEN, "Ouch! That Burns!", "Use Sun Lichen to warm yourself");

        addDamageType(
                builder,
                damageTypes.getOrThrow(FDamageTypes.ICICLE),
                "%1$s was impaled on an icicle",
                "%1$s was impaled on an icicle whilst fighting %2$s"
        );
        addDamageType(
                builder,
                damageTypes.getOrThrow(FDamageTypes.FALLING_ICICLE),
                "%1$s was skewered by a falling icicle",
                "%1$s was skewered by a falling icicle whilst fighting %2$s"
        );
        addDamageType(
                builder,
                damageTypes.getOrThrow(FDamageTypes.ICE_SKATE),
                "%1$s got a skate to the face",
                "%1$s got a skate to the face from %2$s"
        );
        addDamageType(
                builder,
                damageTypes.getOrThrow(FDamageTypes.MELT),
                "%1$s melted",
                "%1$s was turned into a puddle by %2$s"
        );
        addDamageType(
                builder,
                damageTypes.getOrThrow(FDamageTypes.BROKEN_ICE),
                "%1$s was shattered into a million pieces",
                "%1$s was shattered into a million pieces by %2$s"
        );

        builder.add(FSoundEvents.SUN_LICHEN_DISCHARGE, "Sun lichen discharges heat");
        builder.add(FSoundEvents.CAMPFIRE_HISS, "Campfire hisses");
        builder.add(FSoundEvents.ITEM_FROST_WAND_CAST_SPELL, "Frost wand fires spell");
        builder.add(FSoundEvents.ITEM_FROST_WAND_PREPARE_CAST, "Frost wand charges spell");
        builder.add(FSoundEvents.ENTITY_FROST_SPELL_FREEZE, "Entity freezes");
        builder.add(FSoundEvents.ENTITY_FROSTOLOGER_AMBIENT, "Frostologer murmurs");
        builder.add(FSoundEvents.ENTITY_FROSTOLOGER_CAST_SPELL, "Frostologer casts spell");
        builder.add(FSoundEvents.ENTITY_FROSTOLOGER_PREPARE_CAST_BLIZZARD, "Frostologer channels blizzard");
        builder.add(FSoundEvents.ENTITY_FROSTOLOGER_CELEBRATE, "Frostologer cheers");
        builder.add(FSoundEvents.ENTITY_FROSTOLOGER_DEATH, "Frostologer dies");
        builder.add(FSoundEvents.ENTITY_FROSTOLOGER_HURT, "Frostologer hurts");
        builder.add(FSoundEvents.ENTITY_CHILLAGER_AMBIENT, "Chillager chills");
        builder.add(FSoundEvents.ENTITY_CHILLAGER_CELEBRATE, "Chillager cheers");
        builder.add(FSoundEvents.ENTITY_CHILLAGER_DEATH, "Chillager dies");
        builder.add(FSoundEvents.ENTITY_CHILLAGER_HURT, "Chillager hurts");
        builder.add(FSoundEvents.ENTITY_THROWN_ICICLE_HIT, "Icicle hits");
        builder.add(FSoundEvents.ENTITY_THROWN_ICICLE_THROW, "Icicle flies");
        builder.add(FSoundEvents.ENTITY_BITER_AMBIENT, "Biter stomach rumbles");
        builder.add(FSoundEvents.ENTITY_BITER_DEATH, "Biter dies");
        builder.add(FSoundEvents.ENTITY_BITER_HURT, "Biter hurts");
        builder.add(FSoundEvents.ENTITY_BITER_BITE, "Biter chomps");
        builder.add(FSoundEvents.ENTITY_BITER_BURP, "Biter burps");
        builder.add(FSoundEvents.ENTITY_WIND_BLOW, "Wind rustles");
        builder.add(FSoundEvents.ENTITY_WIND_HOWL, "Wind howls");
        builder.add(FSoundEvents.ENTITY_FREEZING_WIND_BLOWOUT, "Fire blows out");
        builder.add(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_SKATE, "Skates push");
        builder.add(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_GLIDE, "Skates glide");
        builder.add(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_STOP, "Skates stop");
        builder.add(FSoundEvents.BLOCK_BRITTLE_ICE_CRACK, "Ice cracks");
        builder.add(FSoundEvents.ENTITY_BREAK_BINDING_CURSE, "Curse of Binding breaks");
        builder.add(FSoundEvents.ITEM_ARMOR_EQUIP_FUR.value(), "Fur armor rustles");

        effect(builder, FStatusEffects.FROST_BITE, "Frost Bite");
        effect(builder, FStatusEffects.WARMTH, "Warmth");

        enchantment(builder, FEnchantments.ENERVATION, "Enervation", "Steals heat from cold enemies when attacking");
        enchantment(builder, FEnchantments.ICE_BREAKER, "Ice Breaker", "Increases the damage from attacking enemies frozen in ice");
        enchantment(builder, FEnchantments.FROZEN_TOUCH_CURSE, "Curse of Frozen Touch", "Transfers heat from you to your enemies when attacking");
        enchantment(builder, FEnchantments.ICE_SPEED, "Ice Speed", "Increases skating speed on ice");

        builder.add(tip("freezing_wind_extinguishes_fire"), "Freezing Wind will blow out open flames");
        builder.add(tip("stay_warm_near_light"), "Keep warm by staying near artificial light (torches, glowstone)");
        builder.add(tip("frostologer_benefits_from_being_cold"), "The Frostologer is powered by the Cold, warm them up!");
        builder.add(tip("walk_on_sun_lichen"), "Walk on Sun Lichen to warm up in a pinch");

        builder.add("frostiful.splash.download_museswipr", "Download Museswipr on Steam!");

        builder.add(FrostifulModMenu.TITLE, "Frostiful Config");
        builder.add(FrostifulModMenu.CLIENT_TITLE, "Client Settings");
        builder.add(FrostifulModMenu.COMMON_TITLE, "Common Settings");

        builder.add(FrostifulModMenu.DISPLAY_CATEGORY, "Display Settings");
        builder.add(FrostifulModMenu.DISPLAY_DESC, "Settings that affect display.");

        builder.add(FrostifulModMenu.ENVIRONMENT_CATEGORY, "Environment Settings");
        builder.add(FrostifulModMenu.ENVIRONMENT_DESC, "Server-side settings for environment simulation.");

        builder.add(FrostifulModMenu.FREEZING_CATEGORY, "Freezing Settings");
        builder.add(FrostifulModMenu.FREEZING_DESC, "Server-side settings for freezing effects.");

        builder.add(FrostifulModMenu.TEMPERATURE_SOURCE_CATEGORY, "Temperature Sources");
        builder.add(FrostifulModMenu.TEMPERATURE_SOURCE_DESC, "Configure temperature sources.");
        builder.add(Translate.categoryKey(TemperatureSourceSettings.HANDLER, TemperatureSourceSettings.GENERAL_CATEGORY), "General");
        builder.add(Translate.categoryKey(TemperatureSourceSettings.HANDLER, TemperatureSourceSettings.TEMPERATURE_SOURCES_CATEGORY), "Temperature Sources");
        builder.add(Translate.groupKey(TemperatureSourceSettings.HANDLER, TemperatureSourceSettings.TEMPERATURE_SOURCES_CATEGORY, TemperatureSourceSettings.COOLING_GROUP), "Cooling Sources");
        builder.add(Translate.groupKey(TemperatureSourceSettings.HANDLER, TemperatureSourceSettings.TEMPERATURE_SOURCES_CATEGORY, TemperatureSourceSettings.HEATING_GROUP), "Heating Sources");

        builder.add(FrostifulModMenu.BLOCK_CATEGORY, "Block Settings");
        builder.add(FrostifulModMenu.BLOCK_DESC, "Server-side settings that relate to blocks.");
        builder.add(Translate.mainGroupKey(BlockSettings.HANDLER, BlockSettings.ICICLE_GROUP), "Icicles");
        builder.add(Translate.mainGroupKey(BlockSettings.HANDLER, BlockSettings.SUN_LICHEN_GROUP), "Sun Lichens");
        builder.add(Translate.mainGroupKey(BlockSettings.HANDLER, BlockSettings.CAMPFIRE_GROUP), "Campfires");

        builder.add(FrostifulModMenu.ENTITY_CATEGORY, "Entity Settings");
        builder.add(FrostifulModMenu.ENTITY_DESC, "Server-side settings that relate to entities and mobs.");
        builder.add(Translate.mainGroupKey(EntitySettings.HANDLER, EntitySettings.CHILLAGER_GROUP), "Chillagers & Frostologers");
        builder.add(Translate.mainGroupKey(EntitySettings.HANDLER, EntitySettings.MISC_GROUP), "Miscellaneous");
        generateConfigEnumTranslations(builder, DifficultySetting.class, "Automatic", "Peaceful", "Easy", "Normal", "Hard");

        builder.add(FrostifulModMenu.ITEM_CATEGORY, "Item Settings");
        builder.add(FrostifulModMenu.ITEM_DESC, "Server-side settings that relate to items.");
        builder.add(Translate.mainGroupKey(ItemSettings.HANDLER, ItemSettings.FROST_WAND_GROUP), "Frost Wands");
        builder.add(Translate.mainGroupKey(ItemSettings.HANDLER, ItemSettings.PACKED_SNOWBALL_GROUP), "Packed Snowballs");
        builder.add(Translate.mainGroupKey(ItemSettings.HANDLER, ItemSettings.ICICLE_GROUP), "Icicles");
        builder.add(Translate.mainGroupKey(ItemSettings.HANDLER, ItemSettings.MISC_GROUP), "Miscellaneous");

        builder.add(FrostifulModMenu.WEATHER_CATEGORY, "Weather Settings");
        builder.add(FrostifulModMenu.WEATHER_DESC, "Server-side settings for weather-related mechanics.");
        builder.add(Translate.mainGroupKey(WeatherSettings.HANDLER, WeatherSettings.ICICLE_GROUP), "Icicles");
        builder.add(Translate.mainGroupKey(WeatherSettings.HANDLER, WeatherSettings.WIND_GROUP), "Freezing Wind");
        generateConfigEnumTranslations(builder, WindSpawnMethod.class, "None", "Entity", "Point");

        generateConfigOptionTranslations(DisplaySettings.HANDLER, builder);
        generateConfigOptionTranslations(EnvironmentConfig.HANDLER, builder);
        generateConfigOptionTranslations(FreezingConfig.HANDLER, builder);
        generateConfigOptionTranslations(TemperatureSourceSettings.HANDLER, builder);
        generateConfigOptionTranslations(BlockSettings.HANDLER, builder);
        generateConfigOptionTranslations(EntitySettings.HANDLER, builder);
        generateConfigOptionTranslations(ItemSettings.HANDLER, builder);
        generateConfigOptionTranslations(WeatherSettings.HANDLER, builder);
    }

    private String itemSuffix(Item item, String suffix) {
        return item.getDescriptionId() + "." + suffix;
    }

    private String potionItem(Item item, Holder<Potion> potion) {
        return item.getDescriptionId() + ".effect." + potion.value().name();
    }

    private void smithingTemplate(TranslationBuilder builder, String baseName, String appliesTo, String ingredients, String baseSlotDescription, String additionsSlotDescription) {
        String prefix = "item.frostiful.smithing_template.%s.".formatted(baseName);
        builder.add(prefix + "applies_to", appliesTo);
        builder.add(prefix + "ingredients", ingredients);
        builder.add(prefix + "base_slot_description", baseSlotDescription);
        builder.add(prefix + "additions_slot_description", additionsSlotDescription);
    }

    private void trimPattern(TranslationBuilder builder, ResourceKey<TrimPattern> pattern, String value) {
        builder.add(Util.makeDescriptionId("trim_pattern", pattern.identifier()), value);
    }

    private void bannerColorVariants(
            TranslationBuilder builder,
            Holder<BannerPattern> pattern,
            String value
    ) {
        for (DyeColor color : DyeColor.values()) {
            String key = pattern.value().translationKey() + "." + color.getName();
            String colorName = COLOR_NAMES.get(color);
            builder.add(key, "%s %s".formatted(colorName, value));
        }
    }

    private String rootCommand(String suffix) {
        return "commands.frostiful.root.set.success." + suffix;
    }

    private String gameruleCategory(GameRuleCategory category) {
        return category.id().toLanguageKey("gamerule.category");
    }

    private void gameRule(
            TranslationBuilder builder,
            GameRule<?> rule,
            String name,
            String description
    ) {
        String key = rule.getDescriptionId();

        builder.add(key, name);
        builder.add(key + ".description", description);
    }

    private void addAdvancement(
            TranslationBuilder builder,
            ResourceKey<Advancement> key,
            String title,
            String desc
    ) {
        String translationKey = Util.makeDescriptionId("advancements", key.identifier());

        builder.add(translationKey + ".title", title);
        builder.add(translationKey + ".description", desc);
    }

    private void addDamageType(
            TranslationBuilder builder,
            Holder<DamageType> damageType,
            String deathMessage,
            String playerDeathMessage
    ) {
        String translationKey = "death.attack." + damageType.value().msgId();

        builder.add(translationKey, deathMessage);
        builder.add(translationKey + ".player", playerDeathMessage);
    }

    private void effect(TranslationBuilder builder, Holder<MobEffect> effect, String value) {
        builder.add(Util.makeDescriptionId("effect", effect.unwrapKey().orElseThrow().identifier()), value);
    }

    private void enchantment(
            TranslationBuilder builder,
            ResourceKey<Enchantment> enchantment,
            String title,
            String description
    ) {
        builder.addEnchantment(enchantment, title);
        builder.add(Util.makeDescriptionId("enchantment", enchantment.identifier()) + ".desc", description);
    }

    private String tip(String key) {
        return "frostiful.tip." + key;
    }

    private <T> void generateConfigOptionTranslations(
            ConfigClassHandler<T> handler,
            TranslationBuilder builder
    ) {
        final String prefix = Translate.prefixKey(handler);

        for (Field field : handler.configClass().getDeclaredFields()) {
            SerialEntry entry = field.getAnnotation(SerialEntry.class);
            if (entry == null) {
                continue;
            }

            Translate.Name nameData = field.getAnnotation(Translate.Name.class);
            String nameKey = configOption(prefix, field.getName());

            if (nameData != null) {
                builder.add(nameKey, nameData.value());
            } else {
                throw new IllegalStateException("Option name missing for" + nameKey);
            }

            String comment = entry.comment();
            String commentKey = commentKey(prefix, field.getName());

            if (comment != null && !comment.isEmpty()) {
                builder.add(commentKey, comment);
            } else if (field.getAnnotation(Translate.NoComment.class) == null) {
                throw new IllegalStateException("Missing comment or @NoComment marker for " + commentKey);
            }
        }
    }

    private <E extends Enum<E> & StringRepresentable> void generateConfigEnumTranslations(
            TranslationBuilder builder,
            Class<E> enumClass,
            String... names
    ) {
        E[] entries = enumClass.getEnumConstants();
        if (entries.length != names.length) {
            throw new IllegalStateException(
                    "Names array length %d is different from enums array length %d"
                            .formatted(names.length, entries.length)
            );
        }

        for (E entry : enumClass.getEnumConstants()) {
            String key = "yacl3.config.enum.%s.%s".formatted(enumClass.getSimpleName(), entry.getSerializedName());
            builder.add(key, names[entry.ordinal()]);
        }
    }

    private static String configOption(String prefix, String name) {
        return prefix + "." + name;
    }

    private static String commentKey(String prefix, String name) {
        return configOption(prefix, name) + ".desc";
    }
}