package com.github.thedeathlycow.frostiful.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class PlayFightTargetGoal extends ActiveTargetGoal<PolarBearEntity> {
    public PlayFightTargetGoal(MobEntity mob, Class<PolarBearEntity> targetClass, boolean checkVisibility) {
        super(mob, targetClass, checkVisibility);
    }

    public PlayFightTargetGoal(MobEntity mob, Class<PolarBearEntity> targetClass, boolean checkVisibility, Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, checkVisibility, targetPredicate);
    }

    public PlayFightTargetGoal(MobEntity mob, Class<PolarBearEntity> targetClass, boolean checkVisibility, boolean checkCanNavigate) {
        super(mob, targetClass, checkVisibility, checkCanNavigate);
    }

    public PlayFightTargetGoal(MobEntity mob, Class<PolarBearEntity> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, reciprocalChance, checkVisibility, checkCanNavigate, targetPredicate);
    }

    @Override
    public boolean canStart() {
        return !mob.isBaby() && super.canStart();
    }
}
