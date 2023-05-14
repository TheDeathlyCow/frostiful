package com.github.thedeathlycow.frostiful.world.spawner;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.entity.FEntityTypes;
import com.github.thedeathlycow.frostiful.entity.FreezingWindEntity;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.biome.FBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WindSpawner {

    public static final WindSpawner INSTANCE = new WindSpawner();

    private volatile int windSpawnCount = 0;

    private WindSpawner() {

    }

    public int getWindSpawnCount() {
        return windSpawnCount;
    }

    public synchronized void updateWindSpawnCount(int amount) {
        this.windSpawnCount += amount;
        if (this.windSpawnCount < 0) {
            this.windSpawnCount = 0;
        }
    }

    public synchronized void setWindSpawnCount(int value) {
        this.windSpawnCount = Math.max(0, value);
    }

    @Contract("_,null->false")
    public boolean isWindCountUnderCap(@NotNull FreezingConfigGroup config, @Nullable MinecraftServer server) {
        if (server == null) {
            return false;
        }

        int cap = server.getCurrentPlayerCount() * config.getWindSpawnCapPerPlayer();
        cap = Math.min(config.getWindSpawnCap(), cap);
        return this.windSpawnCount <= cap;
    }

    @Nullable
    public synchronized FreezingWindEntity trySpawnFreezingWind(World world, WorldChunk chunk) {
        return this.trySpawn(
                world,
                chunk,
                FEntityTypes.FREEZING_WIND,
                FBiomeTags.FREEZING_WIND_ALWAYS_SPAWNS,
                FBiomeTags.FREEZING_WIND_SPAWNS_IN_STORMS
        );
    }

    @Nullable
    public synchronized <W extends WindEntity> W trySpawn(
            World world,
            WorldChunk chunk,
            EntityType<W> type,
            TagKey<Biome> alwaysSpawnBiomes,
            TagKey<Biome> spawnInStormsBiomes
    ) {
        FrostifulConfig config = Frostiful.getConfig();
        if (!config.freezingConfig.doWindSpawning()) {
            return null;
        }

        if (!world.getDimension().natural()) {
            return null;
        }

        if (world.random.nextInt(world.isThundering() ? 100 : 400) != 0) {
            return null;
        }

        if (this.isWindCountUnderCap(config.freezingConfig, world.getServer())) {
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

        if (canSpawnOnGround || (spawnInAir && config.freezingConfig.spawnWindInAir())) {
            W wind = type.create(world);
            if (wind != null) {
                if (spawnInAir) {
                    wind.setLifeTicks(wind.getLifeTicks() * 3);
                }
                wind.setPosition(spawnPos.getX(), y, spawnPos.getZ());

                if (world.spawnEntity(wind)) {
                    wind.setSpawnedPassively(true);
                    this.windSpawnCount++;
                    return wind;
                }
                return null;
            }
        }

        return null;
    }

}
