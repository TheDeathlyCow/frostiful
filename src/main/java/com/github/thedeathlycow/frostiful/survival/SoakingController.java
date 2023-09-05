package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import com.github.thedeathlycow.thermoo.api.temperature.Soakable;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.LightType;

public class SoakingController extends EnvironmentControllerDecorator {

    public SoakingController(EnvironmentController controller) {
        super(new DryingController(new WettingController(controller)));
    }

    private static class DryingController extends EnvironmentControllerDecorator {

        private DryingController(EnvironmentController controller) {
            super(controller);
        }

        @Override
        public int getSoakChange(Soakable soakable) {
            int soakChange = controller.getSoakChange(soakable);

            if (!(soakable instanceof LivingEntity entity)) {
                return soakChange;
            }
            FrostifulConfig config = Frostiful.getConfig();

            // dry off slowly when not being wetted
            if (soakChange <= 0 && entity.thermoo$isWet()) {
                soakChange = -config.environmentConfig.getDryRate();
            }

            // increase drying from block light
            int blockLightLevel = entity.getWorld().getLightLevel(LightType.BLOCK, entity.getBlockPos());
            if (blockLightLevel > 0) {
                soakChange -= blockLightLevel / 4;
            }

            if (entity.isOnFire()) {
                soakChange -= config.environmentConfig.getOnFireDryDate();
            }

            return soakChange;
        }
    }

    private static class WettingController extends EnvironmentControllerDecorator {

        private WettingController(EnvironmentController controller) {
            super(controller);
        }

        @Override
        public int getSoakChange(Soakable soakable) {

            int soakChange = 0;

            if (!(soakable instanceof LivingEntity entity)) {
                return soakChange;
            }

            FrostifulConfig config = Frostiful.getConfig();
            EntityInvoker invoker = (EntityInvoker) entity;


            // add wetness from rain
            if (invoker.frostiful$invokeIsBeingRainedOn()) {
                soakChange += config.environmentConfig.getRainWetnessIncrease();
            }

            // add wetness when touching, but not submerged in, water
            if (entity.isTouchingWater() || entity.getBlockStateAtPos().isOf(Blocks.WATER_CAULDRON)) {
                soakChange += config.environmentConfig.getTouchingWaterWetnessIncrease();
            }

            // immediately soak players in water
            if (entity.isSubmergedInWater() || invoker.frostiful$invokeIsInsideBubbleColumn()) {
                soakChange = entity.thermoo$getMaxWetTicks();
            }

            return soakChange;
        }

    }
}
