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

package com.github.thedeathlycow.frostiful.survival.system;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.SoakingSettings;
import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

public final class SnowAccumulationSystem {
    public static void serverTick(LivingEntity provider) {
        if (isBeingSnowedOn(provider)) {
            addSnowAccumulation(provider, FrostifulConfigYACL.soakingSettings());
        } else {
            tryMeltSnowAccumulation(provider);
        }
    }

    public static void tryMeltSnowAccumulation(LivingEntity provider) {
        if (!provider.hasAttached(FDataAttachments.SNOW_ACCUMULATION_TICKS)) {
            return;
        }

        int snowAccumulation = provider.getAttachedOrThrow(FDataAttachments.SNOW_ACCUMULATION_TICKS) - 1;
        provider.thermoo$addWetTicks(2);

        if (snowAccumulation > 0) {
            provider.setAttached(FDataAttachments.SNOW_ACCUMULATION_TICKS, snowAccumulation);
        } else {
            provider.removeAttached(FDataAttachments.SNOW_ACCUMULATION_TICKS);
        }
    }

    private static boolean isBeingSnowedOn(LivingEntity provider) {
        Level world = provider.level();
        BlockPos pos = provider.blockPosition();
        return hasSnow(world, pos)
                || hasSnow(world, BlockPos.containing(pos.getX(), provider.getBoundingBox().maxY, pos.getZ()));
    }

    private static void addSnowAccumulation(LivingEntity provider, SoakingSettings settings) {
        int snowAccumulation = provider.getAttachedOrCreate(FDataAttachments.SNOW_ACCUMULATION_TICKS);

        if (snowAccumulation < settings.maxSnowAccumulationTicks()) {
            provider.setAttached(FDataAttachments.SNOW_ACCUMULATION_TICKS, snowAccumulation + 1);
        }
    }

    private static boolean hasSnow(Level world, BlockPos pos) {
        if (!world.isRaining()) {
            return false;
        } else if (!world.canSeeSky(pos)) {
            return false;
        } else if (world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY()) {
            return false;
        } else {
            Biome biome = world.getBiome(pos).value();
            return biome.getPrecipitationAt(pos, world.getSeaLevel()) == Biome.Precipitation.SNOW;
        }
    }

    private SnowAccumulationSystem() {

    }
}