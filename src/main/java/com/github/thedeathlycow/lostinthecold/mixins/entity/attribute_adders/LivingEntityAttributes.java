package com.github.thedeathlycow.lostinthecold.mixins.entity.attribute_adders;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.HypothermiaConfig;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
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
        HypothermiaConfig config = LostInTheCold.getConfig();
        if (config == null) {
            LostInTheCold.LOGGER.warn("LivingEntityAttributes: Hypothermia config not found!");
            return;
        }

        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(ModEntityAttributes.FROST_RESISTANCE, config.getBaseEntityFrostResistance());
        cir.setReturnValue(attributeBuilder);
    }

}
