package com.github.thedeathlycow.frostiful.test.sun_lichen;

import com.github.thedeathlycow.frostiful.block.FBlocks;
import com.github.thedeathlycow.frostiful.block.SunLichenBlock;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

import java.util.function.Function;

@SuppressWarnings("unused")
public final class SunLichenCollisionTests implements FabricGameTest {

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void cool_lichen_does_not_damage(TestContext context) {
        final BlockPos pos = new BlockPos(1, 2, 1);

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        context.expectEntityWithData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlockState(pos, FBlocks.HOT_SUN_LICHEN.getDefaultState());
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void hot_lichen_damages(TestContext context) {
        final BlockPos pos = new BlockPos(1, 2, 1);

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        context.expectEntityWithData(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth());

        context.setBlockState(pos, FBlocks.HOT_SUN_LICHEN.getDefaultState());
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, LivingEntity::getHealth, entity.getMaxHealth() - 1.0f);
    }

    /**
     * This test will probably fail because hot sun lichen is *just* hot enough to provide a bit of passive warmth,
     * will be fixed later.
     *
     * @param context
     */
    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform", required = false)
    public void hot_lichen_warms_appropriate_amount(TestContext context) {
        doWarmVillagerTest(context, FBlocks.HOT_SUN_LICHEN);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void warm_lichen_warms_appropriate_amount(TestContext context) {
        doWarmVillagerTest(context, FBlocks.WARM_SUN_LICHEN);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void cool_lichen_warms_appropriate_amount(TestContext context) {
        doWarmVillagerTest(context, FBlocks.COOL_SUN_LICHEN);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void cold_lichen_does_not_warm(TestContext context) {
        doWarmVillagerTest(context, FBlocks.COLD_SUN_LICHEN);
    }

    private static void doWarmVillagerTest(TestContext context, Block block) {
        final BlockPos pos = new BlockPos(1, 2, 1);
        final int freezeAmount = -1000;
        final int level = ((SunLichenBlock) block).getHeatLevel();

        final MobEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        final Function<VillagerEntity, Integer> frostGetter = TemperatureAware::thermoo$getTemperature;

        entity.thermoo$setTemperature(freezeAmount);
        context.expectEntityWithData(pos, EntityType.VILLAGER, frostGetter, freezeAmount);

        context.setBlockState(pos, block.getDefaultState());
        FrostifulConfig config = Frostiful.getConfig();
        context.expectEntityWithDataEnd(pos, EntityType.VILLAGER, frostGetter, freezeAmount + level * config.freezingConfig.getSunLichenHeatPerLevel());
    }
}
