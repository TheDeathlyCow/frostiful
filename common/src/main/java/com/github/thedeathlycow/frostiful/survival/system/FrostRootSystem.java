package com.github.thedeathlycow.frostiful.survival.system;

import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.frostiful.registry.FEntityAttributes;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.tag.FDamageTypeTags;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class FrostRootSystem {
    private static final double ICE_BREAKER_FALLBACK_DAMAGE = 3.0;

    public static float getProgress(int ticksRemaining) {
        return (float) ticksRemaining / FrostifulConfigYACL.itemSettings().frostWandRootTime();
    }

    public static void afterDamage(
            LivingEntity provider,
            DamageSource source,
            float baseDamageTaken,
            float damageTaken,
            boolean blocked
    ) {
        int ticksRemaining = provider.getAttachedOrCreate(FDataAttachments.FROST_WAND_ROOT_TICKS);
        boolean breakRoot = !blocked
                && damageTaken > 0f
                && ticksRemaining > 0
                && !source.is(FDamageTypeTags.DOES_NOT_BREAK_ROOT);

        if (breakRoot) {
            breakRoot(provider, source.getEntity(), ticksRemaining);
        }
    }

    @Nullable
    public static Vec3 adjustMovementForRoot(MoverType type, Vec3 movement, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            int ticksRemaining = livingEntity.getAttachedOrCreate(FDataAttachments.FROST_WAND_ROOT_TICKS);

            if (ticksRemaining <= 0) {
                return null;
            }

            return switch (type) {
                case SELF, PLAYER ->
                        Vec3.ZERO.add(0, movement.y < 0 && !livingEntity.isNoGravity() ? movement.y : 0, 0);
                default -> null;
            };
        }

        return null;
    }

    public static boolean isRooted(LivingEntity entity) {
        return entity.getAttachedOrElse(FDataAttachments.FROST_WAND_ROOT_TICKS, 0) < 0;
    }

    public static void serverTick(LivingEntity provider) {
        if (!provider.hasAttached(FDataAttachments.FROST_WAND_ROOT_TICKS)) {
            return;
        }

        int ticksRemaining = provider.getAttachedOrThrow(FDataAttachments.FROST_WAND_ROOT_TICKS);

        if (provider.isSpectator()) {
            provider.removeAttached(FDataAttachments.FROST_WAND_ROOT_TICKS);
        } else if (provider.isOnFire()) {
            breakRoot(provider, null, ticksRemaining);
            provider.clearFire();
            ((EntityInvoker) provider).frostiful$invokePlayExtinguishSound();
        } else if (ticksRemaining > 0) { // dont bother decrementing if on fire since the ticks remaining will be set to 1
            decrementTicksRemaining(provider, ticksRemaining);
        }
    }

    private static void decrementTicksRemaining(LivingEntity provider, int ticksRemaining) {
        ticksRemaining = ticksRemaining - 1;
        if (ticksRemaining <= 0) {
            provider.removeAttached(FDataAttachments.FROST_WAND_ROOT_TICKS);
        } else {
            provider.setAttached(FDataAttachments.FROST_WAND_ROOT_TICKS, ticksRemaining);
        }
    }

    public static void breakRoot(LivingEntity provider, @Nullable Entity attacker, int ticksRemaining) {
        if (ticksRemaining > 0 && provider.level() instanceof ServerLevel serverWorld) {
            // set to 1 so the icebreaker enchantment can detect it
            provider.setAttached(FDataAttachments.FROST_WAND_ROOT_TICKS, 1);

            spawnShatterParticlesAndSound(provider, serverWorld);

            double damage = attacker instanceof LivingEntity livingAttacker
                    ? livingAttacker.getAttributeValue(FEntityAttributes.ICE_BREAKER_DAMAGE)
                    : ICE_BREAKER_FALLBACK_DAMAGE;

            DamageSource source = FDamageSources.getDamageSources(provider.level())
                    .frostiful$brokenIce(attacker);
            if (provider.hurtServer(serverWorld, source, (float) damage)) {
                dropAllBindingItems(provider);
            }
        }
    }

    public static boolean tryRootFromFrostWand(Entity provider, @Nullable Entity originalCaster) {
        if (canBeRootedBy(provider, originalCaster)) {
            provider.setAttached(FDataAttachments.FROST_WAND_ROOT_TICKS, FrostifulConfigYACL.itemSettings().frostWandRootTime());
            return true;
        }
        return false;
    }

    private static boolean canBeRootedBy(Entity provider, @Nullable Entity originalCaster) {
        if (provider.hasAttached(FDataAttachments.FROST_WAND_ROOT_TICKS)) {
            return false;
        }

        if (provider.is(FEntityTypeTags.ROOT_IMMUNE)) {
            return false;
        }

        if (provider.isAlliedTo(originalCaster)) {
            return false;
        }

        return !(provider instanceof LivingEntity livingEntity) || livingEntity.thermoo$canFreeze();
    }

    private static void dropAllBindingItems(LivingEntity victim) {
        TrinketsIntegration.getAllEquipped(victim).forEach(stack -> {
            if (EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) {
                victim.drop(stack.copy(), true, true);
                stack.setCount(0);
                victim.level().playSound(
                        null,
                        victim.getX(),
                        victim.getY(),
                        victim.getZ(),
                        FSoundEvents.ENTITY_BREAK_BINDING_CURSE,
                        victim.getSoundSource()
                );
            }
        });
    }

    private static void spawnShatterParticlesAndSound(LivingEntity victim, ServerLevel serverWorld) {
        ParticleOptions shatteredIce = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BLUE_ICE.defaultBlockState());

        serverWorld.sendParticles(
                shatteredIce,
                victim.getX(),
                victim.getY(),
                victim.getZ(),
                500,
                0.5, 1.0, 0.5,
                1.0
        );

        victim.level().playSound(
                null,
                victim.getX(),
                victim.getY(),
                victim.getZ(),
                SoundEvents.GLASS_BREAK,
                SoundSource.AMBIENT,
                1.0f, 0.75f
        );
    }
}