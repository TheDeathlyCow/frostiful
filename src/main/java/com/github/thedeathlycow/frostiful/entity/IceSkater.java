package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Block;

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

    static void frostiful$updateSkateWalkPenalityModifier(LivingEntity entity, boolean shouldBeSlowed) {
        AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            return;
        }

        movementSpeed.removeModifier(IceSkaterSettings.SKATE_WALK_PENALITY_ID);
        if (shouldBeSlowed) {
            movementSpeed.addTransientModifier(IceSkaterSettings.SKATE_WALK_PENALTY);
        }
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
        return entity.getDeltaMovement().lengthSqr() >= IceSkaterSettings.MIN_SPEED * IceSkaterSettings.MIN_SPEED;
    }

    /**
     * Gets the block slipperiness value for an entity that is ice skating. Value is used the same as in
     * {@link Block#getFriction()}, but for entities that are skating on ice. Entities may have different slipperiness
     * values depending on what they are doing, such as sneaking or sprinting.
     * <p>
     * Pre-condition: THe given entity is ice skating.
     *
     * @param entity The entity
     * @return Return the slipperiness value for the ice skating entity.
     */
    static float frostiful$getSlipperinessForEntity(Entity entity) {
        float slipperiness;
        if (entity.isShiftKeyDown()) {
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

        private static final ResourceLocation SKATE_WALK_PENALITY_ID = Frostiful.id("gameplay.skate_walk_penalty");

        private static final AttributeModifier SKATE_WALK_PENALTY = new AttributeModifier(
                SKATE_WALK_PENALITY_ID,
                -0.5,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        private static final Set<Pose> VALID_POSES_FOR_SKATING = EnumSet.of(Pose.STANDING, Pose.CROUCHING);

        private IceSkaterSettings() {
        }
    }
}
