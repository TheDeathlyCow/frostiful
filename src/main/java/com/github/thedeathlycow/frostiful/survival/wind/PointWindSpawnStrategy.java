package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.FreezingWindEntity;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PointWindSpawnStrategy implements WindSpawnStrategy {

    private static final int POWER_SCALE = 6;
    private static final int SIZE = 3;

    private static final ParticleEffect[] WIND_PARTICLES = new ParticleEffect[]{
            new WindParticleEffect(true),
            new WindParticleEffect(false)
    };

    @Override
    public boolean spawn(World world, BlockPos spawnPos, boolean isInAir) {

        if (!(world instanceof ServerWorld serverWorld)) {
            return false;
        }
        Box box = new Box(spawnPos).expand(SIZE);
        Vec3d center = box.getCenter();
        double x = center.x;
        double y = center.y;
        double z = center.z;

        for (ParticleEffect particleEffect : WIND_PARTICLES) {
            serverWorld.spawnParticles(
                    particleEffect,
                    x, y, z,
                    50,
                    1, 1, 1,
                    0.02
            );
        }

        serverWorld.spawnParticles(
                ParticleTypes.SNOWFLAKE,
                x, y, z,
                75,
                1, 1, 1,
                0.02
        );

        serverWorld.playSound(
                null,
                x, y, z,
                FSoundEvents.ENTITY_WIND_BLOW,
                SoundCategory.AMBIENT,
                0.75f,
                0.9f + serverWorld.random.nextFloat() / 3
        );


        for (BlockPos pos : BlockPos.iterateOutwards(BlockPos.ofFloored(center), SIZE / 2, SIZE / 2, SIZE / 2)) {

            WindManager.INSTANCE.extinguishBlock(
                    world.getBlockState(pos),
                    world,
                    pos,
                    () -> {
                        serverWorld.playSound(
                                null,
                                x, y, z,
                                FSoundEvents.ENTITY_FREEZING_WIND_BLOWOUT,
                                SoundCategory.AMBIENT,
                                0.75f,
                                0.9f + serverWorld.random.nextFloat() / 3
                        );
                    }
            );
        }

        world.getEntitiesByClass(LivingEntity.class, box, WindEntity.CAN_BE_BLOWN)
                .forEach(entity -> {
                    WindEntity.pushEntity(entity, world, center, POWER_SCALE);
                    FreezingWindEntity.freezeEntity(
                            entity,
                            Frostiful.getConfig().freezingConfig.getFreezingWindFrost() * POWER_SCALE
                    );
                });

        return true;
    }
}
