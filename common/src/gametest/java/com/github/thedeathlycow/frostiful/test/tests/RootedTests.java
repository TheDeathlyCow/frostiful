package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.frostiful.survival.system.FrostRootSystem;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

@SuppressWarnings("unused")
public class RootedTests {

    @GameTest(structure = "frostiful_test:effects.platform")
    public void villagerStopsWalkingWhenRooted(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 1, 1);
        BlockPos end = start.offset(2, 0, 2);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);
        FrostRootSystem.tryRootFromFrostWand(entity, null);

        context.walkTo(entity, end, 1.0f);
        context.succeedWhenEntityPresent(EntityType.VILLAGER, start);
    }

    @GameTest(structure = "frostiful_test:effects.platform")
    public void villagerCanWalkWhenNotRooted(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 1, 1);
        BlockPos end = start.offset(2, 0, 2);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);

        context.walkTo(entity, end, 1.0f);
        context.succeedWhenEntityPresent(EntityType.VILLAGER, end);
    }

    @GameTest(structure = "frostiful_test:effects.platform")
    public void villagerRootIsNotReset(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 1, 1);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);

        // initial root
        FrostRootSystem.tryRootFromFrostWand(entity, null);

        int initialRootTicks = entity.getAttachedOrThrow(FDataAttachments.FROST_WAND_ROOT_TICKS);
        context.assertTrue(initialRootTicks > 0, Component.literal("Villager is not rooted"));

        context.runAfterDelay(
                10L,
                () -> {
                    context.assertTrue(entity.getAttachedOrThrow(FDataAttachments.FROST_WAND_ROOT_TICKS) > 0, Component.literal("Villager is not rooted for re-apply"));

                    // root again, before root is expired
                    FrostRootSystem.tryRootFromFrostWand(entity, null);
                    int newRootTicks = entity.getAttachedOrThrow(FDataAttachments.FROST_WAND_ROOT_TICKS);

                    context.assertFalse(
                            newRootTicks >= initialRootTicks,
                            Component.literal("Villager root ticks were reset")
                    );

                    context.succeed();
                }
        );
    }
}
