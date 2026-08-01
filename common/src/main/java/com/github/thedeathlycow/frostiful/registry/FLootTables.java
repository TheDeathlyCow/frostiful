package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public final class FLootTables {
    public static final ResourceKey<LootTable> POLAR_BEAR_BRUSHING_GAMEPLAY = createKey("gameplay/polar_bear_brushing");
    public static final ResourceKey<LootTable> OCELOT_BRUSHING_GAMEPLAY = createKey("gameplay/ocelot_brushing");
    public static final ResourceKey<LootTable> WOLF_BRUSHING_GAMEPLAY = createKey("gameplay/wolf_brushing");

    public static final ResourceKey<LootTable> POLAR_BEAR_PLAYFIGHT_GAMEPLAY = createKey("gameplay/polar_bear_playfight");
    public static final ResourceKey<LootTable> OCELOT_PLAYFIGHT_GAMEPLAY = createKey("gameplay/ocelot_playfight");
    public static final ResourceKey<LootTable> WOLF_PLAYFIGHT_GAMEPLAY = createKey("gameplay/wolf_playfight");

    public static final ResourceKey<LootTable> CHILLAGER_OUTPOST_FLETCHER = createKey("chests/chillager_outpost/fletcher");
    public static final ResourceKey<LootTable> CHILLAGER_OUTPOST_MAP = createKey("chests/chillager_outpost/map");
    public static final ResourceKey<LootTable> CHILLAGER_OUTPOST_SMITH = createKey("chests/chillager_outpost/smith");
    public static final ResourceKey<LootTable> CHILLAGER_OUTPOST_TOWER = createKey("chests/chillager_outpost/tower");

    private static ResourceKey<LootTable> createKey(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Frostiful.id(name));
    }

    private FLootTables() {
    }
}
