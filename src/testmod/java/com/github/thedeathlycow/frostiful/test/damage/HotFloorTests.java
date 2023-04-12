package com.github.thedeathlycow.frostiful.test.damage;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.command.GameRuleCommand;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

import java.util.function.Function;

public class HotFloorTests {

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void when_villager_standing_on_magma_block_then_villager_is_heated(TestContext context) {
        var config = Frostiful.getConfig();
        expectVillagerIsHeated(context, Blocks.MAGMA_BLOCK.getDefaultState(), config.freezingConfig.getHeatFromHotFloor());
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void when_villager_standing_on_campfire_then_villager_is_heated(TestContext context) {
        var config = Frostiful.getConfig();
        expectVillagerIsHeated(context, Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, true), config.freezingConfig.getHeatFromHotFloor());
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void when_villager_standing_on_soul_campfire_then_villager_is_heated(TestContext context) {
        var config = Frostiful.getConfig();
        expectVillagerIsHeated(context, Blocks.SOUL_CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, true), config.freezingConfig.getHeatFromHotFloor());
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void when_villager_standing_on_unlit_campfire_then_villager_is_heated(TestContext context) {
        var config = Frostiful.getConfig();
        expectVillagerIsHeated(context, Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, false), 0);
    }

    @GameTest(templateName = "frostiful-test:sun_lichen_tests.platform")
    public void when_villager_standing_on_unlit_soul_campfire_then_villager_is_heated(TestContext context) {
        var config = Frostiful.getConfig();
        expectVillagerIsHeated(context, Blocks.SOUL_CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, false), 0);
    }

    private static void expectVillagerIsHeated(TestContext context, BlockState state, int temperatureChange) {
        final BlockPos pos = new BlockPos(1, 3, 1);
        final int temperature = -1000;

        final VillagerEntity entity = context.spawnMob(EntityType.VILLAGER, pos);
        final Function<VillagerEntity, Integer> temperatureGetter = TemperatureAware::thermoo$getTemperature;

        entity.thermoo$setTemperature(temperature);
        context.expectEntityWithData(pos, EntityType.VILLAGER, temperatureGetter, temperature);

        context.setBlockState(pos.down(), state);
        context.waitAndRun(5, () -> context.addInstantFinalTask(() -> context.testEntityProperty(
                entity,
                temperatureGetter,
                "temperature",
                temperature + temperatureChange
        )));


    }

}
