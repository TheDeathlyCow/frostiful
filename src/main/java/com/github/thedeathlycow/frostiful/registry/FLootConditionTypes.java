package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.loot.WearingIceLikeItem;
import com.github.thedeathlycow.frostiful.entity.loot.LocationWarmthLootCondition;
import com.github.thedeathlycow.frostiful.entity.loot.RootedLootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FLootConditionTypes {


    public static final LootConditionType ROOTED = register(
            "rooted",
            new LootConditionType(RootedLootCondition.CODEC)
    );
    public static final LootConditionType CHEST_EQUPPED_WITH_TRINKET = register(
            "wearing_ice_like_item",
            new LootConditionType(WearingIceLikeItem.CODEC)
    );
    public static final LootConditionType LOCATION_WARMTH = register(
            "location_warmth",
            new LootConditionType(LocationWarmthLootCondition.CODEC)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful loot condition types");
    }

    private static LootConditionType register(String name, LootConditionType lootCondition) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, Frostiful.id(name), lootCondition);
    }

    private FLootConditionTypes() {

    }
}
