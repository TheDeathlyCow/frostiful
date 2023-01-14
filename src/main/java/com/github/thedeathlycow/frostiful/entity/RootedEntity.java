package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.FNbtHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

public interface RootedEntity {

    String ROOTED_KEY = "RootedTicks";

    int frostiful$getRootedTicks();

    void frostiful$setRootedTicks(int amount);

    boolean frostiful$canRoot(@Nullable Entity attacker);

    default boolean frostiful$root(@Nullable Entity attacker) {
        if (this.frostiful$canRoot(attacker)) {
            FrostifulConfig config = Frostiful.getConfig();
            this.frostiful$setRootedTicks(config.combatConfig.getFrostWandRootTime());
            return true;
        }
        return false;
    }

    default void frostiful$breakRoot() {
        this.frostiful$setRootedTicks(0);
    }

    default boolean frostiful$isRooted() {
        return this.frostiful$getRootedTicks() > 0;
    }

    static void breakRootOnEntity(LivingEntity victim) {
        RootedEntity rootedEntity = (RootedEntity) victim;

        if (!victim.world.isClient && rootedEntity.frostiful$isRooted()) {
            rootedEntity.frostiful$breakRoot();
            playIceBreakEffects(victim, (ServerWorld) victim.world);
        }
    }

    static void frostiful$addRootedTicksToNbt(RootedEntity entity, NbtCompound nbt) {
        NbtCompound frostifulNbt = FNbtHelper.getOrDefault(
                nbt,
                FreezableEntity.FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE,
                NbtCompound::getCompound,
                FNbtHelper.NEW_COMPOUND_FALLBACK
        );

        if (entity.frostiful$isRooted()) {
            frostifulNbt.putInt(ROOTED_KEY, entity.frostiful$getRootedTicks());
        }
        nbt.put(FreezableEntity.FROSTIFUL_KEY, frostifulNbt);
    }

    static void frostiful$setRootedTicksFromNbt(RootedEntity entity, NbtCompound nbt) {
        int amount = 0;
        if (nbt.contains(FreezableEntity.FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE)) {
            NbtCompound frostifulNbt = nbt.getCompound(FreezableEntity.FROSTIFUL_KEY);
            if (frostifulNbt.contains(ROOTED_KEY, NbtElement.INT_TYPE)) {
                amount = frostifulNbt.getInt(ROOTED_KEY);
            }
        }

        entity.frostiful$setRootedTicks(amount);
    }

    private static void playIceBreakEffects(LivingEntity victim, ServerWorld serverWorld) {
        ParticleEffect shatteredIce = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.BLUE_ICE.getDefaultState());

        serverWorld.spawnParticles(
                shatteredIce,
                victim.getX(), victim.getY(), victim.getZ(),
                500,
                0.5, 1.0, 0.5,
                1.0
        );

        victim.world.playSound(
                null,
                victim.getBlockPos(),
                SoundEvents.BLOCK_GLASS_BREAK,
                SoundCategory.AMBIENT,
                1.0f, 0.75f
        );
    }
}
