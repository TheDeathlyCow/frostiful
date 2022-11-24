package com.github.thedeathlycow.frostiful.test.sun_lichen;

import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public final class SunLichenPathfindingTests {

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.pathfinding.big")
    public void villager_does_not_collide_with_sun_lichen(TestContext context) {
        context.setTime(1000);
        final BlockPos start = new BlockPos(1, 2, 1);
        final BlockPos end = new BlockPos(10, 2, 7);
        final int freezeAmount = 1000;

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);
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
