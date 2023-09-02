package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;

public class WindEntitySpawnStrategy implements WindSpawnStrategy {

    @Override
    public boolean spawn(
            World world,
            FreezingConfigGroup config,
            WorldChunk chunk,
            TagKey<Biome> alwaysSpawnBiomes,
            TagKey<Biome> spawnInStormsBiomes
    ) {
        if (!world.getDimension().natural()) {
            return false;
        }

        int chanceBound = world.isThundering()
                ? config.getWindSpawnRarityThunder()
                : config.getWindSpawnRarity();

        if (world.random.nextInt(chanceBound) != 0) {
            return false;
        }

        ChunkPos chunkPos = chunk.getPos();
        BlockPos spawnPos = world.getTopPosition(
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                world.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15)
        );

        boolean spawnInAir = world.random.nextBoolean();
        int y = spawnPos.getY();

        if (spawnInAir) {
            if (!config.spawnWindInAir()) {
                return false;
            }

            int topY = world.getTopY();
            y += (int) world.random.nextTriangular(topY, topY - spawnPos.getY());
        }

        RegistryEntry<Biome> biome = world.getBiome(spawnPos);
        boolean canSpawnOnGround = (world.isRaining() && biome.isIn(spawnInStormsBiomes))
                || biome.isIn(alwaysSpawnBiomes);

        if (canSpawnOnGround || spawnInAir) {
            return spawnWindEntity(world, spawnPos, y, spawnInAir);
        }

        return false;
    }

    private boolean spawnWindEntity(World world, BlockPos spawnPos, int y, boolean isInAir) {
        WindEntity wind = FEntityTypes.FREEZING_WIND.create(world);
        if (wind == null) {
            return false;
        }

        if (isInAir) {
            wind.setLifeTicks(wind.getLifeTicks() * 3);
        }
        wind.setPosition(spawnPos.getX(), y, spawnPos.getZ());

        return world.spawnEntity(wind);
    }
}
