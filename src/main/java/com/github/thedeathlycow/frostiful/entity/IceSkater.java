package com.github.thedeathlycow.frostiful.entity;

import net.minecraft.entity.Entity;

public interface IceSkater {

    boolean frostiful$isIceSkating();

    boolean frostiful$isGliding();

    float MIN_SPEED = 0.2f;

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

}
