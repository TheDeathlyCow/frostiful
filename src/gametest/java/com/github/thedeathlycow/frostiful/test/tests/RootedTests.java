package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.entity.component.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.registry.FCardinalComponents;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

@SuppressWarnings("unused")
public class RootedTests {

    @GameTest(structure = "frostiful-test:effects.platform")
    public void villagerStopsWalkingWhenRooted(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 1, 1);
        BlockPos end = start.offset(2, 0, 2);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);
        FrostWandRootComponent rootComponent = FCardinalComponents.FROST_WAND_ROOT_COMPONENT.get(entity);
        rootComponent.tryRootFromFrostWand(null);

        context.walkTo(entity, end, 1.0f);
        context.succeedWhenEntityPresent(EntityType.VILLAGER, start);
    }

    @GameTest(structure = "frostiful-test:effects.platform")
    public void villagerCanWalkWhenNotRooted(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 1, 1);
        BlockPos end = start.offset(2, 0, 2);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);

        context.walkTo(entity, end, 1.0f);
        context.succeedWhenEntityPresent(EntityType.VILLAGER, end);
    }

    @GameTest(structure = "frostiful-test:effects.platform")
    public void villagerRootIsNotReset(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 1, 1);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);
        FrostWandRootComponent rootComponent = FCardinalComponents.FROST_WAND_ROOT_COMPONENT.get(entity);

        // initial root
        rootComponent.tryRootFromFrostWand(null);

        int initialRootTicks = rootComponent.getRootedTicks();
        context.assertTrue(rootComponent.isRooted(), Component.literal("Villager is not rooted"));

        context.runAfterDelay(
                10L,
                () -> {
                    context.assertTrue(rootComponent.isRooted(), Component.literal("Villager is not rooted for re-apply"));
                    // root again, before root is expired
                    rootComponent.tryRootFromFrostWand(null);

                    int newRootTicks = rootComponent.getRootedTicks();
                    context.assertFalse(
                            newRootTicks >= initialRootTicks,
                            Component.literal("Villager root ticks were not reset")
                    );

                    context.succeed();
                }
        );
    }
}
