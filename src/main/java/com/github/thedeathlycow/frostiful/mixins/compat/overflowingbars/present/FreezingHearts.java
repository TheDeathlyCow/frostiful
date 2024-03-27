package com.github.thedeathlycow.frostiful.mixins.compat.overflowingbars.present;

import fuzs.overflowingbars.client.handler.HealthBarRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.util.Arrays;

@Mixin(targets = "fuzs.overflowingbars.client.handler.HealthBarRenderer$HeartType")
public abstract class FreezingHearts {

    @Unique
    private static Object frostiful$frozenHeartType;

    /**
     * Lazily instantiate {@link #frostiful$frozenHeartType}. The return value should be a reference to
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
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isFrozen()Z",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private static void setFreezingHeartsWhenFrozen(
            PlayerEntity player,
            boolean absorbing,
            boolean orange,
            CallbackInfoReturnable<Object> cir // THIS IS ACTUALLY A HEART TYPE!!!!
    ) {
        if (player.thermoo$getTemperatureScale() <= -0.99) {
            if (frostiful$frozenHeartType == null) {
                frostiful$frozenHeartType = getFrozenHeartField();
            }

            cir.setReturnValue(frostiful$frozenHeartType);
        }
    }

}
