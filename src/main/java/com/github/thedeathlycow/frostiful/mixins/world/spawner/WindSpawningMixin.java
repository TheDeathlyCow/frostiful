package com.github.thedeathlycow.frostiful.mixins.world.spawner;

import com.github.thedeathlycow.frostiful.entity.FEntityTypes;
import com.github.thedeathlycow.frostiful.entity.FreezingWindEntity;
import com.github.thedeathlycow.frostiful.tag.biome.FBiomeTags;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class WindSpawningMixin extends World {

    @Shadow public abstract boolean spawnEntity(Entity entity);

    protected WindSpawningMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            )
    )
    private void tickWindSpawn(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {

        if (this.random.nextInt(this.isThundering() ? 50 : 100) != 0) {
            return;
        }

        ChunkPos chunkPos = chunk.getPos();
        BlockPos spawnPos = this.getTopPosition(
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                this.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15)
        );

        boolean spawnInAir = this.random.nextBoolean();
        int y = spawnPos.getY();
        if (spawnInAir) {
            int diff = this.getTopY() - spawnPos.getY();
            y += (int)this.random.nextTriangular(diff, diff);
        }

        boolean canSpawnOnGround = this.isRaining()
                || this.getBiome(spawnPos).isIn(FBiomeTags.FREEZING_WIND_ALWAYS_SPAWNS);

        if (canSpawnOnGround || spawnInAir) {
            FreezingWindEntity wind = FEntityTypes.FREEZING_WIND.create(this);
            wind.setPosition(spawnPos.getX(), y, spawnPos.getZ());
            this.spawnEntity(wind);
        }

    }

}
