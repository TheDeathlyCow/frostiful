package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.Config;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.world.ModGameRules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(
            method = "canFreeze",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void creativePlayersCannotFreeze(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof PlayerEntity player) {
            World world = player.getWorld();
            if (player.isCreative() && world.getGameRules().getBoolean(ModGameRules.DO_PASSIVE_FREEZING)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V"
            )
    )
    private void blockDefaultPowderSnowFreezingForPlayer(LivingEntity instance, int i) {
        if (instance instanceof PlayerEntity) {
            if (!instance.getWorld().getGameRules().getBoolean(ModGameRules.DO_PASSIVE_FREEZING)) {
                instance.setFrozenTicks(i);
            }
        } else {
            instance.setFrozenTicks(i);
        }
    }

    @Inject(
            method = "createLivingAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addFrostResistanceAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        Config config = LostInTheCold.getConfig();
        if (config == null) {
            LostInTheCold.LOGGER.warn("LivingEntityMixin: Hypothermia config not found!");
            return;
        }

        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(ModEntityAttributes.FROST_RESISTANCE, config.getDouble("base_entity_frost_resistance"));
        cir.setReturnValue(attributeBuilder);
    }

}
