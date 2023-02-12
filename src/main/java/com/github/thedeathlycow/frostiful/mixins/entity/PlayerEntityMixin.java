package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(
            method = "createPlayerAttributes",
            at = @At("TAIL")
    )
    private static void overrideDefaultMinimumTemperatureValue(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(ThermooAttributes.MIN_TEMPERATURE, 45.0d);
    }
}
