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
    public void stopsWalkingWhenRooted(TestContext context) {
        BlockPos start = new BlockPos(1, 2, 1);
        BlockPos end = start.add(2, 0, 2);

        MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);
        RootedEntity rootedEntity = (RootedEntity) entity;
        rootedEntity.frostiful$root(null);

        context.startMovingTowards(entity, end, 1.0f);
        context.expectEntityAtEnd(EntityType.VILLAGER, start);
    }

    @GameTest(templateName = "frostiful-test:effects.platform")
    public void canWalkWhenNotRooted(TestContext context) {
        BlockPos start = new BlockPos(1, 2, 1);
        BlockPos end = start.add(2, 0, 2);

        MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);

        context.startMovingTowards(entity, end, 1.0f);
        context.expectEntityAtEnd(EntityType.VILLAGER, end);
    }

    @GameTest(templateName = "frostiful-test:effects.platform")
    public void villager_root_is_not_reset(TestContext context) {
        BlockPos start = new BlockPos(1, 2, 1);

        MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);
        RootedEntity rootedEntity = (RootedEntity) entity;

        // initial root
        rootedEntity.frostiful$root(null);

        int initialRootTicks = rootedEntity.frostiful$getRootedTicks();
        context.assertTrue(rootedEntity.frostiful$isRooted(), "Villager is not rooted");

        context.waitAndRun(
                10L,
                () -> {
                    context.assertTrue(rootedEntity.frostiful$isRooted(), "Villager is not rooted for re-apply");
                    // root again, before root is expired
                    rootedEntity.frostiful$root(null);

                    int newRootTicks = rootedEntity.frostiful$getRootedTicks();
                    context.assertFalse(
                            newRootTicks >= initialRootTicks,
                            "Villager root ticks were not reset"
                    );

                    context.complete();
                }
        );
    }
}
