package com.github.thedeathlycow.frostiful.entity.attachment;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.AccessoriesIntegration;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import com.github.thedeathlycow.frostiful.registry.FrostifulEntityAttachments;
import com.github.thedeathlycow.frostiful.registry.FEntityAttributes;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.tag.FDamageTypeTags;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import com.google.common.base.Preconditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public class FrostWandRootComponent implements INBTSerializable<CompoundTag> {
    private static final String ROOTED_TICKS_KEY = "rooted_ticks";

    private final IAttachmentHolder provider;
    private int rootedTicks;

    public FrostWandRootComponent(IAttachmentHolder provider) {
        this(provider, 0);
    }

    private FrostWandRootComponent(IAttachmentHolder provider, int rootedTicks) {
        this.provider = provider;
        this.rootedTicks = rootedTicks;
    }

    public static FrostWandRootComponent get(LivingEntity entity) {
        return entity.getData(FrostifulEntityAttachments.FROST_WAND_ROOT_COMPONENT);
    }

    public static void afterDamage(
            LivingEntity provider,
            DamageSource source,
            float baseDamageTaken, float damageTaken,
            boolean blocked
    ) {
        FrostWandRootComponent component = get(provider);
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
            FrostWandRootComponent component = get(livingEntity);
            return component.adjustMovementForRoot(livingEntity, type, movement);
        }

        return null;
    }

    public void serverTick(LivingEntity providerEntity) {
        if (!FMLEnvironment.production) {
            Preconditions.checkArgument(this.provider == providerEntity, "Provided entity is not the attachment holder!");
        }

        if (providerEntity.isSpectator()) {
            this.setRootedTicks(0);
        } else if (this.isRooted()) {
            this.setRootedTicks(this.getRootedTicks() - 1);

            if (providerEntity.isOnFire()) {
                this.breakRoot(null);
                providerEntity.clearFire();
                ((EntityInvoker) providerEntity).frostiful$invokePlayExtinguishSound();
            }
        }
    }

    public float getRootProgress() {
        return (float) this.rootedTicks / Frostiful.getConfig().combatConfig.getFrostWandRootTime();
    }

    public void breakRoot(@Nullable Entity attacker) {
        if (!(this.provider instanceof LivingEntity providerEntity)) {
            return;
        }

        if (this.isRooted() && providerEntity.level() instanceof ServerLevel serverWorld) {
            this.setRootedTicks(1); // set to 1 so the icebreaker enchantment can detect it
            spawnShatterParticlesAndSound(providerEntity, serverWorld);

            double damage = attacker instanceof LivingEntity livingAttacker
                    ? livingAttacker.getAttributeValue(FEntityAttributes.ICE_BREAK_DAMAGE)
                    : Frostiful.getConfig().combatConfig.getIceBreakFallbackDamage();

            DamageSource source = FDamageSources.getDamageSources(providerEntity.level())
                    .frostiful$brokenIce(attacker);
            if (providerEntity.hurt(source, (float) damage)) {
                dropAllBindingItems(providerEntity);
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
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        var tag = new CompoundTag();

        if (this.rootedTicks != 0) {
            tag.putInt(ROOTED_TICKS_KEY, this.rootedTicks);
        }

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.rootedTicks = tag.contains(ROOTED_TICKS_KEY, Tag.TAG_INT)
                ? tag.getInt(ROOTED_TICKS_KEY)
                : 0;
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
            this.provider.syncData(FrostifulEntityAttachments.FROST_WAND_ROOT_COMPONENT);
        }
    }

    private boolean canBeRootedBy(@Nullable Entity originalCaster) {
        if (this.isRooted()) {
            return false;
        }

        if (!(this.provider instanceof LivingEntity providerEntity)) {
            return false;
        }

        if (providerEntity.getType().is(FEntityTypeTags.ROOT_IMMUNE)) {
            return false;
        }

        if (originalCaster != null && providerEntity.isAlliedTo(originalCaster)) {
            return false;
        }

        return providerEntity.thermoo$canFreeze();
    }

    @Nullable
    private Vec3 adjustMovementForRoot(LivingEntity providerEntity, MoverType type, Vec3 movement) {
        if (!this.isRooted()) {
            return null;
        }

        return switch (type) {
            case SELF, PLAYER -> Vec3.ZERO.add(0, movement.y < 0 && !providerEntity.isNoGravity() ? movement.y : 0, 0);
            default -> null;
        };
    }

    private static void dropAllBindingItems(LivingEntity victim) {
        AccessoriesIntegration.getAllEquipped(victim).forEach(stack -> {
            if (victim instanceof Player player && EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) {
                player.drop(stack.copy(), true, true);
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

    public static final class SyncHandler implements AttachmentSyncHandler<FrostWandRootComponent> {
        @Override
        public void write(RegistryFriendlyByteBuf buf, FrostWandRootComponent attachment, boolean initialSync) {
            buf.writeVarInt(attachment.getRootedTicks());
        }

        @Override
        public FrostWandRootComponent read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable FrostWandRootComponent previousValue) {
            return new FrostWandRootComponent(holder, buf.readVarInt());
        }
    }
}