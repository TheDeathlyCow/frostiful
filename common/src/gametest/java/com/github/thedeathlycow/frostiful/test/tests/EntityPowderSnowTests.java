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

import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@SuppressWarnings("unused")
public class EntityPowderSnowTests {
    private static final String NIGHT_ENVIRONMENT = "frostiful_test:night";

    @GameTest(structure = "frostiful_test:powder_snow_walkable_test", environment = NIGHT_ENVIRONMENT)
    public void zombieWearingFurBootsDoesNotFall(GameTestHelper context) {
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        Zombie zombie = context.spawn(EntityType.ZOMBIE, spawnPos);
        SlotAccess stackReference = zombie.getSlot(EquipmentSlot.FEET.getIndex(100));
        stackReference.set(new ItemStack(FItems.FUR_BOOTS));

        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityNotPresent(EntityType.ZOMBIE, new BlockPos(1, 1, 1));
                }
        );
    }

    @GameTest(structure = "frostiful_test:powder_snow_walkable_test", environment = NIGHT_ENVIRONMENT)
    public void zombieWearingChainmailFurBootsDoesNotFall(GameTestHelper context) {
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        Zombie zombie = context.spawn(EntityType.ZOMBIE, spawnPos);
        SlotAccess stackReference = zombie.getSlot(EquipmentSlot.FEET.getIndex(100));
        stackReference.set(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_BOOTS));
        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityNotPresent(EntityType.ZOMBIE, new BlockPos(1, 1, 1));
                }
        );
    }

    @GameTest(structure = "frostiful_test:powder_snow_walkable_test", environment = NIGHT_ENVIRONMENT)
    public void zombieWearingLeatherBootsDoesNotFall(GameTestHelper context) {
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        Zombie zombie = context.spawn(EntityType.ZOMBIE, spawnPos);
        SlotAccess stackReference = zombie.getSlot(EquipmentSlot.FEET.getIndex(100));
        stackReference.set(new ItemStack(Items.LEATHER_BOOTS));

        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityNotPresent(EntityType.ZOMBIE, new BlockPos(1, 1, 1));
                }
        );
    }

    @GameTest(structure = "frostiful_test:powder_snow_walkable_test", environment = NIGHT_ENVIRONMENT)
    public void zombieWearingNoBootsFalls(GameTestHelper context) {
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        Zombie zombie = context.spawn(EntityType.ZOMBIE, spawnPos);

        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityPresent(EntityType.ZOMBIE, new BlockPos(1, 1, 1));
                }
        );
    }

    @GameTest(structure = "frostiful_test:powder_snow_walkable_test")
    public void rabbitDoesNotFall(GameTestHelper context) {
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        Rabbit rabbit = context.spawn(EntityType.RABBIT, spawnPos);

        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityNotPresent(EntityType.RABBIT, new BlockPos(1, 1, 1));
                }
        );
    }
}
