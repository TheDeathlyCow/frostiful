package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WindEntitySpawnStrategy implements WindSpawnStrategy {

    @Override
    public boolean spawn(World world, BlockPos spawnPos, boolean isInAir) {
        WindEntity wind = FEntityTypes.FREEZING_WIND.create(world);
        if (wind == null) {
            return false;
        }

        if (isInAir) {
            wind.setLifeTicks(wind.getLifeTicks() * 3);
        }
        wind.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());

        return world.spawnEntity(wind);
    }
}
