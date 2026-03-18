package com.github.thedeathlycow.frostiful.entity.frostologer;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;

class FrostWandCastGoal extends RangedAttackGoal {

    private final FrostologerEntity frostologerEntity;

    public FrostWandCastGoal(FrostologerEntity frostologer, double mobSpeed, int intervalTicks, float maxShootRange) {
        super(frostologer, mobSpeed, intervalTicks, maxShootRange);
        this.frostologerEntity = frostologer;
    }

    @Override
    public boolean canUse() {
        return super.canUse()
                && frostologerEntity.hasTarget()
                && !frostologerEntity.isTargetRooted()
                && frostologerEntity.getMainHandItem().is(FItems.FROST_WAND);
    }

    @Override
    public void start() {
        super.start();
        frostologerEntity.setAggressive(true);
        frostologerEntity.startUsingItem(InteractionHand.MAIN_HAND);
        this.startUsingFrostWand();
    }

    @Override
    public void stop() {
        super.stop();
        frostologerEntity.setAggressive(false);
        frostologerEntity.stopUsingItem();
        this.stopUsingFrostWand();
        if (frostologerEntity.isTargetRooted()) {
            int cooling = -FrostifulConfigYACL.combatConfig().getFrostologerCoolingFromFrostWandHit();
            frostologerEntity.thermoo$addTemperature(cooling);
        }
    }

    private void startUsingFrostWand() {
        frostologerEntity.playSound(
                FSoundEvents.ITEM_FROST_WAND_PREPARE_CAST,
                1.0f, 1.0f
        );
        frostologerEntity.getEntityData().set(FrostologerEntity.IS_USING_FROST_WAND, true);
    }

    private void stopUsingFrostWand() {
        frostologerEntity.getEntityData().set(FrostologerEntity.IS_USING_FROST_WAND, false);
    }
}