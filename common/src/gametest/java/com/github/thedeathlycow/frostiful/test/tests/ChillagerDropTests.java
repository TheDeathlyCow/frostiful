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

import com.github.thedeathlycow.frostiful.entity.Chillager;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;

@SuppressWarnings("unused")
public class ChillagerDropTests {
    @GameTest()
    public void regularChillagerDoesNotDropOminousBottle(GameTestHelper context) {
        ServerLevel world = context.getLevel();
        DamageSources damageSources = world.damageSources();

        Chillager chillager = context.spawn(FEntityTypes.CHILLAGER, BlockPos.ZERO);

        chillager.hurtServer(world, damageSources.genericKill(), Float.MAX_VALUE);

        context.assertItemEntityNotPresent(Items.OMINOUS_BOTTLE, BlockPos.ZERO, 3f);

        context.succeed();
    }

    @GameTest()
    public void chillagerCaptainDropsOminousBottle(GameTestHelper context) {
        ServerLevel world = context.getLevel();
        DamageSources damageSources = world.damageSources();

        Chillager chillager = context.spawn(FEntityTypes.CHILLAGER, BlockPos.ZERO);
        chillager.setPatrolLeader(true);
        chillager.setItemSlot(
                EquipmentSlot.HEAD,
                Raid.getOminousBannerInstance(chillager.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN))
        );

        context.assertTrue(chillager.isCaptain(), Component.literal("Chillager is not a captain!"));

        chillager.hurtServer(world, damageSources.genericKill(), Float.MAX_VALUE);

        context.assertItemEntityPresent(Items.OMINOUS_BOTTLE, BlockPos.ZERO, 3f);

        context.succeed();
    }
}