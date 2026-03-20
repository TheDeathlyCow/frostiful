package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.item.*;
import com.github.thedeathlycow.frostiful.item.attribute.FrostResistanceComponent;
import com.github.thedeathlycow.frostiful.item.attribute.ResistanceComponentBuilder;
import com.github.thedeathlycow.frostiful.item.cloak.FrostologyCloakItemComponents;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.github.thedeathlycow.frostiful.item.component.SimpleTooltipComponent;
import com.github.thedeathlycow.frostiful.item.component.TemperatureStatusEquipment;
import com.github.thedeathlycow.frostiful.registry.tag.FBannerPatternTags;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.registry.tag.FTemperatureStatusTags;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public final class FItems {
    public static final Item FUR_HELMET = register(
            "fur_helmet",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR, ArmorType.HELMET)
                            .durability(ArmorType.HELMET.getDurability(5))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );
    public static final Item FUR_CHESTPLATE = register(
            "fur_chestplate",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR, ArmorType.CHESTPLATE)
                            .durability(ArmorType.CHESTPLATE.getDurability(5))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );
    public static final Item FUR_LEGGINGS = register(
            "fur_leggings",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR, ArmorType.LEGGINGS)
                            .durability(ArmorType.LEGGINGS.getDurability(5))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );
    public static final Item FUR_BOOTS = register(
            "fur_boots",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR, ArmorType.BOOTS)
                            .durability(ArmorType.BOOTS.getDurability(5))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );

    public static final Item FUR_PADDING = register("fur_padding");

    public static final Item FUR_UPGRADE_TEMPLATE = register(
            "fur_upgrade_template",
            settings -> FurSmithingUpgradeTemplate.createItem(settings.rarity(Rarity.UNCOMMON))
    );

    public static final Item ICE_SKATE_UPGRADE_TEMPLATE = register(
            "ice_skate_upgrade_template",
            settings -> IceSkateUpgradeTemplate.createItem(settings.rarity(Rarity.UNCOMMON))
    );

    public static final Item FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE = register(
            "frosty_armor_trim_smithing_template",
            settings -> SmithingTemplateItem.createArmorTrimTemplate(settings.rarity(Rarity.RARE))
    );

    public static final Item FUR_PADDED_CHAINMAIL_HELMET = register(
            "fur_padded_chainmail_helmet",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR_LINED_CHAINMAIL, ArmorType.HELMET)
                            .durability(ArmorType.HELMET.getDurability(15))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );
    public static final Item FUR_PADDED_CHAINMAIL_CHESTPLATE = register(
            "fur_padded_chainmail_chestplate",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR_LINED_CHAINMAIL, ArmorType.CHESTPLATE)
                            .durability(ArmorType.CHESTPLATE.getDurability(15))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );
    public static final Item FUR_PADDED_CHAINMAIL_LEGGINGS = register(
            "fur_padded_chainmail_leggings",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR_LINED_CHAINMAIL, ArmorType.LEGGINGS)
                            .durability(ArmorType.LEGGINGS.getDurability(15))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );
    public static final Item FUR_PADDED_CHAINMAIL_BOOTS = register(
            "fur_padded_chainmail_boots",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR_LINED_CHAINMAIL, ArmorType.BOOTS)
                            .durability(ArmorType.BOOTS.getDurability(15))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );

    public static final Item GLACIAL_HEART = register(
            "glacial_heart",
            settings -> new Item(
                    settings
                            .rarity(Rarity.UNCOMMON)
            )
    );

    public static final Item INERT_FROSTOLOGY_CLOAK = register(
            "inert_frostology_cloak",
            settings -> new Item(
                    settings
                            .component(FDataComponentTypes.SIMPLE_TOOLTIP, new SimpleTooltipComponent(SimpleTooltipComponent.INERT_TEXT))
                            .component(FDataComponentTypes.CAPE, CapeComponent.FROSTOLOGY_CLOAK)
                            .component(DataComponents.EQUIPPABLE, FrostologyCloakItemComponents.createEquippableComponent())
                            .rarity(Rarity.UNCOMMON)
                            .stacksTo(1)
            )
    );

    public static final Item FROSTOLOGY_CLOAK = register(
            "frostology_cloak",
            settings -> new Item(
                    settings
                            .attributes(FrostologyCloakItemComponents.createAttributeModifiers())
                            .component(FDataComponentTypes.SIMPLE_TOOLTIP, new SimpleTooltipComponent(SimpleTooltipComponent.ICE_LIKE_TEXT))
                            .component(FDataComponentTypes.CAPE, CapeComponent.FROSTOLOGY_CLOAK)
                            .component(DataComponents.EQUIPPABLE, FrostologyCloakItemComponents.createEquippableComponent())
                            .delayedComponent(FDataComponentTypes.BLOCKS_DAMAGE, context -> context.getOrThrow(DamageTypeTags.IS_FREEZING))
                            .delayedComponent(FDataComponentTypes.DISABLE_TEMPERATURE_STATUSES, context -> context.getOrThrow(FTemperatureStatusTags.NORMAL_PLAYER_STATUSES))
                            .delayedComponent(FDataComponentTypes.ENABLE_TEMPERATURE_STATUSES, context -> context.getOrThrow(FTemperatureStatusTags.FROSTOLOGY_CLOAK_PLAYER_STATUSES))
                            .rarity(Rarity.EPIC)
                            .stacksTo(1)
            )
    );

    public static final Item ICE_SKATES = register(
            "ice_skates",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR, ArmorType.BOOTS)
                            .durability(ArmorType.BOOTS.getDurability(5))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );

    public static final Item ARMORED_ICE_SKATES = register(
            "armored_ice_skates",
            settings -> new Item(
                    settings
                            .humanoidArmor(FArmorMaterials.FUR_LINED_CHAINMAIL, ArmorType.BOOTS)
                            .durability(ArmorType.BOOTS.getDurability(15))
                            .component(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE)
            )
    );

    public static final Item POLAR_BEAR_FUR_TUFT = register("polar_bear_fur_tuft");
    public static final Item WOLF_FUR_TUFT = register("wolf_fur_tuft");
    public static final Item OCELOT_FUR_TUFT = register("ocelot_fur_tuft");

    public static final Item ICICLE = register(
            "icicle",
            settings -> new IcicleItem(FBlocks.ICICLE, settings)
    );
    public static final Item COLD_SUN_LICHEN = register("cold_sun_lichen", FBlocks.COLD_SUN_LICHEN);
    public static final Item COOL_SUN_LICHEN = register("cool_sun_lichen", FBlocks.COOL_SUN_LICHEN);
    public static final Item WARM_SUN_LICHEN = register("warm_sun_lichen", FBlocks.WARM_SUN_LICHEN);
    public static final Item HOT_SUN_LICHEN = register("hot_sun_lichen", FBlocks.HOT_SUN_LICHEN);
    public static final Item FROST_WAND = register(
            "frost_wand",
            settings -> new FrostWandItem(
                    settings
                            .stacksTo(1)
                            .durability(250)
                            .attributes(FrostWandItem.createAttributeModifiers())
                            .component(DataComponents.TOOL, FrostWandItem.createToolComponent())
                            .rarity(Rarity.RARE)
                            .repairable(FItemTags.REPAIRS_FROST_WAND)
                            .enchantable(15)
            )
    );
    public static final Item GLACIAL_ARROW = register(
            "glacial_arrow",
            GlacialArrowItem::new
    );

    public static final Item FROSTOLOGER_SPAWN_EGG = register(
            "frostologer_spawn_egg",
            settings -> new SpawnEggItem(settings.spawnEgg(FEntityTypes.FROSTOLOGER))
    );
    public static final Item CHILLAGER_SPAWN_EGG = register(
            "chillager_spawn_egg",
            settings -> new SpawnEggItem(settings.spawnEgg(FEntityTypes.CHILLAGER))
    );

    public static final Item BITER_SPAWN_EGG = register(
            "biter_spawn_egg",
            settings -> new SpawnEggItem(settings.spawnEgg(FEntityTypes.BITER))
    );

    public static final Item FROZEN_TORCH = register(
            "frozen_torch",
            settings -> new StandingAndWallBlockItem(
                    FBlocks.FROZEN_TORCH,
                    FBlocks.FROZEN_WALL_TORCH,
                    Direction.DOWN,
                    settings.useBlockDescriptionPrefix()
            )
    );

    public static final Item PACKED_SNOW = register(
            "packed_snow", FBlocks.PACKED_SNOW
    );
    public static final Item PACKED_SNOWBALL = register(
            "packed_snowball",
            settings -> new PackedSnowBallItem(settings.stacksTo(16))
    );
    public static final Item PACKED_SNOW_BLOCK = register(
            "packed_snow_block", FBlocks.PACKED_SNOW_BLOCK
    );
    public static final Item PACKED_SNOW_BRICKS = register(
            "packed_snow_bricks", FBlocks.PACKED_SNOW_BRICKS
    );
    public static final Item PACKED_SNOW_BRICK_STAIRS = register(
            "packed_snow_brick_stairs", FBlocks.PACKED_SNOW_BRICK_STAIRS
    );
    public static final Item PACKED_SNOW_BRICK_SLAB = register(
            "packed_snow_brick_slab", FBlocks.PACKED_SNOW_BRICK_SLAB
    );
    public static final Item PACKED_SNOW_BRICK_WALL = register(
            "packed_snow_brick_wall", FBlocks.PACKED_SNOW_BRICK_WALL
    );

    public static final Item ICE_PANE = register("ice_pane", FBlocks.ICE_PANE);
    public static final Item CUT_PACKED_ICE = register("cut_packed_ice", FBlocks.CUT_PACKED_ICE);
    public static final Item CUT_PACKED_ICE_STAIRS = register("cut_packed_ice_stairs", FBlocks.CUT_PACKED_ICE_STAIRS);
    public static final Item CUT_PACKED_ICE_SLAB = register("cut_packed_ice_slab", FBlocks.CUT_PACKED_ICE_SLAB);
    public static final Item CUT_PACKED_ICE_WALL = register("cut_packed_ice_wall", FBlocks.CUT_PACKED_ICE_WALL);
    public static final Item CUT_BLUE_ICE = register("cut_blue_ice", FBlocks.CUT_BLUE_ICE);
    public static final Item CUT_BLUE_ICE_STAIRS = register("cut_blue_ice_stairs", FBlocks.CUT_BLUE_ICE_STAIRS);
    public static final Item CUT_BLUE_ICE_SLAB = register("cut_blue_ice_slab", FBlocks.CUT_BLUE_ICE_SLAB);
    public static final Item CUT_BLUE_ICE_WALL = register("cut_blue_ice_wall", FBlocks.CUT_BLUE_ICE_WALL);

    public static final Item SNOWFLAKE_BANNER_PATTERN = register(
            "snowflake_banner_pattern",
            settings -> new Item(
                    settings
                            .stacksTo(1)
                            .delayedComponent(
                                    DataComponents.PROVIDES_BANNER_PATTERNS,
                                    context -> context.getOrThrow(FBannerPatternTags.SNOWFLAKE_PATTERN_ITEM)
                            )
            )
    );

    public static final Item ICICLE_BANNER_PATTERN = register(
            "icicle_banner_pattern",
            settings -> new Item(
                    settings
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)
                            .delayedComponent(
                                    DataComponents.PROVIDES_BANNER_PATTERNS,
                                    context -> context.getOrThrow(FBannerPatternTags.ICICLE_PATTERN_ITEM)
                            )
            )
    );

    public static final Item FROSTOLOGY_BANNER_PATTERN = register(
            "frostology_banner_pattern",
            settings -> new Item(
                    settings
                            .stacksTo(1)
                            .rarity(Rarity.RARE)
                            .delayedComponent(
                                    DataComponents.PROVIDES_BANNER_PATTERNS,
                                    context -> context.getOrThrow(FBannerPatternTags.FROSTOLOGY_PATTERN_ITEM)
                            )
            )
    );

    public static final Item ICY_TRIAL_SPAWNER = register("icy_trial_spawner", FBlocks.ICY_TRIAL_SPAWNER);

    public static final Item ICY_VAULT = register("icy_vault", FBlocks.ICY_VAULT);

    public static final Item CASTLE_KEY = register("castle_key");

    public static final Item OMINOUS_CASTLE_KEY = register("ominous_castle_key");

    public static final Item BRITTLE_ICE = register("brittle_ice", FBlocks.BRITTLE_ICE);

    public static final Item FROZEN_ROD = register("frozen_rod");

    public static final Item GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE = register(
            "glacial_armor_trim_smithing_template",
            settings -> SmithingTemplateItem.createArmorTrimTemplate(settings.rarity(Rarity.EPIC))
    );

    public static final Item SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE = register(
            "snow_man_armor_trim_smithing_template",
            settings -> SmithingTemplateItem.createArmorTrimTemplate(settings.rarity(Rarity.UNCOMMON))
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful items");
        FSmithingTemplateItem.addTemplatesToLoot();
        ResistanceComponentBuilder.initialize();
        ServerEntityEvents.EQUIPMENT_CHANGE.register(new TemperatureStatusEquipment());
    }

    private static Item register(String id, Block block) {
        return register(id, settings -> new BlockItem(block, settings.useBlockDescriptionPrefix()));
    }

    private static Item register(String id) {
        return register(id, Item::new, new Item.Properties());
    }

    private static Item register(String id, Function<Item.Properties, Item> itemFactory) {
        return register(id, itemFactory, new Item.Properties());
    }

    private static Item register(String id, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Frostiful.id(id));
        Item item = itemFactory.apply(settings.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private FItems() {

    }
}
