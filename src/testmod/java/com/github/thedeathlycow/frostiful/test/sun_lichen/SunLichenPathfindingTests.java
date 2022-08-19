package com.github.thedeathlycow.frostiful.test.sun_lichen;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

public class SunLichenPathfindingTests {

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.pathfinding.big", required = false)
    public void villagerDoesNotCollideWithSunLichen(TestContext context) {
        final BlockPos start = new BlockPos(1, 2, 1);
        final BlockPos end = new BlockPos(10, 2, 7);
        final int freezeAmount = 1000;

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);
        entity.setFrozenTicks(freezeAmount);
        context.expectEntityWithData(start, EntityType.VILLAGER, Entity::getFrozenTicks, freezeAmount);
        context.startMovingTowards(entity, end, 1.0f);
        context.expectEntityWithDataEnd(end, EntityType.VILLAGER, Entity::getFrozenTicks, freezeAmount);
    }

}
