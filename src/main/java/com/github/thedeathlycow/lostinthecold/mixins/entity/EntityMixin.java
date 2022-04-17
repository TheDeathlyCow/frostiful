package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.attributes.LostInTheColdEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.LostInTheColdConfig;
import com.github.thedeathlycow.lostinthecold.config.ConfigKeys;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
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
            // add more time to freeze based on frost resistance
            if (livingEntity.getAttributes().hasAttribute(LostInTheColdEntityAttributes.FROST_RESISTANCE)) {
                double frostResistance = livingEntity.getAttributes().getValue(LostInTheColdEntityAttributes.FROST_RESISTANCE);
                int freezeTickDamageThreshold = getTicksFromFrostResistance(frostResistance);
                cir.setReturnValue(freezeTickDamageThreshold);
            }
        }
    }

    private static int getTicksFromFrostResistance(final double frostResistance) {
        LostInTheColdConfig config = LostInTheCold.getConfig();
        if (config == null) {
            LostInTheCold.LOGGER.warn("EntityMixin: Hypothermia config not found!");
            return 0;
        }
        return (int)(config.get(ConfigKeys.TICKS_PER_FROST_RESISTANCE) * frostResistance);
    }
}
