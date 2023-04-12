package com.github.thedeathlycow.frostiful.test;

import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentManager;
import com.github.thedeathlycow.thermoo.impl.EnvironmentControllerImpl;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FrostifulTestMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Mock the environment controller to disable passive temperature changes
        EnvironmentManager.INSTANCE.setController(
                new EnvironmentControllerImpl() {
                    @Override
                    public int getLocalTemperatureChange(World world, BlockPos pos) {
                        return 0;
                    }

                    @Override
                    public int getHeatAtLocation(World world, BlockPos pos) {
                        return 0;
                    }
                }
        );
    }
}
