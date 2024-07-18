package com.github.thedeathlycow.frostiful.test.effects;

import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class RootedTests {

    @GameTest(templateName = "frostiful-test:effects.platform")
    public void villager_stops_walking_when_rooted(TestContext context) {
        BlockPos start = new BlockPos(1, 2, 1);
        BlockPos end = start.add(2, 0, 2);

        MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);
        RootedEntity rootedEntity = (RootedEntity) entity;
        rootedEntity.frostiful$root(null);

        context.startMovingTowards(entity, end, 1.0f);
        context.expectEntityAtEnd(EntityType.VILLAGER, start);
    }

    @GameTest(templateName = "frostiful-test:effects.platform")
    public void villager_can_walk_when_not_rooted(TestContext context) {
        BlockPos start = new BlockPos(1, 2, 1);
        BlockPos end = start.add(2, 0, 2);

        MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);

        context.startMovingTowards(entity, end, 1.0f);
        context.expectEntityAtEnd(EntityType.VILLAGER, end);
    }
}
