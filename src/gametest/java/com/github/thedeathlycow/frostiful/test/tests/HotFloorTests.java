package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.Villager;

@SuppressWarnings("unused")
public class HotFloorTests {
    @GameTest(structure = "frostiful-test:magma_block_test", maxTicks = 40)
    public void villagerOnMagmaHeatedMoreThanVillagerOnStone(GameTestHelper context) {
        int temperatureChange = FrostifulConfigYACL.freezingConfig().getHeatFromHotFloor();

        final BlockPos stonePos = new BlockPos(2, 2, 3);
        final BlockPos magmaPos = new BlockPos(4, 2, 3);
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
                20, () -> {
                    int magmaTemperature = magmaVillager.thermoo$getTemperature();
                    int stoneTemperature = stoneVillager.thermoo$getTemperature();

                    context.assertTrue(
                            magmaTemperature > stoneTemperature,
                            Component.literal(String.format(
                                    "Magma Villager temperature of %d is not greater than Stone Villager temperature of %d",
                                    magmaTemperature,
                                    stoneTemperature
                            ))
                    );
                    context.succeed();
                }
        );
    }

}
