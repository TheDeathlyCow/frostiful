package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.transformer.BlockTransformer;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.FreezingConfig;
import com.github.thedeathlycow.frostiful.config.section.WeatherSettings;
import com.github.thedeathlycow.frostiful.registry.FBlockTransformers;
import com.github.thedeathlycow.frostiful.registry.FEnvironmentAttributes;
import com.github.thedeathlycow.frostiful.registry.FrostifulRegistries;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureUnit;
import com.github.thedeathlycow.thermoo.api.environment.v2.EnvironmentLookup;
import com.github.thedeathlycow.thermoo.api.environment.v2.component.EnvironmentComponentTypes;
import com.github.thedeathlycow.thermoo.api.environment.v2.component.TemperatureRecordComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Optional;

public final class WindManager {

    public static final WindManager INSTANCE = new WindManager();

    private int windSpawnCount = 0;

    private WindManager() {

    }

    public void resetWindSpawnCount() {
        this.windSpawnCount = 0;
    }

    public void trySpawnFreezingWind(Level level, LevelChunk chunk) {
        WeatherSettings config = FrostifulConfigYACL.weatherSettings();

        if (this.windSpawnCount >= 15) { // spawn cap
            return;
        }

        int chanceBound = level.isThundering() ? 750 : 500;

        if (level.getRandom().nextInt(chanceBound) != 0) {
            return;
        }

        BlockPos.MutableBlockPos spawnPos = new BlockPos.MutableBlockPos();
        boolean spawnInAir = this.setSpawnPosition(level, chunk, spawnPos);
        WindBehavior windBehavior = level.environmentAttributes().getValue(FEnvironmentAttributes.WIND_BEHAVIOR, spawnPos);

        if (windBehavior == WindBehavior.NEVER || (spawnInAir && !config.enableWindInTheAir())) {
            return;
        }

        double temperatureC = EnvironmentLookup.getInstance().findEnvironmentComponents(level, spawnPos)
                .getOrDefault(EnvironmentComponentTypes.TEMPERATURE, TemperatureRecordComponent.DEFAULT)
                .valueInUnit(TemperatureUnit.CELSIUS);

        if (temperatureC > 0) {
            return;
        }

        boolean canSpawnOnGround = (level.isRaining() && windBehavior == WindBehavior.DURING_RAIN)
                || windBehavior == WindBehavior.ALWAYS;

        WindSpawnStrategy strategy = config.freezingWindSpawningMethod().getStrategy();

        if (strategy == null) {
            return;
        }

        if ((canSpawnOnGround || spawnInAir) && strategy.spawn(level, spawnPos, spawnInAir)) {
            this.windSpawnCount++;
        }
    }

    public void extinguishBlock(BlockState state, Level level, BlockPos pos, Runnable playSoundCallback) {
        if (!FrostifulConfigYACL.weatherSettings().freezingWindDestroysExposuedFire()) {
            return;
        }

        if (!(level instanceof ServerLevel serverLevel) || !serverLevel.getGameRules().get(GameRules.MOB_GRIEFING)) {
            return;
        }

        if (state.is(FBlockTags.FROZEN_TORCHES)) {
            return;
        }

        Optional<BlockTransformer> transformer = level.registryAccess()
                .lookupOrThrow(FrostifulRegistries.BLOCK_TRANSFORMER_KEY)
                .getOptional(FBlockTransformers.BLOW_OUT_FROM_WIND);

        if (transformer.isEmpty()) {
            Frostiful.LOGGER.warn("Blow out from wind block transformer missing!");
            return;
        }

        transformer.orElseThrow()
                .transformBlockState(serverLevel, pos, state)
                .ifPresent(blownOutResult -> {
                    level.setBlockAndUpdate(pos, blownOutResult);
                    playSoundCallback.run();
                });
    }


    /**
     * Sets the spawn position for the wind. Returns if the spawn was spawned in the air.
     *
     * @param world    World/level access object
     * @param chunk    The chunk of level this is happening in
     * @param blockPos The mutable blockpos to set the spawn position into
     * @return Returns true if the blockpos is an air blockpos
     */
    private boolean setSpawnPosition(Level world, LevelChunk chunk, BlockPos.MutableBlockPos blockPos) {
        ChunkPos chunkPos = chunk.getPos();
        BlockPos spawnPos = world.getHeightmapPos(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                world.getBlockRandomPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ(), 15)
        );
        blockPos.set(spawnPos);

        boolean spawnInAir = world.getRandom().nextBoolean();
        if (spawnInAir) {
            int topY = world.getMaxY();
            blockPos.setY(
                    spawnPos.getY() + (int) world.getRandom().triangle(
                            topY,
                            ((double) topY) - spawnPos.getY()
                    )
            );
            return true;
        }

        return false;
    }

}
