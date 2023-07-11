package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.enchantment.FEnchantmentHelper;
import com.github.thedeathlycow.frostiful.enchantment.IceBreakerEnchantment;
import com.github.thedeathlycow.frostiful.util.FNbtHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface RootedEntity {

    String FROSTIFUL_KEY = "Frostiful";
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

    @Nullable
    static Vec3d getMovementWhenRooted(MovementType type, Vec3d movement, Entity entity) {

        if (!(entity instanceof RootedEntity rootedEntity && rootedEntity.frostiful$isRooted())) {
            return null;
        }

        return switch (type) {
            case SELF, PLAYER -> Vec3d.ZERO.add(0, movement.y < 0 && !entity.hasNoGravity() ? movement.y : 0, 0);
            default -> null;
        };
    }

    static void breakRootOnEntity(LivingEntity victim) {
        RootedEntity rootedEntity = (RootedEntity) victim;
        World world = victim.getWorld();
        if (!world.isClient && rootedEntity.frostiful$isRooted()) {
            rootedEntity.frostiful$breakRoot();
            IceBreakerEnchantment.spawnShatterParticlesAndSound(victim, (ServerWorld) world);
        }
    }

    static void frostiful$addRootedTicksToNbt(RootedEntity entity, NbtCompound nbt) {
        NbtCompound frostifulNbt = FNbtHelper.getOrDefault(
                nbt,
                FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE,
                NbtCompound::getCompound,
                FNbtHelper.NEW_COMPOUND_FALLBACK
        );

        if (entity.frostiful$isRooted()) {
            frostifulNbt.putInt(ROOTED_KEY, entity.frostiful$getRootedTicks());
        }
        nbt.put(FROSTIFUL_KEY, frostifulNbt);
    }

    static void frostiful$setRootedTicksFromNbt(RootedEntity entity, NbtCompound nbt) {
        int amount = 0;
        if (nbt.contains(FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE)) {
            NbtCompound frostifulNbt = nbt.getCompound(FROSTIFUL_KEY);
            if (frostifulNbt.contains(ROOTED_KEY, NbtElement.INT_TYPE)) {
                amount = frostifulNbt.getInt(ROOTED_KEY);
            }
        }

        entity.frostiful$setRootedTicks(amount);
    }


}
