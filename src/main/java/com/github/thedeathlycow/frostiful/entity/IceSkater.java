package com.github.thedeathlycow.frostiful.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public interface IceSkater {

    boolean frostiful$isIceSkating();

    boolean frostiful$isGliding();

    float MIN_SPEED = 0.2f;

    float SKATE_SLIPPERINESS = 1.075f;
    float SPRINT_SLIPPERINESS = 1.078f;
    float BRAKE_SLIPPERINESS = 1.0f;

    /**
     * Checks if the given entity is travelling at a fast enough speed to be considered moving, for the purpose
     * of ice skating effects like sound and particles. If the entity is effectively standing still (or moving very slowly)
     * these effects would be annoying and so should not be applied.
     *
     * @param entity The entity to check
     * @return Returns true if the speed of the entity is greater than or equal to {@link #MIN_SPEED}
     */
    static boolean isMoving(Entity entity) {
        return entity.getVelocity().lengthSquared() >= MIN_SPEED * MIN_SPEED;
    }

    /**
     * Gets the block slipperiness value for an entity that is ice skating. Value is used the same as in
     * {@link Block#getSlipperiness()}, but for entities that are skating on ice. Entities may have different slipperiness
     * values depending on what they are doing, such as sneaking or sprinting.
     *
     * Pre-condition: THe given entity is ice skating.
     *
     * @param entity The entity
     * @return Return the slipperiness value for the ice skating entity.
     */
    static float getSlipperinessForEntity(Entity entity) {
        float slipperiness;
        if (entity.isSneaking()) {
            slipperiness = BRAKE_SLIPPERINESS;
        } else if (entity.isSprinting()) {
            slipperiness = SPRINT_SLIPPERINESS;
        } else {
            slipperiness = SKATE_SLIPPERINESS;
        }

        return slipperiness;
    }

}
