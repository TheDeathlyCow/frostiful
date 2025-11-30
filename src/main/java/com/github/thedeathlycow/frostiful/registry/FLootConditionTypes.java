package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.loot.ChestEquippedWithTrinketLootCondition;
import com.github.thedeathlycow.frostiful.entity.loot.LocationWarmthLootCondition;
import com.github.thedeathlycow.frostiful.entity.loot.RootedLootCondition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class FLootConditionTypes {


    public static final LootItemConditionType ROOTED = register(
            "rooted",
            new LootItemConditionType(RootedLootCondition.CODEC)
    );
    public static final LootItemConditionType CHEST_EQUPPED_WITH_TRINKET = register(
            "chest_equipped_with_trinket",
            new LootItemConditionType(ChestEquippedWithTrinketLootCondition.CODEC)
    );
    public static final LootItemConditionType LOCATION_WARMTH = register(
            "location_warmth",
            new LootItemConditionType(LocationWarmthLootCondition.CODEC)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful loot condition types");
    }

    private static LootItemConditionType register(String name, LootItemConditionType lootCondition) {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, Frostiful.id(name), lootCondition);
    }

    private FLootConditionTypes() {

    }
}
