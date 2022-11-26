package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSource;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezingEffects extends Entity {

    private static final UUID FREEZING_SLOW_ID = UUID.fromString("31e65736-ac14-4951-925a-f30969276138");

    public LivingEntityFreezingEffects(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    @Nullable
    public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

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
                    target = "Lnet/minecraft/entity/LivingEntity;removePowderSnowSlow()V"
            )
    )
    private void tickFreezingEffects(CallbackInfo ci) {
        this.world.getProfiler().push("frostifulFreezing");
        final LivingEntity instance = (LivingEntity) (Object) this;
        final FreezableEntity freezable = (FreezableEntity) this;
        this.frostiful$removeAttributeEffects();
        //this.frostiful$addAttributeEffects(freezable);
        this.frostiful$tickFreezeDamage(freezable);
        //this.frostiful$tickEntityFreezing();
        FrostHelper.applyEffects(instance);
        this.world.getProfiler().pop();
    }

//    private void frostiful$tickEntityFreezing() {
//        if (this.isAlive()) {
//            int amount = 0;
//            FrostifulConfig config = Frostiful.getConfig();
//            if (this.inPowderSnow) {
//                amount += config.freezingConfig.getPowderSnowFreezeRate();
//            }
//            FrostHelper.addLivingFrost((LivingEntity) (Object) this, amount);
//        }
//    }

    private void frostiful$tickFreezeDamage(FreezableEntity freezable) {

        if (this.world.isClient) {
            return;
        }

        FrostifulConfig config = Frostiful.getConfig();
        if (this.age % config.freezingConfig.getFreezeDamageRate() != 0) {
            return;
        }

        if (freezable.frostiful$isFrozen() && freezable.frostiful$canFreeze()) {
            int amount = this.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ?
                    config.freezingConfig.getFreezeDamageExtraAmount() :
                    config.freezingConfig.getFreezeDamageAmount();

            this.damage(DamageSource.FREEZE, amount);
        }
    }

    private void frostiful$removeAttributeEffects() {
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (entityAttributeInstance != null) {
            if (entityAttributeInstance.getModifier(FREEZING_SLOW_ID) != null) {
                entityAttributeInstance.removeModifier(FREEZING_SLOW_ID);
            }
        }
    }

//    private void frostiful$addAttributeEffects(FreezableEntity freezable) {
//        if (freezable.frostiful$getCurrentFrost() > 0) {
//            EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
//            if (entityAttributeInstance == null) {
//                return;
//            }
//
//            double slownessModifier = -0.05 * freezable.frostiful$getFrostProgress();
//            entityAttributeInstance.addTemporaryModifier(
//                    new EntityAttributeModifier(
//                            FREEZING_SLOW_ID,
//                            "Frostiful freezing slow",
//                            slownessModifier,
//                            EntityAttributeModifier.Operation.ADDITION
//                    )
//            );
//        }
//    }

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
