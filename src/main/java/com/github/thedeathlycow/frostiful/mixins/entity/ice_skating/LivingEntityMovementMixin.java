package com.github.thedeathlycow.frostiful.mixins.entity.ice_skating;

import com.github.thedeathlycow.frostiful.entity.IceSkater;
import com.github.thedeathlycow.frostiful.entity.component.LivingEntityComponents;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
@Debug(export = true)
public abstract class LivingEntityMovementMixin extends Entity implements IceSkater {


    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot var1);

    @Shadow
    protected abstract float getBlockSpeedFactor();

    public LivingEntityMovementMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Unique
    private static final int FROSTIFUL_IS_SKATING_INDEX = 0;

    @Unique
    private static final int FROSTIFUL_IS_GLIDING_INDEX = 1;

    @Unique
    private boolean frostiful$wasSlowed = false;

    @Unique
    private boolean frostiful$getSkateFlag(int index) {
        byte flags = FComponents.ENTITY_COMPONENTS.get(this).getSkateFlags();
        return (flags & 1 << index) != 0;
    }

    @Unique
    private void frostiful$setSkateFlag(int index, boolean value) {
        LivingEntityComponents component = FComponents.ENTITY_COMPONENTS.get(this);
        byte data = component.getSkateFlags();
        if (value) {
            component.setSkateFlags((byte) (data | 1 << index));
        } else {
            component.setSkateFlags((byte) (data & ~(1 << index)));
        }
    }

    @Override
    @Unique
    public boolean frostiful$isIceSkating() {
        return frostiful$getSkateFlag(FROSTIFUL_IS_SKATING_INDEX);
    }

    @Override
    @Unique
    public void frostiful$setSkating(boolean value) {
        this.frostiful$setSkateFlag(FROSTIFUL_IS_SKATING_INDEX, value);
    }

    @Override
    @Unique
    public boolean frostiful$isWearingSkates() {
        return this.getItemBySlot(EquipmentSlot.FEET).is(FItemTags.ICE_SKATES);
    }

    @Override
    @Unique
    public boolean frostiful$isGliding() {
        return frostiful$getSkateFlag(FROSTIFUL_IS_GLIDING_INDEX);
    }

    @Inject(
            method = "aiStep",
            at = @At("TAIL")
    )
    private void updateIsIceSkating(CallbackInfo ci) {

        Level world = this.level();
        ProfilerFiller profiler = world.getProfiler();
        profiler.push("frostiful.ice_skate_tick");

        BlockState velocityAffectingBlock = world.getBlockState(this.getBlockPosBelowThatAffectsMyMovement());

        this.frostiful$setSkating(
                velocityAffectingBlock.is(BlockTags.ICE)
                        && IceSkater.frostiful$isInSkatingPose(this)
                        && this.frostiful$isWearingSkates()
        );

        this.updateSlowness(velocityAffectingBlock);

        if (this.frostiful$isIceSkating() && IceSkater.frostiful$isMoving(this)) {
            this.spawnSprintParticle();
            if (this.isShiftKeyDown()) {
                this.applyStopEffects(velocityAffectingBlock);
            }
        }

        profiler.pop();
    }

    @ModifyVariable(
            method = "travel",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/world/entity/LivingEntity;onGround()Z"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/level/block/Block;getFriction()F"
                    )
            )
    )
    private float getSlipperinessForIceSkates(float slipperiness) {
        if (this.frostiful$isIceSkating()) {
            slipperiness = IceSkater.frostiful$getSlipperinessForEntity(this);
        }
        return slipperiness;
    }

    @Inject(
            method = "handleRelativeFrictionAndCalculateMovement",
            at = @At("HEAD")
    )
    private void updateGliding(Vec3 movementInput, float slipperiness, CallbackInfoReturnable<Vec3> cir) {
        this.frostiful$setSkateFlag(FROSTIFUL_IS_GLIDING_INDEX, movementInput.horizontalDistanceSqr() < 1e-3);
    }

    @Inject(
            method = "push(Lnet/minecraft/world/entity/Entity;)V",
            at = @At("HEAD")
    )
    private void damageOnLandingUponEntity(Entity entity, CallbackInfo ci) {

        if (!this.getItemBySlot(EquipmentSlot.FEET).is(FItemTags.ICE_SKATES)) {
            return;
        }

        if (entity instanceof LivingEntity target) {
            double attackerHeight = this.position().y;
            double targetEyeHeight = target.getEyePosition().y;
            if (attackerHeight > targetEyeHeight) {
                FDamageSources damageSources = FDamageSources.getDamageSources(this.level());
                target.hurt(damageSources.frostiful$iceSkate(this), 1.0f);
            }
        }
    }

    private void updateSlowness(BlockState velocityAffectingBlock) {

        boolean shouldBeSlowed = this.onGround()
                && this.frostiful$isWearingSkates()
                && !velocityAffectingBlock.is(BlockTags.ICE);

        if (shouldBeSlowed != frostiful$wasSlowed) {
            IceSkater.frostiful$updateSkateWalkPenalityModifier((LivingEntity) (Object) this, shouldBeSlowed);
        }

        frostiful$wasSlowed = shouldBeSlowed;
    }

    private void applyStopEffects(BlockState velocityAffectingBlock) {
        float pitch = this.random.nextFloat() * 0.75f + 0.5f;
        this.playSound(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_STOP, 1.0f, pitch);

        Level world = this.level();

        if (!world.isClientSide) {
            return;
        }
        ParticleOptions iceParticles = new BlockParticleOption(ParticleTypes.BLOCK, velocityAffectingBlock);

        Vec3 velocity = this.getDeltaMovement();
        Vec3 pos = this.position();

        for (int i = 0; i < 25; i++) {
            world.addParticle(
                    iceParticles,
                    pos.x + random.nextFloat() - 0.5f,
                    pos.y + random.nextFloat() - 0.5f,
                    pos.z + random.nextFloat() - 0.5f,
                    velocity.x,
                    velocity.y,
                    velocity.z
            );
        }
    }
}
