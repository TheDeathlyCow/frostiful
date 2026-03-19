package com.github.thedeathlycow.frostiful.survival.wind;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.entity.FreezingWindEntity;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.server.network.PointWindSpawnPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PointWindSpawnStrategy implements WindSpawnStrategy {

    private static final int SIZE = 3;
    private static final int POWER_SCALE = 6;


    @Override
    public boolean spawn(Level world, BlockPos spawnPos, boolean isInAir) {

        if (!(world instanceof ServerLevel serverWorld)) {
            return false;
        }
        AABB box = new AABB(spawnPos).inflate(SIZE);
        Vec3 center = box.getCenter();
        PointWindSpawnPacket.sendToNearbyPlayersFromServer(serverWorld, spawnPos, center);

        for (BlockPos pos : BlockPos.withinManhattan(spawnPos, SIZE / 2, SIZE / 2, SIZE / 2)) {

            WindManager.INSTANCE.extinguishBlock(
                    world.getBlockState(pos),
                    world,
                    pos,
                    () -> {
                        serverWorld.playSound(
                                null,
                                center.x, center.y, center.z,
                                FSoundEvents.ENTITY_FREEZING_WIND_BLOWOUT,
                                SoundSource.AMBIENT,
                                0.75f,
                                0.9f + serverWorld.random.nextFloat() / 3
                        );
                    }
            );
        }

        world.getEntitiesOfClass(LivingEntity.class, box, WindEntity.CAN_BE_BLOWN)
                .forEach(entity -> {
                    WindEntity.pushEntity(entity, world, center, POWER_SCALE);
                    FreezingWindEntity.freezeEntity(
                            entity,
                            FrostifulConfigYACL.freezingConfig().getFreezingWindFrost() * POWER_SCALE,
                            null
                    );
                });

        return true;
    }

    public static Vec3 randomParticlePos(Vec3 origin, RandomSource random) {
        return new Vec3(
                origin.x + SIZE * (2.0 * random.nextDouble() - 1.0),
                origin.y + SIZE * (2.0 * random.nextDouble() - 1.0),
                origin.z + SIZE * (2.0 * random.nextDouble() - 1.0)
        );
    }
}
