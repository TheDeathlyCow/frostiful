package com.github.thedeathlycow.frostiful.test.sun_lichen;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

@SuppressWarnings("unused")
public final class SunLichenCollisionTests implements FabricGameTest {
    @GameTest(batch = "sunLichenCollision", template = "frostiful-test:sun_lichen_tests.platform")
    public void cool_lichen_does_not_damage(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 2, 1);

        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        context.assertEntityData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlock(pos, FBlocks.HOT_SUN_LICHEN.defaultBlockState());
        context.succeedWhenEntityData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());
    }

    @GameTest(batch = "sunLichenCollision", template = "frostiful-test:sun_lichen_tests.platform")
    public void hot_lichen_damages(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 2, 1);

        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);

        context.assertEntityData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlock(pos, FBlocks.HOT_SUN_LICHEN.defaultBlockState());
        context.succeedWhenEntityData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth() - 1.0f);
    }

    @GameTest(batch = "sunLichenCollision", template = "frostiful-test:sun_lichen_tests.platform")
    public void cold_lichen_does_not_warm(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 2, 1);

        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        final Function<Villager, Integer> frostGetter = TemperatureAware::thermoo$getTemperature;

        entity.thermoo$setTemperature(0);
        context.assertEntityData(pos, EntityType.VILLAGER, frostGetter, 0);

        context.setBlock(pos, FBlocks.COLD_SUN_LICHEN.defaultBlockState());

        context.succeedWhenEntityData(pos, EntityType.VILLAGER, frostGetter, 0);
    }

    @GameTest(batch = "sunLichenCollision", template = "frostiful-test:sun_lichen_tests.platform")
    public void sun_lichen_does_not_overheat(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 2, 1);

        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        final Function<Villager, Integer> frostGetter = TemperatureAware::thermoo$getTemperature;

        int freezeAmount = -500;
        entity.thermoo$setTemperature(freezeAmount);
        context.assertEntityData(pos, EntityType.VILLAGER, frostGetter, freezeAmount);

        context.setBlock(pos, FBlocks.HOT_SUN_LICHEN.defaultBlockState());

        context.succeedWhenEntityData(pos, EntityType.VILLAGER, frostGetter, 0);
    }

    @GameTest(batch = "sunLichenCollision", template = "frostiful-test:sun_lichen_tests.platform")
    public void warm_villager_is_burned_by_hot_sun_lichen(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 2, 1);
        int temperature = 500;
        Mob entity = setupWarmVillagerTest(context, pos, temperature);

        context.setBlock(pos, FBlocks.HOT_SUN_LICHEN.defaultBlockState());

        context.runAfterDelay(1L, () -> {
            context.assertEntityData(pos, EntityType.VILLAGER, TemperatureAware::thermoo$getTemperature, temperature);
            context.assertEntityData(pos, EntityType.VILLAGER, Entity::isOnFire, true);

            context.succeed();
        });
    }

    @GameTest(batch = "sunLichenCollision", template = "frostiful-test:sun_lichen_tests.platform")
    public void warm_villager_is_not_burned_by_cool_sun_lichen(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 2, 1);
        int temperature = 500;
        Mob entity = setupWarmVillagerTest(context, pos, temperature);

        context.setBlock(pos, FBlocks.COOL_SUN_LICHEN.defaultBlockState());

        context.runAfterDelay(1L, () -> {
            context.assertEntityData(pos, EntityType.VILLAGER, TemperatureAware::thermoo$getTemperature, temperature);
            context.assertEntityData(pos, EntityType.VILLAGER, Entity::isOnFire, false);

            context.succeed();
        });
    }

    @GameTest(batch = "sunLichenCollision", template = "frostiful-test:sun_lichen_tests.platform")
    public void hot_lichen_warms(GameTestHelper context) {
        expectWarmLichenWarmsVillager(context, FBlocks.HOT_SUN_LICHEN);
    }

    @GameTest(batch = "sunLichenCollision", template = "frostiful-test:sun_lichen_tests.platform")
    public void warm_lichen_warms(GameTestHelper context) {
        expectWarmLichenWarmsVillager(context, FBlocks.WARM_SUN_LICHEN);
    }

    @GameTest(batch = "sunLichenCollision", template = "frostiful-test:sun_lichen_tests.platform")
    public void cool_lichen_warms(GameTestHelper context) {
        expectWarmLichenWarmsVillager(context, FBlocks.COOL_SUN_LICHEN);
    }

    private static void expectWarmLichenWarmsVillager(GameTestHelper context, Block warmLichen) {
        final BlockPos pos = new BlockPos(1, 2, 1);

        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        final Function<Villager, Integer> frostGetter = TemperatureAware::thermoo$getTemperature;

        int initialTemperature = -2000;
        entity.thermoo$setTemperature(initialTemperature);
        context.assertEntityData(pos, EntityType.VILLAGER, frostGetter, initialTemperature);

        context.setBlock(pos, warmLichen.defaultBlockState());

        context.runAfterDelay(10, () -> {
            context.succeedWhen(() -> {
                context.assertTrue(
                        entity.thermoo$getTemperature() > -2000,
                        String.format(
                                "Villager temperature of %d is not greater than %d",
                                entity.thermoo$getTemperature(),
                                initialTemperature
                        )
                );
            });
        });
    }

    private static Mob setupWarmVillagerTest(GameTestHelper context, BlockPos pos, int temperature) {
        final Mob entity = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);

        AttributeInstance maxTemperature = entity.getAttribute(ThermooAttributes.MAX_TEMPERATURE);
        context.assertFalse(maxTemperature == null, "Villager does not have a max temperature attribute");
        maxTemperature.addTransientModifier(
                new AttributeModifier(
                        FrostifulGameTest.id("max_temperature"),
                        40,
                        AttributeModifier.Operation.ADD_VALUE
                )
        );

        entity.thermoo$setTemperature(temperature);
        context.assertEntityData(pos, EntityType.VILLAGER, TemperatureAware::thermoo$getTemperature, temperature);

        return entity;
    }
}
