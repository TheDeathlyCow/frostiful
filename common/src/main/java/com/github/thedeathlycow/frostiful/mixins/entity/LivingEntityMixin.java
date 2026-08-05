package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.registry.FEntityAttributes;
import com.github.thedeathlycow.frostiful.survival.system.FrostRootSystem;
import com.github.thedeathlycow.frostiful.survival.system.SnowAccumulationSystem;
import com.github.thedeathlycow.frostiful.survival.system.SoakedFreezingSystem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @ModifyReturnValue(
            method = "createLivingAttributes",
            at = @At("RETURN")
    )
    private static AttributeSupplier.Builder hookCreateLivingAttributes(AttributeSupplier.Builder original) {
        FEntityAttributes.createLivingAttributes(original);
        return original;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (!this.level().isClientSide()) {
            var self = (LivingEntity) (Object) this;
            FrostRootSystem.serverTick(self);
            SnowAccumulationSystem.serverTick(self);
            SoakedFreezingSystem.serverTick(self);
        }
    }
}