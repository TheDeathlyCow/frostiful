package com.github.thedeathlycow.frostiful.test.sun_lichen;

import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.block.SunLichenBlock;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

public class SunLichenCollisionTests implements FabricGameTest {

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void coolLichenDoesNotDamage(TestContext context) {
        final BlockPos pos = new BlockPos(1, 2, 1);

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        context.expectEntityWithData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlockState(pos, FrostifulBlocks.SUN_LICHEN.getDefaultState().with(SunLichenBlock.HEAT_LEVEL, 0));
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void hotLichenDamages(TestContext context) {
        final BlockPos pos = new BlockPos(1, 2, 1);
        final int level = SunLichenBlock.MAX_HEAT_LEVEL;

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        context.expectEntityWithData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlockState(pos, FrostifulBlocks.SUN_LICHEN.getDefaultState()
                .with(SunLichenBlock.HEAT_LEVEL, level));
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth() - 1.0f);
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void level3LichenWarmsAppropriateAmount(TestContext context) {
        doWarmVillagerTest(context, 3);
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void level2LichenWarmsAppropriateAmount(TestContext context) {
        doWarmVillagerTest(context, 2);
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void level1LichenWarmsAppropriateAmount(TestContext context) {
        doWarmVillagerTest(context, 1);
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void level0LichenDoesNotWarm(TestContext context) {
        doWarmVillagerTest(context, 0);
    }

    private static void doWarmVillagerTest(TestContext context, int level) {
        final BlockPos pos = new BlockPos(1, 2, 1);
        final int freezeAmount = 1000;

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        entity.setFrozenTicks(freezeAmount);
        context.expectEntityWithData(pos, EntityType.VILLAGER, Entity::getFrozenTicks, freezeAmount);

        context.setBlockState(pos, FrostifulBlocks.SUN_LICHEN.getDefaultState().with(SunLichenBlock.HEAT_LEVEL, level));
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, Entity::getFrozenTicks, freezeAmount - level * FreezingConfigGroup.SUN_LICHEN_HEAT_PER_LEVEL.getValue());
    }
}
