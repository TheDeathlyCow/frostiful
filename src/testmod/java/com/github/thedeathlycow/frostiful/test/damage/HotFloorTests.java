package com.github.thedeathlycow.frostiful.test.damage;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@SuppressWarnings("unused")
@GameTestHolder(FrostifulGameTest.MODID)
@PrefixGameTestTemplate(false)
public class HotFloorTests {
    @GameTest(template = "magma_block_test")
    public void villager_on_magma_heated_more_than_villager_on_stone(GameTestHelper context) {
        int temperatureChange = Frostiful.getConfig().freezingConfig.getHeatFromHotFloor();

        final BlockPos stonePos = new BlockPos(2, 3, 3);
        final BlockPos magmaPos = new BlockPos(4, 3, 3);
        final int initialTemperature = -1000;

        final Villager magmaVillager = context.spawnWithNoFreeWill(EntityType.VILLAGER, magmaPos);
        final Villager stoneVillager = context.spawnWithNoFreeWill(EntityType.VILLAGER, stonePos);

        stoneVillager.thermoo$setTemperature(initialTemperature);
        magmaVillager.thermoo$setTemperature(initialTemperature);
        context.assertEntityData(
                magmaPos, EntityType.VILLAGER,
                TemperatureAware::thermoo$getTemperature, initialTemperature
        );
        context.assertEntityData(
                stonePos, EntityType.VILLAGER,
                TemperatureAware::thermoo$getTemperature, initialTemperature
        );
        context.runAfterDelay(
                20L, () -> {
                    context.assertTrue(
                            magmaVillager.thermoo$getTemperature() > stoneVillager.thermoo$getTemperature(),
                            String.format(
                                    "Magma Villager temperature of %d is not greater than Stone Villager temperature of %d",
                                    magmaVillager.thermoo$getTemperature(),
                                    stoneVillager.thermoo$getTemperature()
                            )
                    );
                    context.succeed();
                }
        );
    }

}
