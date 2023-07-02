package com.github.thedeathlycow.frostiful.entity;

import net.minecraft.entity.Entity;

public interface IceSkater {

    boolean frostiful$isIceSkating();

    boolean frostiful$isGliding();

    float MIN_SPEED = 0.2f;

    static boolean isMoving(Entity entity) {
        return entity.getVelocity().lengthSquared() >= MIN_SPEED * MIN_SPEED;
    }

}
