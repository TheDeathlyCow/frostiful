package com.github.thedeathlycow.frostiful.test.tests;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("unused")
public class BlockLightTemperatureTests {
    @GameTest(structure = "frostiful-test:effects.local_temperature")
    public void villagerIsWarmedByTorch(GameTestHelper context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        int temperature = -2000;

        Villager villager = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        villager.thermoo$setTemperature(temperature);

        context.assertEntityData(
                pos,
                EntityType.VILLAGER,
                Villager::thermoo$getTemperature,
                temperature
        );

        context.assertBlockPresent(Blocks.TORCH, pos);

        context.runAfterDelay(
                20L,
                () -> {
                    context.assertTrue(
                            villager.thermoo$getTemperature() > temperature,
                            Component.literal(
                                    String.format(
                                            "Villager temperature of %d is not greater than %d",
                                            villager.thermoo$getTemperature(),
                                            temperature
                                    )
                            )
                    );
                    context.succeed();
                }
        );
    }

    @GameTest(structure = "frostiful-test:effects.local_temperature")
    public void villagerInBoatIsWarmedByTorch(GameTestHelper context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        int temperature = -2000;

        Villager villager = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        Entity boat = context.spawn(EntityType.OAK_BOAT, pos);
        villager.startRiding(boat, true, true);

        villager.thermoo$setTemperature(temperature);

        context.assertEntityData(
                pos,
                EntityType.VILLAGER,
                Villager::thermoo$getTemperature,
                temperature
        );

        context.assertEntityData(
                pos,
                EntityType.VILLAGER,
                e -> {
                    Entity vehicle = e.getVehicle();
                    context.assertFalse(vehicle == null, Component.literal("Villager must have a vehicle"));
                    return vehicle.getId();
                },
                boat.getId()
        );

        context.assertBlockPresent(Blocks.TORCH, pos);

        context.runAfterDelay(
                5L,
                () -> {
                    context.assertTrue(
                            villager.thermoo$getTemperature() > temperature,
                            Component.literal(
                                    String.format(
                                            "Villager temperature of %d is not greater than %d",
                                            villager.thermoo$getTemperature(),
                                            temperature
                                    )
                            )
                    );
                    context.succeed();
                }
        );
    }


    @GameTest(structure = "frostiful-test:effects.local_temperature")
    public void villagerIsNotWarmedWithoutTorch(GameTestHelper context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        int temperature = -2000;

        Villager villager = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        villager.thermoo$setTemperature(temperature);

        context.assertEntityData(
                pos,
                EntityType.VILLAGER,
                Villager::thermoo$getTemperature,
                temperature
        );

        context.setBlock(pos, Blocks.AIR.defaultBlockState());
        context.assertBlockNotPresent(Blocks.TORCH, pos);

        context.runAfterDelay(
                5L,
                () -> {
                    int villagerTemperature = villager.thermoo$getTemperature();
                    context.assertTrue(
                            villagerTemperature == temperature,
                            Component.literal(
                                    String.format(
                                            "Villager temperature of %d does not match expected %d",
                                            villagerTemperature,
                                            temperature
                                    )
                            )
                    );
                    context.succeed();
                }
        );
    }

}
