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

import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.frostiful.survival.system.FrostRootSystem;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

@SuppressWarnings("unused")
public class RootedTests {

    @GameTest(structure = "frostiful_test:effects.platform")
    public void villagerStopsWalkingWhenRooted(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 1, 1);
        BlockPos end = start.offset(2, 0, 2);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);
        FrostRootSystem.tryRootFromFrostWand(entity, null);

        context.walkTo(entity, end, 1.0f);
        context.succeedWhenEntityPresent(EntityType.VILLAGER, start);
    }

    @GameTest(structure = "frostiful_test:effects.platform")
    public void villagerCanWalkWhenNotRooted(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 1, 1);
        BlockPos end = start.offset(2, 0, 2);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);

        context.walkTo(entity, end, 1.0f);
        context.succeedWhenEntityPresent(EntityType.VILLAGER, end);
    }

    @GameTest(structure = "frostiful_test:effects.platform")
    public void villagerRootIsNotReset(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 1, 1);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);

        // initial root
        FrostRootSystem.tryRootFromFrostWand(entity, null);

        int initialRootTicks = entity.getAttachedOrThrow(FDataAttachments.FROST_WAND_ROOT_TICKS);
        context.assertTrue(initialRootTicks > 0, Component.literal("Villager is not rooted"));

        context.runAfterDelay(
                10L,
                () -> {
                    context.assertTrue(entity.getAttachedOrThrow(FDataAttachments.FROST_WAND_ROOT_TICKS) > 0, Component.literal("Villager is not rooted for re-apply"));

                    // root again, before root is expired
                    FrostRootSystem.tryRootFromFrostWand(entity, null);
                    int newRootTicks = entity.getAttachedOrThrow(FDataAttachments.FROST_WAND_ROOT_TICKS);

                    context.assertFalse(
                            newRootTicks >= initialRootTicks,
                            Component.literal("Villager root ticks were reset")
                    );

                    context.succeed();
                }
        );
    }
}
