package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class SpellEntity extends ExplosiveProjectileEntity {

    private static final String AMPLIFIER_NBT_KEY = "EffectAmplifier";
    private static final String MAX_DISTANCE_NBT_KEY = "MaxDistance";
    private double maxDistance = Double.POSITIVE_INFINITY;
    @Nullable
    private Vec3d startPosition = null;

    public SpellEntity(World world, @Nullable LivingEntity owner, Vec3d velocity) {
        this(world, owner, velocity, Double.POSITIVE_INFINITY);
    }

    public SpellEntity(World world, @Nullable LivingEntity owner, Vec3d velocity, double maxDistance) {
        super(FEntityTypes.FROST_SPELL, owner, velocity, world);
        this.maxDistance = maxDistance;
    }

    protected SpellEntity(EntityType<? extends SpellEntity> entityType, World world) {
        super(entityType, world);
    }

    protected abstract void applyEffectCloud();

    protected abstract void applySingleTargetEffect(Entity target);

    public void tick() {
        super.tick();

        if (!getWorld().isClient && this.isAlive()) {
            if (this.startPosition == null) {
                this.startPosition = this.getPos();
            }

            double distTravelledSqd = this.startPosition.squaredDistanceTo(this.getPos());
            if (distTravelledSqd > this.maxDistance * this.maxDistance) {
                this.applyEffectCloud();
            }
        }
    }

    @Override
    public void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);
        if (!getWorld().isClient && this.isAlive()) {
            this.applySingleTargetEffect(hitResult.getEntity());
            this.applyEffectCloud();
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putDouble(MAX_DISTANCE_NBT_KEY, this.maxDistance);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.contains(MAX_DISTANCE_NBT_KEY, NbtElement.DOUBLE_TYPE)) {
            this.maxDistance = nbt.getDouble(MAX_DISTANCE_NBT_KEY);
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!getWorld().isClient) {
            this.applyEffectCloud();
        }
    }

    @Override
    protected ParticleEffect getParticleType() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected float getDrag() {
        return 1.0f;
    }

    @Override
    protected boolean isBurning() {
        return false;
    }


}
