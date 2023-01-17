package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class FrostWyrmEntity extends HostileEntity {

    @Nullable
    MobEntity owner;

    private boolean isCharging;

    protected FrostWyrmEntity(EntityType<? extends FrostWyrmEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    public void tick() {
        this.noClip = true;
        super.tick();
        this.noClip = false;
        this.setNoGravity(true);
    }


    public static DefaultAttributeContainer.Builder createFrostWyrmAttributes() {
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

    public MobEntity getOwner() {
        return owner;
    }

    public void setOwner(MobEntity owner) {
        this.owner = owner;
    }

    public boolean isCharging() {
        return isCharging;
    }

    public void setCharging(boolean charging) {
        isCharging = charging;
    }

    private class ChargeTargetGoal extends Goal {
        public ChargeTargetGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            LivingEntity target = FrostWyrmEntity.this.getTarget();

            boolean hasTarget = target != null
                    && target.isAlive()
                    && !FrostWyrmEntity.this.getMoveControl().isMoving()
                    && FrostWyrmEntity.this.random.nextInt(Goal.toGoalTicks(7)) == 0;

            if (hasTarget) {
                return FrostWyrmEntity.this.squaredDistanceTo(target) > 4.0;
            } else {
                return false;
            }
        }

        public boolean shouldContinue() {
            return FrostWyrmEntity.this.getMoveControl().isMoving()
                    && FrostWyrmEntity.this.isCharging()
                    && FrostWyrmEntity.this.getTarget() != null
                    && FrostWyrmEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity target = FrostWyrmEntity.this.getTarget();
            if (target != null) {
                Vec3d targetPos = target.getEyePos();
                FrostWyrmEntity.this.moveControl.moveTo(targetPos.x, targetPos.y, targetPos.z, 1.0);
            }

            FrostWyrmEntity.this.setCharging(true);
            FrostWyrmEntity.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0f, 1.0f);
        }

        public void stop() {
            FrostWyrmEntity.this.setCharging(false);
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity target = FrostWyrmEntity.this.getTarget();
            if (target != null) {
                if (FrostWyrmEntity.this.getBoundingBox().intersects(target.getBoundingBox())) {
                    FrostWyrmEntity.this.tryAttack(target);
                    FrostWyrmEntity.this.setCharging(false);
                } else {
                    double distanceToTarget = FrostWyrmEntity.this.squaredDistanceTo(target);
                    if (distanceToTarget < 9.0) {
                        Vec3d targetPos = target.getEyePos();
                        FrostWyrmEntity.this.moveControl.moveTo(targetPos.x, targetPos.y, targetPos.z, 1.0);
                    }
                }
            }
        }
    }

    public class TrackOwnerTargetGoal extends TrackTargetGoal {
        private final TargetPredicate targetPredicate = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal() {
            super(FrostWyrmEntity.this, false);
        }

        public boolean canStart() {
            return FrostWyrmEntity.this.owner != null
                    && FrostWyrmEntity.this.owner.getTarget() != null
                    && this.canTrack(FrostWyrmEntity.this.owner.getTarget(), this.targetPredicate);
        }

        public void start() {
            if (FrostWyrmEntity.this.owner != null) {
                FrostWyrmEntity.this.setTarget(FrostWyrmEntity.this.owner.getTarget());
            }
            super.start();
        }
    }
}
