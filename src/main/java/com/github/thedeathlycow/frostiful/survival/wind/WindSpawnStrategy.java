package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;

public interface WindSpawnStrategy {

    boolean spawn(
            World world,
            FreezingConfigGroup config,
            WorldChunk chunk,
            TagKey<Biome> alwaysSpawnBiomes,
            TagKey<Biome> spawnInStormsBiomes
    );

}
