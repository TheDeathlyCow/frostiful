package com.github.thedeathlycow.frostiful.server.world;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PatrolSpawner;

public class ChillagerPatrolSpawner {


    /**
     * Essentially a rewrite of {@link PatrolSpawner#spawnPatrolMember(ServerLevel, BlockPos, RandomSource, boolean)}, except
     * that it spawns a {@link com.github.thedeathlycow.frostiful.entity.ChillagerEntity} instead of a
     * {@link net.minecraft.world.entity.monster.Pillager}. Called via mixin as method is private.
     *
     * @param world world to spawn chillager in
     * @param pos position to spawn chillager at
     * @param random random instance
     * @param captain whether the chillager should be a captain
     * @return Returns true if the chillager was spawned
     */
    public static boolean spawnChillagerPatrol(ServerLevel world, BlockPos pos, RandomSource random, boolean captain) {
        BlockState state = world.getBlockState(pos);

        if (!NaturalSpawner.isValidEmptySpawnBlock(world, pos, state, state.getFluidState(), FEntityTypes.CHILLAGER)) {
            return false;
        } else if (!PatrollingMonster.checkPatrollingMonsterSpawnRules(FEntityTypes.CHILLAGER, world, MobSpawnType.PATROL, pos, random)) {
            return false;
        } else {
            PatrollingMonster patroller = FEntityTypes.CHILLAGER.create(world);
            if (patroller != null) {
                if (captain) {
                    patroller.setPatrolLeader(true);
                    patroller.findPatrolTarget();
                }

                patroller.setPos(pos.getX(), pos.getY(), pos.getZ());
                patroller.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.PATROL, null);

                world.addFreshEntityWithPassengers(patroller);
                return true;
            } else {
                return false;
            }
        }
    }
}
