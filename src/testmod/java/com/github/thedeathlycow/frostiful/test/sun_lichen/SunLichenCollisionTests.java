package com.github.thedeathlycow.frostiful.test.sun_lichen;

import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.block.SunLichenBlock;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

        context.setBlockState(pos, FrostifulBlocks.HOT_SUN_LICHEN.getDefaultState());
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void hotLichenDamages(TestContext context) {
        final BlockPos pos = new BlockPos(1, 2, 1);

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        context.expectEntityWithData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlockState(pos, FrostifulBlocks.HOT_SUN_LICHEN.getDefaultState());
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth() - 1.0f);
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void hotLichenWarmsAppropriateAmount(TestContext context) {
        doWarmVillagerTest(context, FrostifulBlocks.HOT_SUN_LICHEN);
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void warmLichenWarmsAppropriateAmount(TestContext context) {
        doWarmVillagerTest(context, FrostifulBlocks.WARM_SUN_LICHEN);
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void coolLichenWarmsAppropriateAmount(TestContext context) {
        doWarmVillagerTest(context, FrostifulBlocks.COOL_SUN_LICHEN);
    }

    @GameTest(structureName = "frostiful-test:sun_lichen_tests.platform")
    public void coldLichenDoesNotWarm(TestContext context) {
        doWarmVillagerTest(context, FrostifulBlocks.COLD_SUN_LICHEN);
    }

    private static void doWarmVillagerTest(TestContext context, Block block) {
        final BlockPos pos = new BlockPos(1, 2, 1);
        final int freezeAmount = 1000;
        final int level = ((SunLichenBlock) block).getHeatLevel();

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        entity.setFrozenTicks(freezeAmount);
        context.expectEntityWithData(pos, EntityType.VILLAGER, Entity::getFrozenTicks, freezeAmount);

        context.setBlockState(pos, block.getDefaultState());
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, Entity::getFrozenTicks, freezeAmount - level * FreezingConfigGroup.SUN_LICHEN_HEAT_PER_LEVEL.getValue());
    }
}
