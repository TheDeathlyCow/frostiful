package com.github.thedeathlycow.frostiful.test.sun_lichen;

import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public final class SunLichenPathfindingTests {

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.pathfinding.hot_sun_lichen")
    public void villager_avoids_hot_sun_lichen(TestContext context) {
        runAvoidanceTest(context, EntityType.VILLAGER, Blocks.EMERALD_BLOCK);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.pathfinding.warm_sun_lichen")
    public void villager_avoids_warm_sun_lichen(TestContext context) {
        runAvoidanceTest(context, EntityType.VILLAGER, Blocks.EMERALD_BLOCK);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.pathfinding.cool_sun_lichen")
    public void villager_avoids_cool_sun_lichen(TestContext context) {
        runAvoidanceTest(context, EntityType.VILLAGER, Blocks.EMERALD_BLOCK);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.pathfinding.cold_sun_lichen")
    public void villager_does_not_avoid_cold_sun_lichen(TestContext context) {
        runAvoidanceTest(context, EntityType.VILLAGER, Blocks.GOLD_BLOCK);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.pathfinding.hot_sun_lichen")
    public void villager_does_not_collide_with_hot_sun_lichen(TestContext context) {
        runCollisionTest(context, EntityType.VILLAGER);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.pathfinding.warm_sun_lichen")
    public void villager_does_not_collide_with_warm_sun_lichen(TestContext context) {
        runCollisionTest(context, EntityType.VILLAGER);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.pathfinding.cool_sun_lichen")
    public void villager_does_not_collide_with_cool_sun_lichen(TestContext context) {
        runCollisionTest(context, EntityType.VILLAGER);
    }

    private static void runAvoidanceTest(TestContext context, EntityType<? extends MobEntity> toSpawn, Block expectedBlock) {
        context.setTime(1000);
        final BlockPos start = new BlockPos(1, 2, 1);
        final BlockPos end = new BlockPos(10, 2, 7);

        final MobEntity entity = context.spawnMob(toSpawn, start);
        context.startMovingTowards(entity, end, 0.7f);

        // the template has a command block that the entity should (or shouldn't) step on that will change
        // the block below the start
        context.expectBlockAtEnd(expectedBlock, start.down());
    }

    private static void runCollisionTest(TestContext context, EntityType<? extends MobEntity> toSpawn) {
        context.setTime(1000);
        final BlockPos start = new BlockPos(1, 2, 1);
        final BlockPos end = new BlockPos(10, 2, 7);
        final int freezeAmount = 1000;

        final MobEntity entity = context.spawnMob(toSpawn, start);
        FrostHelper.addLivingFrost(entity, freezeAmount, false);
        context.expectEntityWithData(start, EntityType.VILLAGER, (e) -> {
            return ((FreezableEntity) e).frostiful$getCurrentFrost();
        }, freezeAmount);
        context.startMovingTowards(entity, end, 0.7f);

        context.expectEntityWithDataEnd(end, EntityType.VILLAGER, (e) -> {
            return ((FreezableEntity) e).frostiful$getCurrentFrost();
        }, freezeAmount);
    }
}
