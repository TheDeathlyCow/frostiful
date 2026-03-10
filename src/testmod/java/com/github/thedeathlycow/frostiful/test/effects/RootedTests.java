package com.github.thedeathlycow.frostiful.test.effects;

import com.github.thedeathlycow.frostiful.entity.attachment.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@SuppressWarnings("unused")
@GameTestHolder(FrostifulGameTest.MODID)
@PrefixGameTestTemplate(false)
public class RootedTests {

    @GameTest(template = "effects.platform")
    public void villager_stops_walking_when_rooted(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 2, 1);
        BlockPos end = start.offset(2, 0, 2);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);
        FrostWandRootComponent rootComponent = FrostWandRootComponent.get(entity);
        rootComponent.tryRootFromFrostWand(null);

        context.walkTo(entity, end, 1.0f);
        context.succeedWhenEntityPresent(EntityType.VILLAGER, start);
    }

    @GameTest(template = "effects.platform")
    public void villager_can_walk_when_not_rooted(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 2, 1);
        BlockPos end = start.offset(2, 0, 2);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);

        context.walkTo(entity, end, 1.0f);
        context.succeedWhenEntityPresent(EntityType.VILLAGER, end);
    }

    @GameTest(template = "effects.platform")
    public void villager_root_is_not_reset(GameTestHelper context) {
        BlockPos start = new BlockPos(1, 2, 1);

        Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, start);
        FrostWandRootComponent rootComponent = FrostWandRootComponent.get(entity);

        // initial root
        rootComponent.tryRootFromFrostWand(null);

        int initialRootTicks = rootComponent.getRootedTicks();
        context.assertTrue(rootComponent.isRooted(), "Villager is not rooted");

        context.runAfterDelay(
                10L,
                () -> {
                    context.assertTrue(rootComponent.isRooted(), "Villager is not rooted for re-apply");
                    // root again, before root is expired
                    rootComponent.tryRootFromFrostWand(null);

                    int newRootTicks = rootComponent.getRootedTicks();
                    context.assertFalse(
                            newRootTicks >= initialRootTicks,
                            "Villager root ticks were not reset"
                    );

                    context.succeed();
                }
        );
    }
}
