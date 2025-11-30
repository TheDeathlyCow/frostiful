package com.github.thedeathlycow.frostiful.entity.frostologer;

import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;

class FrostWandAttackGoal extends OcelotAttackGoal {
    private final FrostologerEntity frostologerEntity;

    public FrostWandAttackGoal(FrostologerEntity frostologerEntity) {
        super(frostologerEntity);
        this.frostologerEntity = frostologerEntity;
    }

    @Override
    public boolean canUse() {
        return frostologerEntity.isTargetRooted()
                && super.canUse();
    }

}