package com.github.thedeathlycow.frostiful.test.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("unused")
public class BlockLightTemperatureTests {
    @GameTest(template = "frostiful-test:effects.local_temperature")
    public void villager_is_warmed_by_torch(GameTestHelper context) {
        BlockPos pos = new BlockPos(1, 2, 1);
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
                            String.format(
                                    "Villager temperature of %d is not greater than %d",
                                    villager.thermoo$getTemperature(),
                                    temperature
                            )
                    );
                    context.succeed();
                }
        );
    }

    @GameTest(template = "frostiful-test:effects.local_temperature")
    public void villager_in_boat_is_warmed_by_torch(GameTestHelper context) {
        BlockPos pos = new BlockPos(1, 2, 1);
        int temperature = -2000;

        Villager villager = context.spawnWithNoFreeWill(EntityType.VILLAGER, pos);
        Entity boat = context.spawn(EntityType.BOAT, pos);
        villager.startRiding(boat, true);

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
                    context.assertFalse(vehicle == null, "Villager must have a vehicle");
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
                            String.format(
                                    "Villager temperature of %d is not greater than %d",
                                    villager.thermoo$getTemperature(),
                                    temperature
                            )
                    );
                    context.succeed();
                }
        );
    }


    @GameTest(template = "frostiful-test:effects.local_temperature")
    public void villager_is_not_warmed(GameTestHelper context) {
        BlockPos pos = new BlockPos(1, 2, 1);
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
                            String.format(
                                    "Villager temperature of %d does not match expected %d",
                                    villagerTemperature,
                                    temperature
                            )
                    );
                    context.succeed();
                }
        );
    }

}
