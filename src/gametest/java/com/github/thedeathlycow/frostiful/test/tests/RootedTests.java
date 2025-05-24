package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.entity.component.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class RootedTests {

    @GameTest(structure = "frostiful-test:effects.platform")
    public void villagerStopsWalkingWhenRooted(TestContext context) {
        BlockPos start = new BlockPos(1, 1, 1);
        BlockPos end = start.add(2, 0, 2);

        MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);
        FrostWandRootComponent rootComponent = FComponents.FROST_WAND_ROOT_COMPONENT.get(entity);
        rootComponent.tryRootFromFrostWand(null);

        context.startMovingTowards(entity, end, 1.0f);
        context.expectEntityAtEnd(EntityType.VILLAGER, start);
    }

    @GameTest(structure = "frostiful-test:effects.platform")
    public void villagerCanWalkWhenNotRooted(TestContext context) {
        BlockPos start = new BlockPos(1, 1, 1);
        BlockPos end = start.add(2, 0, 2);

        MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);

        context.startMovingTowards(entity, end, 1.0f);
        context.expectEntityAtEnd(EntityType.VILLAGER, end);
    }

    @GameTest(structure = "frostiful-test:effects.platform")
    public void villagerRootIsNotReset(TestContext context) {
        BlockPos start = new BlockPos(1, 1, 1);

        MobEntity entity = context.spawnMob(EntityType.VILLAGER, start);
        FrostWandRootComponent rootComponent = FComponents.FROST_WAND_ROOT_COMPONENT.get(entity);

        // initial root
        rootComponent.tryRootFromFrostWand(null);

        int initialRootTicks = rootComponent.getRootedTicks();
        context.assertTrue(rootComponent.isRooted(), Text.literal("Villager is not rooted"));

        context.waitAndRun(
                10L,
                () -> {
                    context.assertTrue(rootComponent.isRooted(), Text.literal("Villager is not rooted for re-apply"));
                    // root again, before root is expired
                    rootComponent.tryRootFromFrostWand(null);

                    int newRootTicks = rootComponent.getRootedTicks();
                    context.assertFalse(
                            newRootTicks >= initialRootTicks,
                            Text.literal("Villager root ticks were not reset")
                    );

                    context.complete();
                }
        );
    }
}
