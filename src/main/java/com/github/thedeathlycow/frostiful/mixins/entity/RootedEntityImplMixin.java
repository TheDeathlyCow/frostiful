package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.enchantment.FrostifulEnchantmentHelper;
import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import com.github.thedeathlycow.frostiful.entity.damage.FrostifulDamageSource;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class RootedEntityImplMixin extends Entity implements RootedEntity {


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

    public int frostiful$getRootedTicks() {
        return this.dataTracker.get(FROZEN_TICKS);
    }

    public void frostiful$setRootedTicks(int amount) {
        this.dataTracker.set(FROZEN_TICKS, amount);
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
        if (!this.world.isClient && !this.isSpectator() && this.frostiful$isRooted()) {
            final LivingEntity instance = (LivingEntity) (Object) this;
            instance.setJumping(false);
            instance.sidewaysSpeed = 0.0F;
            instance.forwardSpeed = 0.0F;

            // remove tick
            int ticksLeft = this.frostiful$getRootedTicks();
            this.frostiful$setRootedTicks(--ticksLeft);
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
            method = "onAttacking",
            at = @At("HEAD")
    )
    private void breakRootOnAttack(Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity livingTarget) {
            final RootedEntity rootedTarget = (RootedEntity) livingTarget;
            final LivingEntity attacker = (LivingEntity) (Object) this;
            if (rootedTarget.frostiful$isRooted()) {
                if (target.world instanceof ServerWorld serverWorld) {
                    FrostifulConfig config = Frostiful.getConfig();

                    // calculate break damage
                    int iceBreakerLevel = FrostifulEnchantmentHelper.getIceBreakerLevel(attacker);
                    float damage = config.combatConfig.getBreakFrozenDamage();
                    damage += iceBreakerLevel * config.combatConfig.getIceBreakerDamagePerLevel();

                    // apply damage and remove frozen effect
                    livingTarget.damage(FrostifulDamageSource.frozenAttack(attacker), damage);
                    rootedTarget.frostiful$breakRoot();

                    // spawn particles
                    ParticleEffect shatteredIce = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.BLUE_ICE.getDefaultState());
                    serverWorld.spawnParticles(
                            shatteredIce,
                            target.getX(), target.getY(), target.getZ(),
                            500, 0.5, 1.0, 0.5, 1.0
                    );
                }
                target.world.playSound(
                        null,
                        target.getBlockPos(),
                        SoundEvents.BLOCK_GLASS_BREAK,
                        SoundCategory.AMBIENT,
                        1.0f, 0.75f
                );
            }
        }
    }
}


