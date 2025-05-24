package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.function.Function;

@SuppressWarnings("unused")
public final class SunLichenCollisionTests {
    @GameTest(structure = "frostiful-test:sun_lichen_tests.platform")
    public void coolLichenDoesNotDamage(TestContext context) {
        final BlockPos pos = new BlockPos(1, 1, 1);

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        context.expectEntityWithData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlockState(pos, FBlocks.HOT_SUN_LICHEN.getDefaultState());
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());
    }

    @GameTest(structure = "frostiful-test:sun_lichen_tests.platform")
    public void hotLichenDamages(TestContext context) {
        final BlockPos pos = new BlockPos(1, 1, 1);

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);

        context.expectEntityWithData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlockState(pos, FBlocks.HOT_SUN_LICHEN.getDefaultState());
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth() - 1.0f);
    }

    @GameTest(structure = "frostiful-test:sun_lichen_tests.platform")
    public void coldLichenDoesNotWarm(TestContext context) {
        final BlockPos pos = new BlockPos(1, 1, 1);

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        final Function<VillagerEntity, Integer> frostGetter = TemperatureAware::thermoo$getTemperature;

        entity.thermoo$setTemperature(0);
        context.expectEntityWithData(pos, EntityType.VILLAGER, frostGetter, 0);

        context.setBlockState(pos, FBlocks.COLD_SUN_LICHEN.getDefaultState());

        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, frostGetter, 0);
    }

    @GameTest(structure = "frostiful-test:sun_lichen_tests.platform")
    public void sunLichenDoesNotOverheat(TestContext context) {
        final BlockPos pos = new BlockPos(1, 1, 1);

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        final Function<VillagerEntity, Integer> frostGetter = TemperatureAware::thermoo$getTemperature;

        int freezeAmount = -500;
        entity.thermoo$setTemperature(freezeAmount);
        context.expectEntityWithData(pos, EntityType.VILLAGER, frostGetter, freezeAmount);

        context.setBlockState(pos, FBlocks.HOT_SUN_LICHEN.getDefaultState());

        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, frostGetter, 0);
    }

    @GameTest(structure = "frostiful-test:sun_lichen_tests.platform")
    public void warmVillagerIsBurnedByHotSunLichen(TestContext context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        int temperature = 500;
        MobEntity entity = setupWarmVillagerTest(context, pos, temperature);

        context.setBlockState(pos, FBlocks.HOT_SUN_LICHEN.getDefaultState());

        context.waitAndRun(1L, () -> {
            context.expectEntityWithData(pos, EntityType.VILLAGER, TemperatureAware::thermoo$getTemperature, temperature);
            context.expectEntityWithData(pos, EntityType.VILLAGER, Entity::isOnFire, true);

            context.complete();
        });
    }

    @GameTest(structure = "frostiful-test:sun_lichen_tests.platform")
    public void warmVillagerIsNotBurnedByCoolSunLichen(TestContext context) {
        final BlockPos pos = new BlockPos(1, 1, 1);
        int temperature = 500;
        MobEntity entity = setupWarmVillagerTest(context, pos, temperature);

        context.setBlockState(pos, FBlocks.COOL_SUN_LICHEN.getDefaultState());

        context.waitAndRun(1L, () -> {
            context.expectEntityWithData(pos, EntityType.VILLAGER, TemperatureAware::thermoo$getTemperature, temperature);
            context.expectEntityWithData(pos, EntityType.VILLAGER, Entity::isOnFire, false);

            context.complete();
        });
    }

    @GameTest(structure = "frostiful-test:sun_lichen_tests.platform")
    public void hotLichenWarmsVillager(TestContext context) {
        expectWarmLichenWarmsVillager(context, FBlocks.HOT_SUN_LICHEN);
    }

    @GameTest(structure = "frostiful-test:sun_lichen_tests.platform")
    public void warmLichenWarmsVillager(TestContext context) {
        expectWarmLichenWarmsVillager(context, FBlocks.WARM_SUN_LICHEN);
    }

    @GameTest(structure = "frostiful-test:sun_lichen_tests.platform")
    public void coolLichenWarmsVillager(TestContext context) {
        expectWarmLichenWarmsVillager(context, FBlocks.COOL_SUN_LICHEN);
    }

    private static void expectWarmLichenWarmsVillager(TestContext context, Block warmLichen) {
        final BlockPos pos = new BlockPos(1, 1, 1);

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        final Function<VillagerEntity, Integer> frostGetter = TemperatureAware::thermoo$getTemperature;

        int initialTemperature = -2000;
        entity.thermoo$setTemperature(initialTemperature);
        context.expectEntityWithData(pos, EntityType.VILLAGER, frostGetter, initialTemperature);

        context.setBlockState(pos, warmLichen.getDefaultState());

        context.waitAndRun(10, () -> {
            context.addInstantFinalTask(() -> {
                context.assertTrue(
                        entity.thermoo$getTemperature() > -2000,
                        Text.literal(
                                String.format(
                                        "Villager temperature of %d is not greater than %d",
                                        entity.thermoo$getTemperature(),
                                        initialTemperature
                                )
                        )
                );
            });
        });
    }

    private static MobEntity setupWarmVillagerTest(TestContext context, BlockPos pos, int temperature) {
        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);

        EntityAttributeInstance maxTemperature = entity.getAttributeInstance(ThermooAttributes.MAX_TEMPERATURE);
        context.assertFalse(maxTemperature == null, Text.literal("Villager does not have a max temperature attribute"));
        maxTemperature.addTemporaryModifier(
                new EntityAttributeModifier(
                        FrostifulGameTest.id("max_temperature"),
                        40,
                        EntityAttributeModifier.Operation.ADD_VALUE
                )
        );

        entity.thermoo$setTemperature(temperature);
        context.expectEntityWithData(pos, EntityType.VILLAGER, TemperatureAware::thermoo$getTemperature, temperature);

        return entity;
    }
}
