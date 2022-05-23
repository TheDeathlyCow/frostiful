package com.github.thedeathlycow.frostiful.entity.effect;

import net.minecraft.block.HoneyBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class FrozenStatusEffect extends StatusEffect {
    public FrozenStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // apply the effect every tick
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // stop the entity from jumping
        Vec3d currentVelocity = entity.getVelocity();
        Vec3d newVelocity = currentVelocity;
        if (currentVelocity.y > -0.13 && !entity.isSpectator()) {
            newVelocity = new Vec3d(currentVelocity.x, -0.13, currentVelocity.z);
        }
        entity.setVelocity(newVelocity);
    }
}
