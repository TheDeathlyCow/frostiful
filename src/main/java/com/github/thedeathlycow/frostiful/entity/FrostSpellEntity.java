package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrostSpellEntity extends SpellEntity {

    private static final double EFFECT_CLOUD_SIZE = 4.0;

    public FrostSpellEntity(World world, @Nullable LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(world, owner, velocityX, velocityY, velocityZ);
    }

    public FrostSpellEntity(World world, @Nullable LivingEntity owner, double velocityX, double velocityY, double velocityZ, double maxDistance) {
        super(world, owner, velocityX, velocityY, velocityZ, maxDistance);
    }

    protected FrostSpellEntity(EntityType<? extends SpellEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void applyEffectCloud() {
        if (this.isRemoved() || this.world.isClient) {
            return;
        }

        Box box = this.getBoundingBox().expand(EFFECT_CLOUD_SIZE, EFFECT_CLOUD_SIZE, EFFECT_CLOUD_SIZE);
        List<LivingEntity> targets = this.world.getNonSpectatingEntities(LivingEntity.class, box);
        for (var target : targets) {
            Entity owner = this.getOwner();
            if (owner == null || !target.getUuid().equals(owner.getUuid())) {
                this.applySingleTargetEffect(target);
            }
        }
        this.world.playSound(
                null,
                this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.AMBIENT,
                2.0f, 1.0f
        );

        ServerWorld serverWorld = (ServerWorld) this.world;

        serverWorld.spawnParticles(
                ParticleTypes.EXPLOSION,
                this.getX(), this.getY(), this.getZ(),
                10,
                2.0, 2.0, 2.0,
                0.3
        );

        this.discard();
    }

    @Override
    protected void applySingleTargetEffect(Entity target) {
        if (!target.world.isClient && target instanceof RootedEntity rootedEntity) {
            if (rootedEntity.frostiful$root(this.getOwner())) {
                target.world.playSound(
                        null,
                        target.getX(), target.getY(), target.getZ(),
                        FSoundEvents.ENTITY_FROST_SPELL_FREEZE, SoundCategory.AMBIENT,
                        1.0f, 1.0f
                );
            }
        }
    }
}
