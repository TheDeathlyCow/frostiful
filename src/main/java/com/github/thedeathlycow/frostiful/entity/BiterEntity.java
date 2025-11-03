package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.registry.FEntityAttributes;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.FStatusEffects;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class BiterEntity extends Monster {

    private static final EntityDataAccessor<Byte> ICE_GOLEM_FLAGS = SynchedEntityData.defineId(BiterEntity.class, EntityDataSerializers.BYTE);

    private static final int IS_CHARGING_FLAG_MASK = 0x1;

    public static final int ATTACK_TIME = 10;

    @Nullable
    Mob owner;

    private int attackTicks = 0;

    public final AnimationState bitingAnimation = new AnimationState();

    public BiterEntity(EntityType<? extends BiterEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createBiterAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(ThermooAttributes.MIN_TEMPERATURE, 45.0)
                .add(FEntityAttributes.ICE_BREAK_DAMAGE, 5.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.isAlive()) {
            if (this.attackTicks > 0) {
                this.attackTicks--;
            }
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel world, Entity target) {
        this.attackTicks = ATTACK_TIME;
        world.broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
        this.playAttackSound();
        if (target instanceof LivingEntity livingTarget && FComponents.FROST_WAND_ROOT_COMPONENT.get(livingTarget).isRooted()) {
            int maxAmplifier = Frostiful.getConfig().combatConfig.getBiterFrostBiteMaxAmplifier() + 1;

            livingTarget.addEffect(
                    new MobEffectInstance(
                            FStatusEffects.FROST_BITE,
                            20 * 15,
                            this.random.nextInt(maxAmplifier)
                    ),
                    this
            );
        }
        return super.doHurtTarget(world, target);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ICE_GOLEM_FLAGS, (byte) 0);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new TrackOwnerTargetGoal());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Nullable
    public Mob getOwner() {
        return owner;
    }

    public void setOwner(@Nullable Mob owner) {
        this.owner = owner;
    }

    public boolean isCharging() {
        return this.checkFlag(IS_CHARGING_FLAG_MASK);
    }

    public void setCharging(boolean charging) {
        this.setIceGolemFlag(IS_CHARGING_FLAG_MASK, charging);
    }

    public int getAttackTicks() {
        return attackTicks;
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == EntityEvent.START_ATTACKING) {
            this.attackTicks = ATTACK_TIME;
            this.playAttackSound();
            this.bitingAnimation.start(this.tickCount);
        } else {
            super.handleEntityEvent(status);
        }
    }

    @Override
    public void addAdditionalSaveData(ValueOutput writeView) {
        super.addAdditionalSaveData(writeView);
        writeView.putInt("AttackTicks", this.attackTicks);
    }

    @Override
    public void readAdditionalSaveData(ValueInput readView) {
        super.readAdditionalSaveData(readView);
        this.attackTicks = readView.getIntOr("AttackTicks", 0);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FSoundEvents.ENTITY_BITER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FSoundEvents.ENTITY_BITER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FSoundEvents.ENTITY_BITER_HURT;
    }

    private boolean checkFlag(int mask) {
        int i = this.entityData.get(ICE_GOLEM_FLAGS);
        return (i & mask) != 0;
    }

    private void setIceGolemFlag(int mask, boolean value) {
        int flags = this.entityData.get(ICE_GOLEM_FLAGS);
        if (value) {
            flags |= mask;
        } else {
            flags &= ~mask;
        }

        this.entityData.set(ICE_GOLEM_FLAGS, (byte) (flags & 0xff));
    }

    public void playAttackSound() {
        this.playSound(FSoundEvents.ENTITY_BITER_BITE, 1.0F, 1.0F);
    }

    private class ChargeTargetGoal extends Goal {
        public ChargeTargetGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = BiterEntity.this.getTarget();

            boolean hasTarget = target != null
                    && target.isAlive()
                    && !BiterEntity.this.getMoveControl().hasWanted()
                    && BiterEntity.this.random.nextInt(Goal.reducedTickDelay(7)) == 0;

            if (hasTarget) {
                return BiterEntity.this.distanceToSqr(target) > 4.0;
            } else {
                return false;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return BiterEntity.this.getMoveControl().hasWanted()
                    && BiterEntity.this.isCharging()
                    && BiterEntity.this.getTarget() != null
                    && BiterEntity.this.getTarget().isAlive();
        }

        @Override
        public void start() {
            LivingEntity target = BiterEntity.this.getTarget();
            if (target != null) {
                Vec3 targetPos = target.getEyePosition();
                BiterEntity.this.moveControl.setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 1.0);
            }

            BiterEntity.this.setCharging(true);
            BiterEntity.this.playSound(SoundEvents.VEX_CHARGE, 1.0f, 1.0f);
        }

        @Override
        public void stop() {
            BiterEntity.this.setCharging(false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = BiterEntity.this.getTarget();
            if (target != null) {

                double distanceToTarget = BiterEntity.this.distanceToSqr(target);
                if (distanceToTarget < 1.5) {
                    BiterEntity.this.doHurtTarget(getServerLevel(target), target);
                    BiterEntity.this.setCharging(false);
                } else if (distanceToTarget < 9.0) {
                    Vec3 targetPos = target.getEyePosition();
                    BiterEntity.this.moveControl.setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 1.0);
                }
            }
        }
    }

    public class TrackOwnerTargetGoal extends TargetGoal {
        private final TargetingConditions targetPredicate = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

        public TrackOwnerTargetGoal() {
            super(BiterEntity.this, false);
        }

        public boolean canUse() {
            return BiterEntity.this.owner != null
                    && BiterEntity.this.owner.getTarget() != null
                    && this.canAttack(BiterEntity.this.owner.getTarget(), this.targetPredicate);
        }

        public void start() {
            if (BiterEntity.this.owner != null) {
                BiterEntity.this.setTarget(BiterEntity.this.owner.getTarget());
            }
            super.start();
        }
    }
}
