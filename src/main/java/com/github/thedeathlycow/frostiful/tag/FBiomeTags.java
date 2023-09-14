package com.github.thedeathlycow.frostiful.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class FBiomeTags {

    public static final TagKey<Biome> FREEZING_WIND_ALWAYS_SPAWNS = FBiomeTags.register("freezing_wind_always_spawns");
    public static final TagKey<Biome> FREEZING_WIND_SPAWNS_IN_STORMS = FBiomeTags.register("freezing_wind_spawns_in_storms");
    public static final TagKey<Biome> FREEZING_BIOMES = FBiomeTags.register("freezing_biomes");

    public static TagKey<Biome> register(String id) {
        return TagKey.of(RegistryKeys.BIOME, new Identifier(Frostiful.MODID, id));
    }

}
