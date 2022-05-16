package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
abstract class EntityMixin {

    @Inject(at = @At("HEAD"), method = "getMinFreezeDamageTicks()I", cancellable = true)
    private void getMinFreezeDamageTicks(CallbackInfoReturnable<Integer> cir) {
        Entity instance = (Entity) (Object) this;

        if (instance instanceof LivingEntity livingEntity) {
            // add more time to freeze
            if (livingEntity.getAttributes().hasAttribute(FrostifulEntityAttributes.MAX_FROST)) {
                double maxFrost = livingEntity.getAttributes().getValue(FrostifulEntityAttributes.MAX_FROST);
                int freezeTickDamageThreshold = getTicksFromMaxFrost(maxFrost);
                cir.setReturnValue(freezeTickDamageThreshold);
            }
        }
    }

    @Redirect(
            method = "baseTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getFrozenTicks()I"
            )
    )
    private int doNotExtinguishColdPlayers(Entity instance) {
        return 0;
    }

    private static int getTicksFromMaxFrost(final double maxFrost) {
        return (int) (AttributeConfigGroup.MAX_FROST_MULTIPLIER.getValue() * maxFrost);
    }
}
