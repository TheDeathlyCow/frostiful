package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.entity.advancement.FrozenByFrostWandCriterion;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class FrozenByFrostWandCriterionTest {

    private static final int NUM_PREDICATES = 3;

    @GameTest()
    public void threeCreeperMobsToEmptyPredicatesIsTrue(TestContext context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = new FrozenByFrostWandCriterion.Conditions(
                Optional.empty(),
                List.of(),
                NumberRange.IntRange.ANY
        );

        context.assertTrue(conditions.matches(creepers), Text.literal("Conditions do not match!"));
        context.complete();
    }

    @GameTest()
    public void threeCreeperMobsToThreeCreeperPredicatesIsTrue(TestContext context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getWorld());

        context.assertTrue(conditions.matches(creepers), Text.literal("Conditions do not match!"));
        context.complete();
    }

    @GameTest()
    public void threeCreeperMobsAndTwoZombiesToThreeCreeperPredicatesIsTrue(TestContext context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.ZOMBIE,
                EntityType.ZOMBIE
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getWorld());

        context.assertTrue(conditions.matches(creepers), Text.literal("Conditions do not match!"));
        context.complete();
    }

    @GameTest()
    public void fiveCreeperMobsToThreeCreeperPredicatesIsTrue(TestContext context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getWorld());

        context.assertTrue(conditions.matches(creepers), Text.literal("Conditions do not match!"));
        context.complete();
    }

    @GameTest()
    public void twoCreeperMobsToThreeCreeperPredicatesIsFalse(TestContext context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getWorld());

        context.assertFalse(conditions.matches(creepers), Text.literal("Conditions do match, but they should NOT!"));
        context.complete();
    }

    @GameTest()
    public void twoCreepersAndOneZombieToThreeCreeperPredicatesIsFalse(TestContext context) {
        List<LootContext> creepers = createLootContexts(
                context,
                EntityType.CREEPER,
                EntityType.CREEPER,
                EntityType.ZOMBIE
        );

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getWorld());

        context.assertFalse(conditions.matches(creepers), Text.literal("Conditions do match, but they should NOT!"));
        context.complete();
    }

    @GameTest()
    public void zeroMobsToThreeCreeperPredicatesIsFalse(TestContext context) {
        List<LootContext> creepers = List.of();

        FrozenByFrostWandCriterion.Conditions conditions = createConditions(context.getWorld());

        context.assertFalse(conditions.matches(creepers), Text.literal("Conditions do match, but they should NOT!"));
        context.complete();
    }

    @SafeVarargs
    private static List<LootContext> createLootContexts(
            TestContext testContext,
            EntityType<? extends MobEntity>... entityTypes
    ) {
        List<LootContext> contexts = new ArrayList<>();

        for (EntityType<? extends MobEntity> type : entityTypes) {
            LootContext context = createAdvancementEntityLootContext(
                    testContext.getWorld(),
                    testContext.spawnMob(type, BlockPos.ORIGIN),
                    Vec3d.ZERO
            );
            contexts.add(context);
        }

        return contexts;
    }

    public static LootContext createAdvancementEntityLootContext(ServerWorld world, Entity target, Vec3d pos) {
        LootWorldContext lootWorldContext = new LootWorldContext.Builder(world)
                .add(LootContextParameters.THIS_ENTITY, target)
                .add(LootContextParameters.ORIGIN, pos)
                .build(LootContextTypes.ADVANCEMENT_ENTITY);
        return new LootContext.Builder(lootWorldContext).build(Optional.empty());
    }

    private static FrozenByFrostWandCriterion.Conditions createConditions(ServerWorld world) {
        List<LootContextPredicate> predicates = new ArrayList<>();
        RegistryEntryLookup<EntityType<?>> lookup = world.getRegistryManager().getOrThrow(RegistryKeys.ENTITY_TYPE);

        for (int i = 0; i < NUM_PREDICATES; i++) {
            LootContextPredicate context = EntityPredicate.contextPredicateFromEntityPredicate(
                    EntityPredicate.Builder.create()
                            .type(EntityTypePredicate.create(lookup, EntityType.CREEPER))
            );
            predicates.add(context);
        }

        return new FrozenByFrostWandCriterion.Conditions(
                Optional.empty(),
                predicates,
                NumberRange.IntRange.ANY
        );
    }
}