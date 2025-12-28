package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.entity.ai.goal.OcelotPlayFightGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Ocelot.class)
public abstract class OcelotPlayFightMixin extends Animal {
    protected OcelotPlayFightMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(
            method = "registerGoals",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",
                    ordinal = 0
            )
    )
    private void addPlayfightGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(8, new OcelotPlayFightGoal((Ocelot) (Object) this, 8e-4f, 5e-3f));
    }
}
