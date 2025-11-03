package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class FBiomeTags {
    public static final TagKey<Biome> FREEZING_WIND_NEVER_SPAWNS = FBiomeTags.register("freezing_wind_never_spawns");
    public static final TagKey<Biome> FREEZING_WIND_ALWAYS_SPAWNS = FBiomeTags.register("freezing_wind_always_spawns");
    public static final TagKey<Biome> FREEZING_WIND_SPAWNS_IN_STORMS = FBiomeTags.register("freezing_wind_spawns_in_storms");
    public static final TagKey<Biome> DRY_BIOMES = FBiomeTags.register("dry_biomes");
    public static final TagKey<Biome> FREEZING_BLACKLIST_BIOMES = FBiomeTags.register("freezing_blacklist_biomes");

    static TagKey<Biome> register(String id) {
        return TagKey.create(Registries.BIOME, Frostiful.location(id));
    }

    private FBiomeTags() {
    }

}
