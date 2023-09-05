package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;

public class PlayerTemperatureController extends EnvironmentControllerDecorator {

    public PlayerTemperatureController(EnvironmentController controller) {
        super(controller);
    }

    @Override
    public double getBaseValueForAttribute(EntityAttribute attribute, LivingEntity entity) {
        double base = controller.getBaseValueForAttribute(attribute, entity);
        if (attribute == ThermooAttributes.MIN_TEMPERATURE && entity.isPlayer()) {
            base += 5.0;
        }
        return base;
    }
}
