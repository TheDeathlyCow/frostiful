package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class HotFloorTests {
    @GameTest(structure = "frostiful-test:magma_block_test", maxTicks = 40)
    public void villagerOnMagmaHeatedMoreThanVillagerOnStone(TestContext context) {
        int temperatureChange = Frostiful.getConfig().freezingConfig.getHeatFromHotFloor();

        final BlockPos stonePos = new BlockPos(2, 2, 3);
        final BlockPos magmaPos = new BlockPos(4, 2, 3);
        final int initialTemperature = -1000;

        final VillagerEntity magmaVillager = context.spawnMob(EntityType.VILLAGER, magmaPos);
        final VillagerEntity stoneVillager = context.spawnMob(EntityType.VILLAGER, stonePos);

        stoneVillager.thermoo$setTemperature(initialTemperature);
        magmaVillager.thermoo$setTemperature(initialTemperature);
        context.expectEntityWithData(
                magmaPos, EntityType.VILLAGER,
                TemperatureAware::thermoo$getTemperature, initialTemperature
        );
        context.expectEntityWithData(
                stonePos, EntityType.VILLAGER,
                TemperatureAware::thermoo$getTemperature, initialTemperature
        );
        context.waitAndRun(
                20, () -> {
                    int magmaTemperature = magmaVillager.thermoo$getTemperature();
                    int stoneTemperature = stoneVillager.thermoo$getTemperature();

                    context.assertTrue(
                            magmaTemperature > stoneTemperature,
                            Text.literal(String.format(
                                    "Magma Villager temperature of %d is not greater than Stone Villager temperature of %d",
                                    magmaTemperature,
                                    stoneTemperature
                            ))
                    );
                    context.complete();
                }
        );
    }

}
