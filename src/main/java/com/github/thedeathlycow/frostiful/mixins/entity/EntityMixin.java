package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.ConfigKeys;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.simple.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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

    private static int getTicksFromMaxFrost(final double maxFrost) {
        Config config = Frostiful.getConfig();
        return (int) (config.get(ConfigKeys.MAX_FROST_MULTIPLIER) * maxFrost);
    }
}
