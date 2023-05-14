package com.github.thedeathlycow.frostiful.world.spawner;

import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.entity.FEntityTypes;
import com.github.thedeathlycow.frostiful.entity.FreezingWindEntity;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.biome.FBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class WindSpawner {

    public static final WindSpawner INSTANCE = new WindSpawner();

    private int windSpawnCount = 0;

    private WindSpawner() {
        
    }

    public void resetWindSpawnCount() {
        this.windSpawnCount = 0;
    }

    @Nullable
    public FreezingWindEntity trySpawnFreezingWind(World world, WorldChunk chunk) {
        return this.trySpawn(
                world,
                chunk,
                FEntityTypes.FREEZING_WIND,
                FBiomeTags.FREEZING_WIND_ALWAYS_SPAWNS,
                FBiomeTags.FREEZING_WIND_SPAWNS_IN_STORMS
        );
    }

    @Nullable
    public <W extends WindEntity> W trySpawn(
            World world,
            WorldChunk chunk,
            EntityType<W> type,
            TagKey<Biome> alwaysSpawnBiomes,
            TagKey<Biome> spawnInStormsBiomes
    ) {
        FreezingConfigGroup config = Frostiful.getConfig().freezingConfig;
        if (!config.doWindSpawning()) {
            return null;
        }

        if (!world.getDimension().natural()) {
            return null;
        }

        int chanceBound = world.isThundering()
                ? config.getWindSpawnRarityThunder()
                : config.getWindSpawnRarity();

        if (world.random.nextInt(chanceBound) != 0) {
            return null;
        }

        if (this.windSpawnCount >= config.getWindSpawnCapPerSecond()) {
            return null;
        }

        ChunkPos chunkPos = chunk.getPos();
        BlockPos spawnPos = world.getTopPosition(
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                world.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15)
        );

        boolean spawnInAir = world.random.nextBoolean();
        int y = spawnPos.getY();
        if (spawnInAir) {
            int topY = world.getTopY();
            y += (int) world.random.nextTriangular(topY, topY - spawnPos.getY());
        }

        var biome = world.getBiome(spawnPos);
        boolean canSpawnOnGround = (world.isRaining() && biome.isIn(spawnInStormsBiomes))
                || biome.isIn(alwaysSpawnBiomes);

        if (canSpawnOnGround || (spawnInAir && config.spawnWindInAir())) {
            W wind = type.create(world);
            if (wind != null) {
                if (spawnInAir) {
                    wind.setLifeTicks(wind.getLifeTicks() * 3);
                }
                wind.setPosition(spawnPos.getX(), y, spawnPos.getZ());

                if (world.spawnEntity(wind)) {
                    this.windSpawnCount++;
                    return wind;
                }
                return null;
            }
        }

        return null;
    }

}
