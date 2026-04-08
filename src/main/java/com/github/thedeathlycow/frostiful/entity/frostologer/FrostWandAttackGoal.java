package com.github.thedeathlycow.frostiful.entity.frostologer;

import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;

class FrostWandAttackGoal extends OcelotAttackGoal {
    private final Frostologer frostologer;

    public FrostWandAttackGoal(Frostologer frostologer) {
        super(frostologer);
        this.frostologer = frostologer;
    }

    @Override
    public boolean canUse() {
        return frostologer.isTargetRooted()
                && super.canUse();
    }

}