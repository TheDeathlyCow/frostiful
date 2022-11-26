package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezeDamageConfigMixin extends Entity {

    public LivingEntityFreezeDamageConfigMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyConstant(
            method = "tickMovement",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/LivingEntity;addPowderSnowSlowIfNeeded()V"
                    )
            ),
            constant = @Constant(
                    intValue = 40,
                    ordinal = 0
            )
    )
    private int configFreezeDamageRate(int constant) {
        FrostifulConfig config = Frostiful.getConfig();
        return config.freezingConfig.getFreezeDamageRate();
    }

    @ModifyArg(
            method = "tickMovement",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/LivingEntity;addPowderSnowSlowIfNeeded()V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
                    ordinal = 0
            ),
            index = 1
    )
    private float configFreezeDamageAmount(float amount) {
        FrostifulConfig config = Frostiful.getConfig();
        return this.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ?
                config.freezingConfig.getFreezeDamageExtraAmount() :
                config.freezingConfig.getFreezeDamageAmount();
    }
}
