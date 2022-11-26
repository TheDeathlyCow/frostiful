package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.enchantment.FEnchantmentHelper;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class RootedEntityImplMixin extends Entity implements RootedEntity {

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    private static final TrackedData<Integer> FROZEN_TICKS = DataTracker.registerData(RootedEntityImplMixin.class, TrackedDataHandlerRegistry.INTEGER);

    public RootedEntityImplMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void trackFrostData(CallbackInfo ci) {
        this.dataTracker.startTracking(FROZEN_TICKS, 0);
    }

    @Override
    public int frostiful$getRootedTicks() {
        return this.dataTracker.get(FROZEN_TICKS);
    }

    @Override
    public void frostiful$setRootedTicks(int amount) {
        this.dataTracker.set(FROZEN_TICKS, amount);
    }

    @Override
    public boolean frostiful$canRoot() {
        final LivingEntity instance = (LivingEntity) (Object) this;
        return ((FreezableEntity) instance).frostiful$canFreeze();
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
    private void tickRoot(CallbackInfo ci) {
        if (!this.isSpectator() && this.frostiful$isRooted()) {
            final LivingEntity instance = (LivingEntity) (Object) this;
            instance.setJumping(false);
            instance.sidewaysSpeed = 0.0F;
            instance.forwardSpeed = 0.0F;

            // remove tick
            int ticksLeft = this.frostiful$getRootedTicks();
            this.frostiful$setRootedTicks(--ticksLeft);

            if (!this.world.isClient && this.isOnFire()) {
                this.frostiful$breakRoot();
                this.extinguish();
                ServerWorld serverWorld = (ServerWorld) this.world;
                serverWorld.spawnParticles(
                        ParticleTypes.POOF,
                        this.getX(), this.getY(), this.getZ(),
                        100,
                        0.5, 0.5, 0.5,
                        0.1f
                );
                this.playExtinguishSound();
            }
        }
    }

    @Inject(
            method = "writeCustomDataToNbt",
            at = @At("TAIL")
    )
    private void writeRootedTicksToNbt(NbtCompound nbt, CallbackInfo ci) {
        RootedEntity.frostiful$addRootedTicksToNbt(this, nbt);
    }

    @Inject(
            method = "readCustomDataFromNbt",
            at = @At("TAIL")
    )
    private void readRootedTicksFromNbt(NbtCompound nbt, CallbackInfo ci) {
        RootedEntity.frostiful$setRootedTicksFromNbt(this, nbt);
    }

    @Inject(
            method = "modifyAppliedDamage",
            at = @At("TAIL"),
            cancellable = true
    )
    private void breakRootOnAttack(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (this.frostiful$isRooted()) {
            if (this.world instanceof ServerWorld serverWorld) {
                FrostifulConfig config = Frostiful.getConfig();
                float damage = config.combatConfig.getIceBreakerBaseDamage() + cir.getReturnValue();
                this.frostiful$breakRoot();

                ParticleEffect shatteredIce = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.BLUE_ICE.getDefaultState());
                serverWorld.spawnParticles(
                        shatteredIce,
                        this.getX(), this.getY(), this.getZ(),
                        500,
                        0.5, 1.0, 0.5,
                        1.0
                );
                this.world.playSound(
                        null,
                        this.getBlockPos(),
                        SoundEvents.BLOCK_GLASS_BREAK,
                        SoundCategory.AMBIENT,
                        1.0f, 0.75f
                );

                if (source.getAttacker() instanceof LivingEntity attacker) {
                    damage += FEnchantmentHelper.getIceBreakerBonusDamage(attacker);
                }

                cir.setReturnValue(damage);
            }
        }
    }
}


