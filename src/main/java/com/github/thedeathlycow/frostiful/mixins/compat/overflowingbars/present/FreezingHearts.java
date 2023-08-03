package com.github.thedeathlycow.frostiful.mixins.compat.overflowingbars.present;

import com.github.thedeathlycow.frostiful.Frostiful;
import fuzs.overflowingbars.client.handler.HealthBarRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.util.Arrays;

@Mixin(targets = "fuzs.overflowingbars.client.handler.HealthBarRenderer$HeartType")
public abstract class FreezingHearts {

    private static Object frostiful$FrozenHeartType;

    /**
     * Lazily instantiate {@link #frostiful$FrozenHeartType}. The return value should be a reference to
     * {@link fuzs.overflowingbars.client.handler.HealthBarRenderer.HeartType#FROZEN}, but because the class is package
     * private and a reference cannot be returned directly
     *
     * @return Returns the enum field {@link fuzs.overflowingbars.client.handler.HealthBarRenderer.HeartType#FROZEN}, but
     * as an object reference
     */
    private static Object getFrozenHeartField() {
        try {
            // yes, this is disgusting, but the class is package private so here we are
            // :)
            Field field = Arrays.stream(HealthBarRenderer.class.getDeclaredClasses())
                    .filter(c -> {
                        return c.getSimpleName().equals("HeartType");
                    })
                    .findFirst()
                    .orElseThrow()
                    .getField("FROZEN");

            if (field.trySetAccessible()) {
                return field.get(null);
            } else {
                return null;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Inject(
            method = "forPlayer",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void setFreezingHeartsWhenFrozen(
            PlayerEntity player,
            boolean absorbing,
            boolean orange,
            CallbackInfoReturnable<Object> cir
    ) {
        if (player.thermoo$getTemperatureScale() <= -0.99) {
            if (frostiful$FrozenHeartType == null) {
                frostiful$FrozenHeartType = getFrozenHeartField();
            }

            cir.setReturnValue(frostiful$FrozenHeartType);
        }
    }

}
