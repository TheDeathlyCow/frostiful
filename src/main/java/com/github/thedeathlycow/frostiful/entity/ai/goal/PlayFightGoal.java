package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class PlayFightGoal extends Goal {

    private static final TargetPredicate VALID_PLAYFIGHT_PREDICATE = TargetPredicate.createAttackable()
            .setBaseMaxDistance(8.0D)
            .ignoreVisibility();
    private static final UUID NO_DAMAGE_ID = UUID.fromString("ff0c233a-900a-4846-ac48-7d6a1330d6a8");
    private static final Multimap<EntityAttribute, EntityAttributeModifier> NO_DAMAGE_MODIFIERS =
            ImmutableMultimap.of(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(NO_DAMAGE_ID, "play fight no damage", -10000.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            );
    private static final int MAX_FIGHT_TIME = 30;

    protected final PathAwareEntity mob;
    @Nullable
    protected PathAwareEntity target;
    private final float adultChance;
    private final float babyChance;
    private int timer;

    public PlayFightGoal(PathAwareEntity mob, float adultChance, float babyChance) {
        this.mob = mob;
        this.adultChance = adultChance;
        this.babyChance = babyChance;
        this.target = null;
        this.timer = 0;
    }

    @Override
    public boolean canStart() {

        float chance = this.mob.isBaby() ? this.babyChance : this.adultChance;

        if (this.mob.getRandom().nextFloat() < chance) {
            this.target = this.findTarget();
            return this.target != null;
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.target != null
                && this.target.isAlive()
                && this.timer < MAX_FIGHT_TIME;
    }

    @Override
    public void stop() {
        this.target = null;
        this.timer = 0;
    }

    @Override
    public void tick() {
        if (this.target == null) {
            return;
        }

        this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
        this.mob.getNavigation().startMovingTo(this.target, 1.0f);

        this.timer++;
        if (this.timer >= this.getTickCount(MAX_FIGHT_TIME) && this.mob.squaredDistanceTo(this.target) < 9.0D) {
            this.playFight();
        }
    }

    protected void playFight() {
        this.mob.swingHand(Hand.MAIN_HAND);
        if (target != null) {
            this.target.lookAtEntity(this.mob, 30f, 30f);
            this.target.swingHand(Hand.MAIN_HAND);

            if (this.timer == this.getTickCount(MAX_FIGHT_TIME)) {
                this.mob.damage(DamageSource.GENERIC, 0.0f);
                this.target.damage(DamageSource.GENERIC, 0.0f);
            }
        }
    }


    /**
     * Finds the closest possible target to playfight with
     *
     * @return Returns a nullable pointer to the nearest path aware entity that this.mob can play
     * fight with.
     */
    @Nullable
    private PathAwareEntity findTarget() {

        World world = this.mob.world;
        List<? extends PathAwareEntity> candidates = world.getTargets(this.mob.getClass(), VALID_PLAYFIGHT_PREDICATE, this.mob, this.mob.getBoundingBox().expand(8.0));
        double closestEntityDistance = Double.POSITIVE_INFINITY;

        PathAwareEntity target = null;
        for (PathAwareEntity candidate : candidates) {
            double distance = this.mob.squaredAttackRange(candidate);
            if (this.canPlayfightWith(candidate) && distance < closestEntityDistance) {
                target = candidate;
                closestEntityDistance = distance;
            }
        }
        return target;
    }

    private boolean canPlayfightWith(PathAwareEntity candidate) {
        return candidate.getType() == this.mob.getType() && this.mob.isBaby() == candidate.isBaby();
    }
}


