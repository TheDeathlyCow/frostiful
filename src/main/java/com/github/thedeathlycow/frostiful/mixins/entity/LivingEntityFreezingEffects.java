package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.entity.effect.FrostifulStatusEffects;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import net.minecraft.entity.Entity;
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
import org.spongepowered.asm.mixin.injection.Slice;
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
                    target = "Lnet/minecraft/util/profiler/Profiler;pop()V",
                    ordinal = 1
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V"
                    )
            )
    )
    private void tickFrozenEffect(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasStatusEffect(FrostifulStatusEffects.FROZEN)) {
            entity.setJumping(false);
            entity.sidewaysSpeed = 0.0F;
            entity.forwardSpeed = 0.0F;
        }
    }

    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;removePowderSnowSlow()V"
            )
    )
    private void tickFreezingEffects(CallbackInfo ci) {
        final LivingEntity instance = (LivingEntity) (Object) this;
        final FreezableEntity freezable = (FreezableEntity) this;
        this.frostiful$removeAttributeEffects();
        this.frostiful$addAttributeEffects(freezable);
        this.frostiful$tickFreezeDamage(freezable);
        this.frostiful$tickEntityFreezing(freezable);
        FrostHelper.applyEffects(instance);
    }

    private void frostiful$tickEntityFreezing(FreezableEntity freezable) {
        if (this.isAlive()) {
            int amount = 0;

            if (this.inPowderSnow) {
                amount += FreezingConfigGroup.POWDER_SNOW_FREEZE_RATE.getValue();
            }

            freezable.frostiful$addFrost(amount);
        }
    }

    private void frostiful$tickFreezeDamage(FreezableEntity freezable) {

        if (this.world.isClient) {
            return;
        }

        if (this.age % FreezingConfigGroup.FREEZE_DAMAGE_RATE.getValue() != 0) {
            return;
        }

        if (freezable.frostiful$isFrozen() && freezable.frostiful$canFreeze()) {
            int amount = this.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ?
                    FreezingConfigGroup.FREEZE_EXTRA_DAMAGE_AMOUNT.getValue() :
                    FreezingConfigGroup.FREEZE_DAMAGE_AMOUNT.getValue();

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

    private void frostiful$addAttributeEffects(FreezableEntity freezable) {
        if (freezable.frostiful$getCurrentFrost() > 0) {
            EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (entityAttributeInstance == null) {
                return;
            }

            double slownessModifier = -0.05 * freezable.frostiful$getFrostProgress();
            entityAttributeInstance.addTemporaryModifier(
                    new EntityAttributeModifier(
                            FREEZING_SLOW_ID,
                            "Frostiful freezing slow",
                            slownessModifier,
                            EntityAttributeModifier.Operation.ADDITION
                    )
            );
        }
    }

    @Inject(
            method = "createLivingAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(FrostifulEntityAttributes.FROST_RESISTANCE, AttributeConfigGroup.BaseFrostResistance.BASE.getValue());
        attributeBuilder.add(FrostifulEntityAttributes.MAX_FROST, AttributeConfigGroup.BaseMaxFrost.BASE.getValue());
        cir.setReturnValue(attributeBuilder);
    }

}
