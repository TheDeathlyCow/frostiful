package com.github.thedeathlycow.frostiful.survival.wind;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface WindSpawnStrategy {

    boolean spawn(World world, BlockPos spawnPos, boolean isInAir);

}
