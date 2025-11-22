package com.github.thedeathlycow.frostiful.survival.wind;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface WindSpawnStrategy {

    boolean spawn(Level world, BlockPos spawnPos, boolean isInAir);

}
