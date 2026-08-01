package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.entity.FreezingWindEntity;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class WindEntitySpawnStrategy implements WindSpawnStrategy {

    @Override
    public boolean spawn(Level world, BlockPos spawnPos, boolean isInAir) {
        WindEntity wind = new FreezingWindEntity(FEntityTypes.FREEZING_WIND, world);

        if (isInAir) {
            wind.setLifeTicks(wind.getLifeTicks() * 3);
        }
        wind.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());

        return world.addFreshEntity(wind);
    }
}
