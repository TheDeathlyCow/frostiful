package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSource;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezingEffects extends Entity {

    public LivingEntityFreezingEffects(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;tick()V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void doHeating(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        World world = livingEntity.getWorld();

        if (world.isClient()) {
            return;
        }

        int warmth = PassiveFreezingHelper.getWarmth(livingEntity);
        FrostHelper.removeLivingFrost(livingEntity, warmth);
    }

    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;addPowderSnowSlowIfNeeded()V",
                    shift = At.Shift.AFTER
            )
    )
    private void tickFreezingEffects(CallbackInfo ci) {
        if (this.world.isClient) {
            return;
        }
        this.world.getProfiler().push("frostiful.freezing_effects");
        final LivingEntity instance = (LivingEntity) (Object) this;
        FrostHelper.applyEffects(instance);
        this.world.getProfiler().pop();
    }

    @Inject(
            method = "createLivingAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(FEntityAttributes.FROST_RESISTANCE);
        attributeBuilder.add(FEntityAttributes.MAX_FROST);
        cir.setReturnValue(attributeBuilder);
    }

    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;sendEntityStatus(Lnet/minecraft/entity/Entity;B)V",
                    ordinal = 2
            )
    )
    private void syncFrozenAttackSourceAsFrozenSource(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (FDamageSource.FROZEN_ATTACK_NAME.equals(source.name)) {
            LivingEntity instance = (LivingEntity) (Object) this;
            this.world.sendEntityStatus(instance, EntityStatuses.DAMAGE_FROM_FREEZING);
        }
    }
}
