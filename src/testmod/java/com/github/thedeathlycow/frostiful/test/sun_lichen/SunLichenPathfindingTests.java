package com.github.thedeathlycow.frostiful.test.sun_lichen;

import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("unused")
public final class SunLichenPathfindingTests {

    @GameTest(template = "frostiful-test:sun_lichen_tests.pathfinding.hot_sun_lichen")
    public void villager_avoids_hot_sun_lichen(GameTestHelper context) {
        runAvoidanceTest(context, EntityType.VILLAGER, Blocks.EMERALD_BLOCK);
    }

    @GameTest(template = "frostiful-test:sun_lichen_tests.pathfinding.warm_sun_lichen")
    public void villager_avoids_warm_sun_lichen(GameTestHelper context) {
        runAvoidanceTest(context, EntityType.VILLAGER, Blocks.EMERALD_BLOCK);
    }

    @GameTest(template = "frostiful-test:sun_lichen_tests.pathfinding.cool_sun_lichen")
    public void villager_avoids_cool_sun_lichen(GameTestHelper context) {
        runAvoidanceTest(context, EntityType.VILLAGER, Blocks.EMERALD_BLOCK);
    }

    @GameTest(template = "frostiful-test:sun_lichen_tests.pathfinding.cold_sun_lichen")
    public void villager_does_not_avoid_cold_sun_lichen(GameTestHelper context) {
        runAvoidanceTest(context, EntityType.VILLAGER, Blocks.GOLD_BLOCK);
    }

    private static void runAvoidanceTest(GameTestHelper context, EntityType<? extends Mob> toSpawn, Block expectedBlock) {
        context.setDayTime(1000);
        final BlockPos start = new BlockPos(1, 2, 1);
        final BlockPos end = new BlockPos(10, 2, 7);

        final Mob entity = context.spawnWithNoFreeWill(toSpawn, start);
        context.walkTo(entity, end, 0.7f);

        // the template has a command block that the entity should (or shouldn't) step on that will change
        // the block below the start
        context.succeedWhenBlockPresent(expectedBlock, start.below());
    }

    private static void runCollisionTest(GameTestHelper context, EntityType<? extends Mob> toSpawn) {
        context.setDayTime(1000);
        final BlockPos start = new BlockPos(1, 2, 1);
        final BlockPos end = new BlockPos(10, 2, 7);
        final int freezeAmount = -1000;

        final Mob entity = context.spawnWithNoFreeWill(toSpawn, start);
        entity.thermoo$setTemperature(freezeAmount);
        context.assertEntityData(start, EntityType.VILLAGER, TemperatureAware::thermoo$getTemperature, freezeAmount);
        context.walkTo(entity, end, 0.7f);

        context.succeedWhenEntityData(end, EntityType.VILLAGER, TemperatureAware::thermoo$getTemperature, freezeAmount);
    }
}
