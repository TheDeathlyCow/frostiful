package com.github.thedeathlycow.frostiful.world.spawner;

import com.github.thedeathlycow.frostiful.entity.FEntityTypes;
import com.github.thedeathlycow.frostiful.entity.FreezingWindEntity;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.biome.FBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

public class WindSpawner {

    @Nullable
    public static FreezingWindEntity trySpawnFreezingWind(World world, WorldChunk chunk) {
        return trySpawn(world, chunk, FEntityTypes.FREEZING_WIND);
    }

    @Nullable
    public static <W extends WindEntity> W trySpawn(World world, WorldChunk chunk, EntityType<W> type) {
        if (!Frostiful.getConfig().freezingConfig.doWindSpawning()) {
            return null;
        }

        if (world.random.nextInt(world.isThundering() ? 100 : 400) != 0) {
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
        boolean canSpawnOnGround = (world.isRaining() && biome.isIn(FBiomeTags.FREEZING_WIND_SPAWNS_IN_STORMS))
                || biome.isIn(FBiomeTags.FREEZING_WIND_ALWAYS_SPAWNS);

        if (canSpawnOnGround || spawnInAir) {
            W wind = type.create(world);
            if (wind != null) {
                if (spawnInAir) {
                    wind.setLifeTicks(wind.getLifeTicks() * 3);
                }
                wind.setPosition(spawnPos.getX(), y, spawnPos.getZ());
                return world.spawnEntity(wind) ? wind : null;
            }
        }

        return null;
    }

}
