package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.loot.LocationWarmthLootCondition;
import com.github.thedeathlycow.frostiful.entity.loot.RootedLootCondition;
import com.github.thedeathlycow.frostiful.entity.loot.IsChillagerLord;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FLootConditionTypes {
    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful loot condition types");

        register("rooted", RootedLootCondition.CODEC);
        register("wearing_ice_like_item", IsChillagerLord.CODEC);
        register("location_warmth", LocationWarmthLootCondition.CODEC);
    }

    private static void register(String name, MapCodec<? extends LootItemCondition> codec) {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, Frostiful.id(name), codec);
    }

    private FLootConditionTypes() {

    }
}
