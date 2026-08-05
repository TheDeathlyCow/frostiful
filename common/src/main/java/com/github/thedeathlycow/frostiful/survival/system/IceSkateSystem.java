package com.github.thedeathlycow.frostiful.survival.system;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Set;

public final class IceSkateSystem {
    private static final float MIN_SPEED = 0.2f;
    private static final float SKATE_SLIPPERINESS = 1.075f;
    private static final float SPRINT_SLIPPERINESS = 1.078f;
    private static final float BRAKE_SLIPPERINESS = 1.0f;

    private static final Identifier SKATE_WALK_PENALITY_ID = Frostiful.id("gameplay.skate_walk_penalty");
    private static final AttributeModifier SKATE_WALK_PENALTY = new AttributeModifier(
            SKATE_WALK_PENALITY_ID,
            -0.5,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    );

    private static final Set<Pose> VALID_POSES_FOR_SKATING = EnumSet.of(Pose.STANDING, Pose.CROUCHING);

    public static boolean isIceSkating(LivingEntity entity) {
        return entity.getAttachedOrElse(FDataAttachments.WAS_ICE_SKATING, false);
    }

    /**
     * Checks if the given entity is travelling at a fast enough speed to be considered moving, for the purpose
     * of ice skating effects like sound and particles. If the entity is effectively standing still (or moving very slowly)
     * these effects would be annoying and so should not be applied.
     *
     * @param entity The entity to check
     * @return Returns true if the speed of the entity is greater than or equal to {@link #MIN_SPEED}
     */
    public static boolean isIceSkatingAndMoving(LivingEntity entity) {
        return isIceSkating(entity) && entity.getDeltaMovement().lengthSqr() >= MIN_SPEED * MIN_SPEED;
    }

    public static boolean isWearingSkates(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.FEET).is(FItemTags.ICE_SKATES);
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
    public static float getSlipperinessForEntity(Entity entity) {
        float slipperiness;

        if (entity.isShiftKeyDown()) {
            slipperiness = BRAKE_SLIPPERINESS;
        } else if (entity.isSprinting()) {
            slipperiness = SPRINT_SLIPPERINESS;
        } else {
            slipperiness = SKATE_SLIPPERINESS;
        }

        return slipperiness;
    }

    public static void aiStep(LivingEntity entity) {
        BlockState velocityAffectingBlock = entity.level().getBlockState(entity.getBlockPosBelowThatAffectsMyMovement());
        updateWasSkating(entity, velocityAffectingBlock);
        updateSlowness(entity, velocityAffectingBlock);

        if (IceSkateSystem.isIceSkatingAndMoving(entity)) {
            ((EntityInvoker) entity).frostiful$spawnSprintParticle();

            if (entity.isShiftKeyDown()) {
                applyStopEffects(entity, velocityAffectingBlock);
            }
        }
    }

    private static void updateWasSkating(LivingEntity entity, BlockState velocityAffectingBlock) {
        final boolean skating = velocityAffectingBlock.is(BlockTags.ICE)
                && VALID_POSES_FOR_SKATING.contains(entity.getPose())
                && isWearingSkates(entity);

        entity.setAttached(FDataAttachments.WAS_ICE_SKATING, skating);
    }

    private static void updateSlowness(LivingEntity entity, BlockState velocityAffectingBlock) {
        boolean shouldBeSlowed = entity.onGround()
                && IceSkateSystem.isWearingSkates(entity)
                && !velocityAffectingBlock.is(BlockTags.ICE);

        if (shouldBeSlowed != entity.getAttachedOrElse(FDataAttachments.WAS_SLOWED_BY_SKATES, false)) {
            updateSkateWalkPenalityModifier(entity, shouldBeSlowed);
        }

        entity.setAttached(FDataAttachments.WAS_SLOWED_BY_SKATES, true);
    }

    private static void applyStopEffects(LivingEntity entity, BlockState velocityAffectingBlock) {
        float pitch = entity.getRandom().nextFloat() * 0.75f + 0.5f;
        entity.playSound(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_STOP, 1.0f, pitch);

        Level level = entity.level();

        if (!level.isClientSide()) {
            return;
        }
        ParticleOptions iceParticles = new BlockParticleOption(ParticleTypes.BLOCK, velocityAffectingBlock);

        Vec3 velocity = entity.getDeltaMovement();
        Vec3 pos = entity.position();

        for (int i = 0; i < 25; i++) {
            level.addParticle(
                    iceParticles,
                    pos.x + entity.getRandom().nextFloat() - 0.5f,
                    pos.y + entity.getRandom().nextFloat() - 0.5f,
                    pos.z + entity.getRandom().nextFloat() - 0.5f,
                    velocity.x,
                    velocity.y,
                    velocity.z
            );
        }
    }

    private static void updateSkateWalkPenalityModifier(LivingEntity entity, boolean shouldBeSlowed) {
        AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            return;
        }

        movementSpeed.removeModifier(SKATE_WALK_PENALITY_ID);
        if (shouldBeSlowed) {
            movementSpeed.addTransientModifier(SKATE_WALK_PENALTY);
        }
    }

    private IceSkateSystem() {

    }
}