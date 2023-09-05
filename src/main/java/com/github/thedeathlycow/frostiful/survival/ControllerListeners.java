package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.block.SunLichenBlock;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ControllerListeners extends EnvironmentControllerDecorator {

    public ControllerListeners(EnvironmentController controller) {
        super(controller);
    }

    @Override
    public int getFloorTemperature(LivingEntity entity, World world, BlockState state, BlockPos pos) {
        int base = super.getFloorTemperature(entity, world, state, pos);

        if (base > 0 && world.isClient) {
            SunLichenBlock.createFireParticles(world, entity.getBlockPos());
        }

        return base;
    }
}
