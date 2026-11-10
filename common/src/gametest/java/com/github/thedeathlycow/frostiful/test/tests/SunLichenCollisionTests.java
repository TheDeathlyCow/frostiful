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

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.FMobEffects;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureAware;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

@SuppressWarnings("unused")
public final class SunLichenCollisionTests {
    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void coolLichenDoesNotDamage(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);

        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        context.assertEntityData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlock(pos, FBlocks.HOT_SUN_LICHEN.defaultBlockState());
        context.succeedWhenEntityData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());
    }

    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void hotLichenDamages(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);

        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);

        context.assertEntityData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlock(pos, FBlocks.HOT_SUN_LICHEN.defaultBlockState());
        context.succeedWhenEntityData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth() - 1.0f);
    }

    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void warmVillagerIsNotBurnedByColdSunLichen(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        entity.thermoo$setTemperature(0);

        context.setBlock(pos, FBlocks.COLD_SUN_LICHEN.defaultBlockState());

        context.runAfterDelay(1L, () -> {
            context.assertEntityProperty(entity, e -> !e.isOnFire(), Component.literal("Is Not On Fire"));
            context.succeed();
        });
    }

    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void warmVillagerIsNotBurnedByCoolSunLichen(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        entity.thermoo$setTemperature(0);

        context.setBlock(pos, FBlocks.COOL_SUN_LICHEN.defaultBlockState());

        context.runAfterDelay(1L, () -> {
            context.assertEntityProperty(entity, e -> !e.isOnFire(), Component.literal("Is Not On Fire"));
            context.succeed();
        });
    }

    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void warmVillagerIsNotBurnedByWarmSunLichen(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        entity.thermoo$setTemperature(0);

        context.setBlock(pos, FBlocks.WARM_SUN_LICHEN.defaultBlockState());

        context.runAfterDelay(1L, () -> {
            context.assertEntityProperty(entity, e -> !e.isOnFire(), Component.literal("Is Not On Fire"));
            context.succeed();
        });
    }

    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void warmVillagerIsBurnedByHotSunLichen(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        entity.thermoo$setTemperature(0);

        context.setBlock(pos, FBlocks.HOT_SUN_LICHEN.defaultBlockState());

        context.runAfterDelay(1L, () -> {
            context.assertEntityProperty(entity, e -> e.isOnFire(), Component.literal("Is On Fire"));
            context.succeed();
        });
    }

    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void coldLichenDoesNotApplyWarming(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);

        context.assertEntityProperty(entity, e -> !e.hasEffect(FMobEffects.WARMING), Component.literal("No Warming mob effect"));
        context.setBlock(pos, FBlocks.COLD_SUN_LICHEN.defaultBlockState());

        context.runAfterDelay(1L, () -> {
            context.assertEntityProperty(entity, e -> !e.hasEffect(FMobEffects.WARMING), Component.literal("No Warming mob effect"));
            context.succeed();
        });
    }

    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void coolLichenAppliesWarming(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);

        context.assertEntityProperty(entity, e -> !e.hasEffect(FMobEffects.WARMING), Component.literal("No Warming mob effect"));
        context.setBlock(pos, FBlocks.COOL_SUN_LICHEN.defaultBlockState());

        context.runAfterDelay(1L, () -> {
            context.assertEntityProperty(entity, e -> e.hasEffect(FMobEffects.WARMING), Component.literal("Warming mob effect"));
            context.assertEntityProperty(entity, e -> e.getEffect(FMobEffects.WARMING).getAmplifier(), 0, Component.literal("Warming mob effect amplifier"));
            context.succeed();
        });
    }

    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void warmLichenAppliesWarming(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);

        context.assertEntityProperty(entity, e -> !e.hasEffect(FMobEffects.WARMING), Component.literal("No Warming mob effect"));
        context.setBlock(pos, FBlocks.WARM_SUN_LICHEN.defaultBlockState());
        context.runAfterDelay(1L, () -> {
            context.assertEntityProperty(entity, e -> e.hasEffect(FMobEffects.WARMING), Component.literal("Warming mob effect"));
            context.assertEntityProperty(entity, e -> e.getEffect(FMobEffects.WARMING).getAmplifier(), 1, Component.literal("Warming mob effect amplifier"));
            context.succeed();
        });
    }

    @GameTest(structure = "frostiful_test:sun_lichen_tests.platform")
    public void hotLichenAppliesWarming(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);

        context.assertEntityProperty(entity, e -> !e.hasEffect(FMobEffects.WARMING), Component.literal("No Warming mob effect"));
        context.setBlock(pos, FBlocks.HOT_SUN_LICHEN.defaultBlockState());
        context.runAfterDelay(1L, () -> {
            context.assertEntityProperty(entity, e -> e.hasEffect(FMobEffects.WARMING), Component.literal("Warming mob effect"));
            context.assertEntityProperty(entity, e -> e.getEffect(FMobEffects.WARMING).getAmplifier(), 2, Component.literal("Warming mob effect amplifier"));
            context.succeed();
        });
    }
}
