package com.github.thedeathlycow.frostiful.test.effects;

import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class LocalTemperatureTests {

    @GameTest(templateName = "frostiful-test:effects.local_temperature")
    public void villager_is_warmed_by_torch(TestContext context) {
        BlockPos pos = new BlockPos(1, 2, 1);

        EnvironmentController controller = EnvironmentManager.INSTANCE.getController();

        int locationHeat = controller.getHeatAtLocation(context.getWorld(), pos);

        context.spawnMob(EntityType.VILLAGER, pos);

        context.expectEntityWithDataEnd(
                pos,
                EntityType.VILLAGER,
                e -> {
                    return controller.getHeatAtLocation(e.getWorld(), e.getBlockPos());
                },
                locationHeat
        );
    }

    @GameTest(templateName = "frostiful-test:effects.local_temperature")
    public void villager_in_boat_is_warmed_by_torch(TestContext context) {
        BlockPos pos = new BlockPos(1, 2, 1);

        EnvironmentController controller = EnvironmentManager.INSTANCE.getController();

        int locationHeat = controller.getHeatAtLocation(context.getWorld(), pos);

        VillagerEntity villager = context.spawnMob(EntityType.VILLAGER, pos);
        Entity boat = context.spawnEntity(EntityType.BOAT, pos);
        villager.startRiding(boat, true);

        context.expectEntityWithDataEnd(
                pos,
                EntityType.VILLAGER,
                e -> {
                    return controller.getHeatAtLocation(e.getWorld(), e.getBlockPos());
                },
                locationHeat
        );
    }

}
