package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.FreezingWindEntity;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.particle.FParticleTypes;
import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PointWindSpawnStrategy implements WindSpawnStrategy {

    private static final ParticleEffect[] WIND_PARTICLES = new ParticleEffect[]{
            new WindParticleEffect(true),
            new WindParticleEffect(false)
    };

    @Override
    public boolean spawn(World world, BlockPos spawnPos, boolean isInAir) {

        if (world instanceof ServerWorld serverWorld) {


            Box box = new Box(spawnPos);
            Vec3d center = box.getCenter();

            for (ParticleEffect particleEffect : WIND_PARTICLES) {
                serverWorld.spawnParticles(
                        particleEffect,
                        center.getX(), center.getY(), center.getZ(),
                        5,
                        1, 1, 1,
                        1
                );
            }

            serverWorld.spawnParticles(
                    ParticleTypes.SNOWFLAKE,
                    center.getX(), center.getY(), center.getZ(),
                    15,
                    1, 1, 1,
                    1
            );

            world.getEntitiesByClass(LivingEntity.class, box, WindEntity.CAN_BE_BLOWN)
                    .forEach(entity -> {
                        WindEntity.pushEntity(entity, world, center);
                        FreezingWindEntity.freezeEntity(entity, Frostiful.getConfig().freezingConfig.getFreezingWindFrost());
                    });

            return true;
        }

        return false;
    }
}
