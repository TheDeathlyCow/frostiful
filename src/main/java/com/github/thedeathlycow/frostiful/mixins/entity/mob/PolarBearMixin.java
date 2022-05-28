package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.entity.ai.goal.PlayFightTargetGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearMixin extends AnimalEntity {

    protected PolarBearMixin(EntityType<? extends AnimalEntity> entityType, World world) {
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
        this.targetSelector.add(7, new PlayFightTargetGoal(
                this,
                PolarBearEntity.class,
                10,
                true,
                false,
                (entity) -> entity.getType() == EntityType.POLAR_BEAR && !entity.isBaby()
        ));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        // do 0 damage to other polar bears
        Entity attacker = source.getAttacker();
        if (attacker != null && attacker.getType() == EntityType.POLAR_BEAR) {
            amount = 0.0f;
        }
        return super.damage(source, amount);
    }
}
