package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.FrozenTorchBlock;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.registry.tag.FBiomeTags;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

public final class WindManager {

    public static final WindManager INSTANCE = new WindManager();

    private int windSpawnCount = 0;

    private WindManager() {

    }

    public void resetWindSpawnCount() {
        this.windSpawnCount = 0;
    }

    public void trySpawnFreezingWind(Level world, LevelChunk chunk) {
        FreezingConfigGroup config = Frostiful.getConfig().freezingConfig;

        if (!world.dimensionType().natural() || this.windSpawnCount >= config.getWindSpawnCapPerSecond()) {
            return;
        }

        int chanceBound = world.isThundering()
                ? config.getWindSpawnRarityThunder()
                : config.getWindSpawnRarity();

        if (world.random.nextInt(chanceBound) != 0) {
            return;
        }

        BlockPos.MutableBlockPos spawnPos = new BlockPos.MutableBlockPos();
        boolean spawnInAir = this.setSpawnPosition(world, chunk, spawnPos);

        int temperatureChange = EnvironmentManager.INSTANCE.getController().getLocalTemperatureChange(world, spawnPos);
        if (temperatureChange >= 0) {
            return;
        }

        if (spawnInAir && !config.spawnWindInAir()) {
            return;
        }

        Holder<Biome> biome = world.getBiome(spawnPos);
        if (biome.is(FBiomeTags.FREEZING_WIND_NEVER_SPAWNS)) {
            return;
        }

        boolean canSpawnOnGround = (world.isRaining() && biome.is(FBiomeTags.FREEZING_WIND_SPAWNS_IN_STORMS))
                || biome.is(FBiomeTags.FREEZING_WIND_ALWAYS_SPAWNS);

        WindSpawnStrategy strategy = config.getWindSpawnStrategy().getStrategy();
        if (strategy == null) {
            return;
        }

        if ((canSpawnOnGround || spawnInAir) && strategy.spawn(world, spawnPos, spawnInAir)) {
            this.windSpawnCount++;
        }
    }

    public void extinguishBlock(BlockState state, Level world, BlockPos pos, Runnable playSoundCallback) {
        if (!Frostiful.getConfig().freezingConfig.isWindDestroysTorches()) {
            return;
        }

        if (!world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
        }

        if (state.is(FBlockTags.FROZEN_TORCHES)) {
            return;
        }

        @Nullable
        BlockState blownOutResult;

        if (state.is(FBlockTags.IS_OPEN_FLAME)) {
            blownOutResult = state.getFluidState().createLegacyBlock();
        } else if (
                state.is(FBlockTags.HAS_OPEN_FLAME)
                        && state.hasProperty(BlockStateProperties.LIT)
                        && state.getValue(BlockStateProperties.LIT)
        ) {
            blownOutResult = state.setValue(BlockStateProperties.LIT, false);
        } else {
            blownOutResult = FrozenTorchBlock.freezeTorch(state);
        }

        if (blownOutResult != null) {
            world.setBlockAndUpdate(pos, blownOutResult);
            playSoundCallback.run();
        }
    }


    /**
     * Sets the spawn position for the wind. Returns if the spawn was spawned in the air.
     *
     * @param world    World/level access object
     * @param chunk    The chunk of world this is happening in
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

        boolean spawnInAir = world.random.nextBoolean();
        if (spawnInAir) {
            int topY = world.getMaxBuildHeight();
            blockPos.setY(
                    spawnPos.getY() + (int) world.random.triangle(
                            topY,
                            ((double) topY) - spawnPos.getY()
                    )
            );
            return true;
        }

        return false;
    }

}
