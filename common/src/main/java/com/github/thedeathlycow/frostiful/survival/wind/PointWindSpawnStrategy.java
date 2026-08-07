/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

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
                                0.9f + serverWorld.getRandom().nextFloat() / 3
                        );
                    }
            );
        }

        final int temperatureChange = FrostifulConfigYACL.temperatureSourceSettings().freezingWindTemperatureChange();
        world.getEntitiesOfClass(LivingEntity.class, box, WindEntity.CAN_BE_BLOWN)
                .forEach(entity -> {
                    WindEntity.pushEntity(entity, world, center, POWER_SCALE);
                    FreezingWindEntity.freezeEntity(entity, temperatureChange * POWER_SCALE, null);
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
