package com.github.thedeathlycow.frostiful.test.damage;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.AfterBatch;
import net.minecraft.test.BeforeBatch;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

@SuppressWarnings("unused")
public class HotFloorTests {

    @BeforeBatch(batchId = "hotFloorTests")
    public void mockController(ServerWorld serverWorld) {
        EnvironmentManager.INSTANCE.addController(
                controller ->
                        new EnvironmentControllerDecorator(controller) {
                            @Override
                            public int getLocalTemperatureChange(World world, BlockPos pos) {
                                return 0;
                            }

                            @Override
                            public int getHeatAtLocation(World world, BlockPos pos) {
                                return 0;
                            }

                            @Override
                            public int getTemperatureEffectsChange(LivingEntity entity) {
                                return 0;
                            }
                        }
        );
    }

    @AfterBatch(batchId = "hotFloorTests")
    public void resetController(ServerWorld serverWorld) {
        EnvironmentManager.INSTANCE.peelController();
    }

    @GameTest(batchId = "hotFloorTests", templateName = "frostiful-test:sun_lichen_tests.platform")
    public void when_villager_standing_on_magma_block_then_villager_is_heated(TestContext context) {
        var config = Frostiful.getConfig();
        expectVillagerIsHeated(context, Blocks.MAGMA_BLOCK.getDefaultState(), config.freezingConfig.getHeatFromHotFloor());
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
                temperature + (temperatureChange * ((int) context.getTick() - 1))
        )));


    }

}
