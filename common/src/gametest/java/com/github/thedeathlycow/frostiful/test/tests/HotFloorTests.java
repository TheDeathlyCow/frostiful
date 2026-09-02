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

package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureAware;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.Villager;

@SuppressWarnings("unused")
public class HotFloorTests {
    @GameTest(structure = "frostiful_test:magma_block_test", maxTicks = 40)
    public void villagerOnMagmaHeatedMoreThanVillagerOnStone(GameTestHelper context) {
        int temperatureChange = FrostifulConfigYACL.temperatureSourceSettings().hotFloorTemperatureChange();

        final BlockPos stonePos = new BlockPos(2, 2, 3);
        final BlockPos magmaPos = new BlockPos(4, 2, 3);
        final int initialTemperature = -1000;

        final Villager magmaVillager = context.spawnWithNoFreeWill(EntityType.VILLAGER, magmaPos);
        final Villager stoneVillager = context.spawnWithNoFreeWill(EntityType.VILLAGER, stonePos);

        stoneVillager.thermoo$setTemperature(initialTemperature);
        magmaVillager.thermoo$setTemperature(initialTemperature);
        context.assertEntityData(
                magmaPos, EntityType.VILLAGER,
                TemperatureAware::thermoo$getTemperature, initialTemperature
        );
        context.assertEntityData(
                stonePos, EntityType.VILLAGER,
                TemperatureAware::thermoo$getTemperature, initialTemperature
        );
        context.runAfterDelay(
                20, () -> {
                    int magmaTemperature = magmaVillager.thermoo$getTemperature();
                    int stoneTemperature = stoneVillager.thermoo$getTemperature();

                    context.assertTrue(
                            magmaTemperature > stoneTemperature,
                            Component.literal(String.format(
                                    "Magma Villager temperature of %d is not greater than Stone Villager temperature of %d",
                                    magmaTemperature,
                                    stoneTemperature
                            ))
                    );
                    context.succeed();
                }
        );
    }

}
