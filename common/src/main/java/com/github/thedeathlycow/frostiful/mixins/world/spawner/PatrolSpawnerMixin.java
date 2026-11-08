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

package com.github.thedeathlycow.frostiful.mixins.world.spawner;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.server.world.ChillagerPatrolSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin {


    @Inject(
            method = "spawnPatrolMember",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spawnChillagerInColdBiomes(ServerLevel world, BlockPos pos, RandomSource random, boolean captain, CallbackInfoReturnable<Boolean> cir) {
        if (!FrostifulConfigYACL.entitySettings().enableChillagerPatrols()) {
            return;
        }

        var biome = world.getBiome(pos).value();

        if (biome.coldEnoughToSnow(pos, world.getSeaLevel())) {
            cir.setReturnValue(ChillagerPatrolSpawner.spawnChillagerPatrol(world, pos, random, captain));
        }
    }

}
