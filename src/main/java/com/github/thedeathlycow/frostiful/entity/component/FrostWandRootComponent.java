package com.github.thedeathlycow.frostiful.entity.component;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.registry.FEntityAttributes;
import com.github.thedeathlycow.frostiful.registry.tag.FDamageTypeTags;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class FrostWandRootComponent implements Component, AutoSyncedComponent, ServerTickingComponent {

    private static final String ROOTED_TICKS_KEY = "rooted_ticks";

    private final LivingEntity provider;

    private int rootedTicks;

    public FrostWandRootComponent(LivingEntity provider) {
        this.provider = provider;
    }

    public static void afterDamage(
            LivingEntity provider,
            DamageSource source,
            float baseDamageTaken, float damageTaken,
            boolean blocked
    ) {
        FrostWandRootComponent component = FComponents.FROST_WAND_ROOT_COMPONENT.get(provider);
        boolean breakRoot = !blocked
                && damageTaken > 0f
                && !source.is(FDamageTypeTags.DOES_NOT_BREAK_ROOT)
                && component.isRooted();

        if (breakRoot) {
            component.breakRoot(source.getEntity());
        }
    }

    @Nullable
    public static Vec3 adjustMovementForRoot(MoverType type, Vec3 movement, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            FrostWandRootComponent component = FComponents.FROST_WAND_ROOT_COMPONENT.get(livingEntity);
            return component.adjustMovementForRoot(type, movement);
        }

        return null;
    }

    @Override
    public void serverTick() {
        if (provider.isSpectator()) {
            this.setRootedTicks(0);
        } else if (this.isRooted()) {
            this.setRootedTicks(this.getRootedTicks() - 1);

            if (provider.isOnFire()) {
                this.breakRoot(null);
                provider.clearFire();
                ((EntityInvoker) provider).frostiful$invokePlayExtinguishSound();
            }
        }
    }

    public float getRootProgress() {
        return (float) this.rootedTicks / Frostiful.getConfig().combatConfig.getFrostWandRootTime();
    }

    public void breakRoot(@Nullable Entity attacker) {
        if (this.isRooted() && provider.level() instanceof ServerLevel serverWorld) {
            this.setRootedTicks(1); // set to 1 so the icebreaker enchantment can detect it
            spawnShatterParticlesAndSound(provider, serverWorld);

            double damage = attacker instanceof LivingEntity livingAttacker
                    ? livingAttacker.getAttributeValue(FEntityAttributes.ICE_BREAK_DAMAGE)
                    : Frostiful.getConfig().combatConfig.getIceBreakFallbackDamage();

            DamageSource source = FDamageSources.getDamageSources(provider.level())
                    .frostiful$brokenIce(attacker);
            if (provider.hurtServer(serverWorld, source, (float) damage)) {
                dropAllBindingItems(provider);
            }
        }
    }

    public boolean tryRootFromFrostWand(@Nullable Entity originalCaster) {
        if (this.canBeRootedBy(originalCaster)) {
            this.setRootedTicks(Frostiful.getConfig().combatConfig.getFrostWandRootTime());
            return true;
        }
        return false;
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer recipient) {
        buf.writeVarInt(this.rootedTicks);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        this.rootedTicks = buf.readVarInt();
    }

    @Override
    public void readData(ValueInput readView) {
        this.rootedTicks = readView.getIntOr(ROOTED_TICKS_KEY, 0);
    }

    @Override
    public void writeData(ValueOutput writeView) {
        if (this.rootedTicks != 0) {
            writeView.putInt(ROOTED_TICKS_KEY, this.rootedTicks);
        }
    }

    public boolean isRooted() {
        return this.getRootedTicks() > 0;
    }

    public int getRootedTicks() {
        return rootedTicks;
    }

    public void setRootedTicks(int rootedTicks) {
        if (this.rootedTicks != rootedTicks) {
            this.rootedTicks = rootedTicks;
            FComponents.FROST_WAND_ROOT_COMPONENT.sync(this.provider);
        }
    }

    private boolean canBeRootedBy(@Nullable Entity originalCaster) {
        if (this.isRooted()) {
            return false;
        }

        if (provider.getType().is(FEntityTypeTags.ROOT_IMMUNE)) {
            return false;
        }

        if (originalCaster != null && provider.isAlliedTo(originalCaster)) {
            return false;
        }

        return provider.thermoo$canFreeze();
    }

    @Nullable
    private Vec3 adjustMovementForRoot(MoverType type, Vec3 movement) {
        if (!this.isRooted()) {
            return null;
        }

        return switch (type) {
            case SELF, PLAYER -> Vec3.ZERO.add(0, movement.y < 0 && !provider.isNoGravity() ? movement.y : 0, 0);
            default -> null;
        };
    }

    private static void dropAllBindingItems(LivingEntity victim) {
        TrinketsIntegration.getAllEquipped(victim).forEach(stack -> {
            if (EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) {
                victim.drop(stack.copy(), true, true);
                stack.setCount(0);
            }
        });
    }

    private static void spawnShatterParticlesAndSound(LivingEntity victim, ServerLevel serverWorld) {
        ParticleOptions shatteredIce = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BLUE_ICE.defaultBlockState());

        serverWorld.sendParticles(
                shatteredIce,
                victim.getX(), victim.getY(), victim.getZ(),
                500,
                0.5, 1.0, 0.5,
                1.0
        );

        victim.level().playSound(
                null,
                victim.blockPosition(),
                SoundEvents.GLASS_BREAK,
                SoundSource.AMBIENT,
                1.0f, 0.75f
        );
    }
}