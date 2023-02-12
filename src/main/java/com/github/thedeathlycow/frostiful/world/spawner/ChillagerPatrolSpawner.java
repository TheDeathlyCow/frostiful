package com.github.thedeathlycow.frostiful.world.spawner;

import com.github.thedeathlycow.frostiful.entity.FEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.PatrolSpawner;

public class ChillagerPatrolSpawner {


    /**
     * Essentially a rewrite of {@link PatrolSpawner#spawnPillager(ServerWorld, BlockPos, Random, boolean)}, except
     * that it spawns a {@link com.github.thedeathlycow.frostiful.entity.ChillagerEntity} instead of a
     * {@link net.minecraft.entity.mob.PillagerEntity}. Called via mixin as method is private.
     *
     * @param world world to spawn chillager in
     * @param pos position to spawn chillager at
     * @param random random instance
     * @param captain whether the chillager should be a captain
     * @return Returns true if the chillager was spawned
     */
    public static boolean spawnChillagerPatrol(ServerWorld world, BlockPos pos, Random random, boolean captain) {
        BlockState state = world.getBlockState(pos);

        if (!SpawnHelper.isClearForSpawn(world, pos, state, state.getFluidState(), FEntityTypes.CHILLAGER)) {
            return false;
        } else if (!PatrolEntity.canSpawn(FEntityTypes.CHILLAGER, world, SpawnReason.PATROL, pos, random)) {
            return false;
        } else {
            PatrolEntity patroller = FEntityTypes.CHILLAGER.create(world);
            if (patroller != null) {
                if (captain) {
                    patroller.setPatrolLeader(true);
                    patroller.setRandomPatrolTarget();
                }

                patroller.setPosition(pos.getX(), pos.getY(), pos.getZ());
                patroller.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, null, null);
                world.spawnEntityAndPassengers(patroller);
                return true;
            } else {
                return false;
            }
        }
    }
}
