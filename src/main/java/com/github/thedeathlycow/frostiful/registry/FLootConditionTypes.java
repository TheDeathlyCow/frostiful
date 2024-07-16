package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.loot.RootedLootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FLootConditionTypes {


    public static final LootConditionType ROOTED = new LootConditionType(RootedLootCondition.CODEC);

    public static void registerAll() {
        register("rooted", ROOTED);
    }

    private static void register(String name, LootConditionType lootCondition) {
        Registry.register(Registries.LOOT_CONDITION_TYPE, Frostiful.id(name), lootCondition);
    }

    private FLootConditionTypes() {

    }
}
