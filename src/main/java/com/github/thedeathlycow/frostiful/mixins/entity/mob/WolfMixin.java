package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.entity.ai.goal.PlayFightGoal;
import com.github.thedeathlycow.frostiful.entity.ai.goal.PolarBearPlayFightGoal;
import com.github.thedeathlycow.frostiful.entity.ai.goal.WolfPlayfightGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntity.class)
public abstract class WolfMixin extends TameableEntity {


    protected WolfMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "initGoals",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
                    ordinal = 0
            )
    )
    private void addPlayFightGoal(CallbackInfo ci) {
        this.goalSelector.add(8, new WolfPlayfightGoal((WolfEntity) (Object) this, 8e-4f, 5e-3f));
    }
}
