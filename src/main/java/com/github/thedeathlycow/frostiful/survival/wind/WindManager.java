package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.tag.FBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

public final class WindManager {

    public static final WindManager INSTANCE = new WindManager();

    private int windSpawnCount = 0;

    private WindManager() {

    }

    public void resetWindSpawnCount() {
        this.windSpawnCount = 0;
    }

    public void trySpawnFreezingWind(World world, WorldChunk chunk) {

        FreezingConfigGroup config = Frostiful.getConfig().freezingConfig;

        WindSpawnStrategy strategy = config.getWindSpawnStrategy().getStrategy();
        if (strategy == null) {
            return;
        }

        if (this.windSpawnCount >= config.getWindSpawnCapPerSecond() && strategy.spawn(
                world,
                config,
                chunk,
                FBiomeTags.FREEZING_WIND_ALWAYS_SPAWNS,
                FBiomeTags.FREEZING_WIND_SPAWNS_IN_STORMS
        )) {
            this.windSpawnCount++;
        }
    }

}
