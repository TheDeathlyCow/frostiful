package com.github.thedeathlycow.frostiful.test.tests;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class BlockLightTemperatureTests {
    @GameTest(structure = "frostiful-test:effects.local_temperature")
    public void villagerIsWarmedByTorch(TestContext context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        int temperature = -2000;

        VillagerEntity villager = context.spawnMob(EntityType.VILLAGER, pos);
        villager.thermoo$setTemperature(temperature);

        context.expectEntityWithData(
                pos,
                EntityType.VILLAGER,
                VillagerEntity::thermoo$getTemperature,
                temperature
        );

        context.expectBlock(Blocks.TORCH, pos);

        context.waitAndRun(
                20L,
                () -> {
                    context.assertTrue(
                            villager.thermoo$getTemperature() > temperature,
                            Text.literal(
                                    String.format(
                                            "Villager temperature of %d is not greater than %d",
                                            villager.thermoo$getTemperature(),
                                            temperature
                                    )
                            )
                    );
                    context.complete();
                }
        );
    }

    @GameTest(structure = "frostiful-test:effects.local_temperature")
    public void villagerInBoatIsWarmedByTorch(TestContext context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        int temperature = -2000;

        VillagerEntity villager = context.spawnMob(EntityType.VILLAGER, pos);
        Entity boat = context.spawnEntity(EntityType.OAK_BOAT, pos);
        villager.startRiding(boat, true);

        villager.thermoo$setTemperature(temperature);

        context.expectEntityWithData(
                pos,
                EntityType.VILLAGER,
                VillagerEntity::thermoo$getTemperature,
                temperature
        );

        context.expectEntityWithData(
                pos,
                EntityType.VILLAGER,
                e -> {
                    Entity vehicle = e.getVehicle();
                    context.assertFalse(vehicle == null, Text.literal("Villager must have a vehicle"));
                    return vehicle.getId();
                },
                boat.getId()
        );

        context.expectBlock(Blocks.TORCH, pos);

        context.waitAndRun(
                5L,
                () -> {
                    context.assertTrue(
                            villager.thermoo$getTemperature() > temperature,
                            Text.literal(
                                    String.format(
                                            "Villager temperature of %d is not greater than %d",
                                            villager.thermoo$getTemperature(),
                                            temperature
                                    )
                            )
                    );
                    context.complete();
                }
        );
    }


    @GameTest(structure = "frostiful-test:effects.local_temperature")
    public void villagerIsNotWarmedWithoutTorch(TestContext context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        int temperature = -2000;

        VillagerEntity villager = context.spawnMob(EntityType.VILLAGER, pos);
        villager.thermoo$setTemperature(temperature);

        context.expectEntityWithData(
                pos,
                EntityType.VILLAGER,
                VillagerEntity::thermoo$getTemperature,
                temperature
        );

        context.setBlockState(pos, Blocks.AIR.getDefaultState());
        context.dontExpectBlock(Blocks.TORCH, pos);

        context.waitAndRun(
                5L,
                () -> {
                    int villagerTemperature = villager.thermoo$getTemperature();
                    context.assertTrue(
                            villagerTemperature == temperature,
                            Text.literal(
                                    String.format(
                                            "Villager temperature of %d does not match expected %d",
                                            villagerTemperature,
                                            temperature
                                    )
                            )
                    );
                    context.complete();
                }
        );
    }

}
