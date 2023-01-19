package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class IceGolemEntity extends HostileEntity {

    private static final TrackedData<Byte> ICE_GOLEM_FLAGS = DataTracker.registerData(IceGolemEntity.class, TrackedDataHandlerRegistry.BYTE);

    private static final int IS_CHARGING_FLAG_MASK = 0x1;

    @Nullable
    MobEntity owner;

    protected IceGolemEntity(EntityType<? extends IceGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createIceGolemAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(FEntityAttributes.MAX_FROST, 45.0);
    }

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(4, new ChargeTargetGoal());
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));

        this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge());
        this.targetSelector.add(2, new TrackOwnerTargetGoal());
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Nullable
    public MobEntity getOwner() {
        return owner;
    }

    public void setOwner(@Nullable MobEntity owner) {
        this.owner = owner;
    }

    public boolean isCharging() {
        return this.checkFlag(IS_CHARGING_FLAG_MASK);
    }

    public void setCharging(boolean charging) {
        this.setIceGolemFlag(IS_CHARGING_FLAG_MASK, charging);
    }

    private boolean checkFlag(int mask) {
        int i = this.dataTracker.get(ICE_GOLEM_FLAGS);
        return (i & mask) != 0;
    }

    private void setIceGolemFlag(int mask, boolean value) {
        int flags = this.dataTracker.get(ICE_GOLEM_FLAGS);
        if (value) {
            flags |= mask;
        } else {
            flags &= ~mask;
        }

        this.dataTracker.set(ICE_GOLEM_FLAGS, (byte)(flags & 0xff));
    }

    private class ChargeTargetGoal extends Goal {
        public ChargeTargetGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            LivingEntity target = IceGolemEntity.this.getTarget();

            boolean hasTarget = target != null
                    && target.isAlive()
                    && !IceGolemEntity.this.getMoveControl().isMoving()
                    && IceGolemEntity.this.random.nextInt(Goal.toGoalTicks(7)) == 0;

            if (hasTarget) {
                return IceGolemEntity.this.squaredDistanceTo(target) > 4.0;
            } else {
                return false;
            }
        }

        public boolean shouldContinue() {
            return IceGolemEntity.this.getMoveControl().isMoving()
                    && IceGolemEntity.this.isCharging()
                    && IceGolemEntity.this.getTarget() != null
                    && IceGolemEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity target = IceGolemEntity.this.getTarget();
            if (target != null) {
                Vec3d targetPos = target.getEyePos();
                IceGolemEntity.this.moveControl.moveTo(targetPos.x, targetPos.y, targetPos.z, 1.0);
            }

            IceGolemEntity.this.setCharging(true);
            IceGolemEntity.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0f, 1.0f);
        }

        public void stop() {
            IceGolemEntity.this.setCharging(false);
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity target = IceGolemEntity.this.getTarget();
            if (target != null) {
                if (IceGolemEntity.this.getBoundingBox().intersects(target.getBoundingBox())) {
                    IceGolemEntity.this.tryAttack(target);
                    IceGolemEntity.this.setCharging(false);
                } else {
                    double distanceToTarget = IceGolemEntity.this.squaredDistanceTo(target);
                    if (distanceToTarget < 9.0) {
                        Vec3d targetPos = target.getEyePos();
                        IceGolemEntity.this.moveControl.moveTo(targetPos.x, targetPos.y, targetPos.z, 1.0);
                    }
                }
            }
        }
    }

    public class TrackOwnerTargetGoal extends TrackTargetGoal {
        private final TargetPredicate targetPredicate = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal() {
            super(IceGolemEntity.this, false);
        }

        public boolean canStart() {
            return IceGolemEntity.this.owner != null
                    && IceGolemEntity.this.owner.getTarget() != null
                    && this.canTrack(IceGolemEntity.this.owner.getTarget(), this.targetPredicate);
        }

        public void start() {
            if (IceGolemEntity.this.owner != null) {
                IceGolemEntity.this.setTarget(IceGolemEntity.this.owner.getTarget());
            }
            super.start();
        }
    }
}
