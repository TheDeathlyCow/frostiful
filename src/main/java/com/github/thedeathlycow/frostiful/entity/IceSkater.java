package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.tag.FItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

import java.util.EnumSet;
import java.util.Set;

public interface IceSkater {

    boolean frostiful$isIceSkating();

    boolean frostiful$isGliding();

    void frostiful$setSkating(boolean value);

    boolean frostiful$isWearingSkates();

    static boolean frostiful$isInSkatingPose(Entity entity) {
        return IceSkaterSettings.VALID_POSES_FOR_SKATING.contains(entity.getPose());
    }

    /**
     * Checks if the given entity is travelling at a fast enough speed to be considered moving, for the purpose
     * of ice skating effects like sound and particles. If the entity is effectively standing still (or moving very slowly)
     * these effects would be annoying and so should not be applied.
     *
     * @param entity The entity to check
     * @return Returns true if the speed of the entity is greater than or equal to {@link IceSkaterSettings#MIN_SPEED}
     */
    static boolean frostiful$isMoving(Entity entity) {
        return entity.getVelocity().lengthSquared() >= IceSkaterSettings.MIN_SPEED * IceSkaterSettings.MIN_SPEED;
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
    static float frostiful$getSlipperinessForEntity(Entity entity) {
        float slipperiness;
        if (entity.isSneaking()) {
            slipperiness = IceSkaterSettings.BRAKE_SLIPPERINESS;
        } else if (entity.isSprinting()) {
            slipperiness = IceSkaterSettings.SPRINT_SLIPPERINESS;
        } else {
            slipperiness = IceSkaterSettings.SKATE_SLIPPERINESS;
        }

        return slipperiness;
    }


    class IceSkaterSettings {
        private static final float MIN_SPEED = 0.2f;
        private static final float SKATE_SLIPPERINESS = 1.075f;
        private static final float SPRINT_SLIPPERINESS = 1.078f;
        private static final float BRAKE_SLIPPERINESS = 1.0f;

        private static final Set<EntityPose> VALID_POSES_FOR_SKATING = EnumSet.of(EntityPose.STANDING, EntityPose.CROUCHING);

        private IceSkaterSettings() {}
    }
}
