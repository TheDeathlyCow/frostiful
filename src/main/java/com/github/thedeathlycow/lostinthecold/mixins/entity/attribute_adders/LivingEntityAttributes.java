package com.github.thedeathlycow.lostinthecold.mixins.entity.attribute_adders;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.FreezingValues;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityAttributes {

    @Inject(
            method = "createLivingAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addFrostResistanceAttributeToLivingEntity(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(ModEntityAttributes.FROST_RESISTANCE, FreezingValues.BASE_FROST_RESISTANCE);
        cir.setReturnValue(attributeBuilder);
    }

}
