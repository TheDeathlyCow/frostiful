package com.github.thedeathlycow.frostiful.test.loot;

import com.github.thedeathlycow.frostiful.entity.advancement.FrozenByFrostWandCriterion;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@GameTestHolder(FrostifulGameTest.MODID)
@PrefixGameTestTemplate(false)
public class FrozenByFrostWandCriterionTest {

    private static final int NUM_PREDICATES = 3;

    @GameTest(template = FrostifulGameTest.EMPTY_STRUCTURE)
    public void three_creeper_mobs_to_empty_predicates_is_true(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = new FrozenByFrostWandCriterion.Conditions(
                Optional.empty(),
                List.of(),
                MinMaxBounds.Ints.ANY
        );

        context.assertTrue(conditions.matches(creepers), "Conditions do not match!");
        context.succeed();
    }

    @GameTest(template = FrostifulGameTest.EMPTY_STRUCTURE)
    public void three_creeper_mobs_to_three_creeper_predicates_is_true(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions();

        context.assertTrue(conditions.matches(creepers), "Conditions do not match!");
        context.succeed();
    }

    @GameTest(template = FrostifulGameTest.EMPTY_STRUCTURE)
    public void five_creeper_mobs_to_three_creeper_predicates_is_true(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions();

        context.assertTrue(conditions.matches(creepers), "Conditions do not match!");
        context.succeed();
    }

    @GameTest(template = FrostifulGameTest.EMPTY_STRUCTURE)
    public void two_creeper_mobs_to_three_creeper_predicates_is_false(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions();

        context.assertFalse(conditions.matches(creepers), "Conditions do match, but they should NOT!");
        context.succeed();
    }

    @GameTest(template = FrostifulGameTest.EMPTY_STRUCTURE)
    public void two_creepers_and_one_zombie_to_three_creeper_predicates_is_false(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.ZOMBIE
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions();

        context.assertFalse(conditions.matches(creepers), "Conditions do match, but they should NOT!");
        context.succeed();
    }

    @GameTest(template = FrostifulGameTest.EMPTY_STRUCTURE)
    public void zero_mobs_to_three_creeper_predicates_is_false(GameTestHelper context) {
        List<LootContext> creepers = List.of();

        FrozenByFrostWandCriterion.Conditions conditions = createConditions();

        context.assertFalse(conditions.matches(creepers), "Conditions do match, but they should NOT!");
        context.succeed();
    }

    @SafeVarargs
    private static List<LootContext> createLootContexts(
            GameTestHelper testContext,
            EntityType<? extends Mob>... entityTypes
    ) {
        ServerPlayer mockPlayer = createMockPlayer(testContext);
        List<LootContext> contexts = new ArrayList<>();

        for (EntityType<? extends Mob> type : entityTypes) {
            LootContext context = EntityPredicate.createContext(
                    mockPlayer,
                    testContext.spawnWithNoFreeWill(type, BlockPos.ZERO)
            );
            contexts.add(context);
        }

        return contexts;
    }

    private static ServerPlayer createMockPlayer(GameTestHelper context) {
        ServerPlayer mockPlayer = Mockito.mock(ServerPlayer.class);

        Mockito.when(mockPlayer.serverLevel())
                .thenReturn(context.getLevel());
        Mockito.when(mockPlayer.position())
                .thenReturn(Vec3.ZERO);

        return mockPlayer;
    }

    private static FrozenByFrostWandCriterion.Conditions createConditions() {
        List<ContextAwarePredicate> predicates = new ArrayList<>();

        for (int i = 0; i < NUM_PREDICATES; i++) {
            ContextAwarePredicate context = EntityPredicate.wrap(
                    EntityPredicate.Builder.entity()
                            .of(EntityType.CREEPER)
            );
            predicates.add(context);
        }

        return new FrozenByFrostWandCriterion.Conditions(
                Optional.empty(),
                predicates,
                MinMaxBounds.Ints.ANY
        );
    }
}