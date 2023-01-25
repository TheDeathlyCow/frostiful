package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.entity.ai.goal.PolarBearPlayFightGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearPlayFightMixin extends AnimalEntity {

    protected PolarBearPlayFightMixin(EntityType<? extends AnimalEntity> entityType, World world) {
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
        this.goalSelector.add(8, new PolarBearPlayFightGoal((PolarBearEntity) (Object) this, 8e-4f, 4e-3f));
    }
}
