package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.entity.advancement.FrozenByFrostWandCriterion;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class FrozenByFrostWandCriterionTest {

    private static final int NUM_PREDICATES = 3;

    @GameTest()
    public void threeCreeperMobsToEmptyPredicatesIsTrue(GameTestHelper context) {
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

        context.assertTrue(conditions.matches(creepers), Component.literal("Conditions do not match!"));
        context.succeed();
    }

    @GameTest()
    public void threeCreeperMobsToThreeCreeperPredicatesIsTrue(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getLevel());

        context.assertTrue(conditions.matches(creepers), Component.literal("Conditions do not match!"));
        context.succeed();
    }

    @GameTest()
    public void threeCreeperMobsAndTwoZombiesToThreeCreeperPredicatesIsTrue(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.ZOMBIE,
                EntityType.ZOMBIE
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getLevel());

        context.assertTrue(conditions.matches(creepers), Component.literal("Conditions do not match!"));
        context.succeed();
    }

    @GameTest()
    public void fiveCreeperMobsToThreeCreeperPredicatesIsTrue(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getLevel());

        context.assertTrue(conditions.matches(creepers), Component.literal("Conditions do not match!"));
        context.succeed();
    }

    @GameTest()
    public void twoCreeperMobsToThreeCreeperPredicatesIsFalse(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getLevel());

        context.assertFalse(conditions.matches(creepers), Component.literal("Conditions do match, but they should NOT!"));
        context.succeed();
    }

    @GameTest()
    public void twoCreepersAndOneZombieToThreeCreeperPredicatesIsFalse(GameTestHelper context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.ZOMBIE
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getLevel());

        context.assertFalse(conditions.matches(creepers), Component.literal("Conditions do match, but they should NOT!"));
        context.succeed();
    }

    @GameTest()
    public void zeroMobsToThreeCreeperPredicatesIsFalse(GameTestHelper context) {
        List<LootContext> creepers = List.of();

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getLevel());

        context.assertFalse(conditions.matches(creepers), Component.literal("Conditions do match, but they should NOT!"));
        context.succeed();
    }

    @SafeVarargs
    private static List<LootContext> createLootContexts(
            GameTestHelper testContext,
            EntityType<? extends Mob>... entityTypes
    ) {
        List<LootContext> contexts = new ArrayList<>();

        for (EntityType<? extends Mob> type : entityTypes) {
            LootContext context = createAdvancementEntityLootContext(
                    testContext.getLevel(),
                    testContext.spawnWithNoFreeWill(type, BlockPos.ZERO),
                    Vec3.ZERO
            );
            contexts.add(context);
        }

        return contexts;
    }

    public static LootContext createAdvancementEntityLootContext(ServerLevel world, Entity target, Vec3 pos) {
        LootParams lootWorldContext = new LootParams.Builder(world)
                .withParameter(LootContextParams.THIS_ENTITY, target)
                .withParameter(LootContextParams.ORIGIN, pos)
                .create(LootContextParamSets.ADVANCEMENT_ENTITY);
        return new LootContext.Builder(lootWorldContext).create(Optional.empty());
    }

    private static FrozenByFrostWandCriterion.Conditions createConditions(ServerLevel world) {
        List<ContextAwarePredicate> predicates = new ArrayList<>();
        HolderGetter<EntityType<?>> lookup = world.registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);

        for (int i = 0; i < NUM_PREDICATES; i++) {
            ContextAwarePredicate context = EntityPredicate.wrap(
                    EntityPredicate.Builder.entity()
                            .entityType(EntityTypePredicate.of(lookup, EntityType.CREEPER))
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