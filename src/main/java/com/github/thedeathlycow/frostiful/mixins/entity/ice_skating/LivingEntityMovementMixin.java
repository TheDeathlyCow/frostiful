package com.github.thedeathlycow.frostiful.mixins.entity.ice_skating;

import com.github.thedeathlycow.frostiful.entity.IceSkater;
import com.github.thedeathlycow.frostiful.entity.component.LivingEntityComponents;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMovementMixin extends Entity implements IceSkater {


    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Shadow
    protected abstract float getVelocityMultiplier();

    public LivingEntityMovementMixin(EntityType<?> type, World world) {
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
        return this.getEquippedStack(EquipmentSlot.FEET).isIn(FItemTags.ICE_SKATES);
    }

    @Override
    @Unique
    public boolean frostiful$isGliding() {
        return frostiful$getSkateFlag(FROSTIFUL_IS_GLIDING_INDEX);
    }

    @Inject(
            method = "tickMovement",
            at = @At("TAIL")
    )
    private void updateIsIceSkating(CallbackInfo ci) {
        World world = this.getWorld();
        Profiler profiler = Profilers.get();
        profiler.push("frostiful.ice_skate_tick");

        BlockState velocityAffectingBlock = world.getBlockState(this.getVelocityAffectingPos());

        this.frostiful$setSkating(
                velocityAffectingBlock.isIn(BlockTags.ICE)
                        && IceSkater.frostiful$isInSkatingPose(this)
                        && this.frostiful$isWearingSkates()
        );

        this.updateSlowness(velocityAffectingBlock);

        if (this.frostiful$isIceSkating() && IceSkater.frostiful$isMoving(this)) {
            this.spawnSprintingParticles();
            if (this.isSneaking()) {
                this.applyStopEffects(velocityAffectingBlock);
            }
        }

        profiler.pop();
    }

    @WrapOperation(
            method = "travelMidAir",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getSlipperiness()F"
            )
    )
    private float setSlipperinessForIceSkates(Block instance, Operation<Float> original) {
        if (this.frostiful$isIceSkating()) {
            return IceSkater.frostiful$getSlipperinessForEntity(this);
        }
        return original.call(instance);
    }

    @Inject(
            method = "applyMovementInput",
            at = @At("HEAD")
    )
    private void updateGliding(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        this.frostiful$setSkateFlag(FROSTIFUL_IS_GLIDING_INDEX, movementInput.horizontalLengthSquared() < 1e-3);
    }

    @Inject(
            method = "pushAwayFrom",
            at = @At("HEAD")
    )
    private void damageOnLandingUponEntity(Entity entity, CallbackInfo ci) {

        if (!this.getEquippedStack(EquipmentSlot.FEET).isIn(FItemTags.ICE_SKATES)) {
            return;
        }

        if (entity instanceof LivingEntity target && target.getWorld() instanceof ServerWorld world) {
            double attackerHeight = this.getPos().y;
            double targetEyeHeight = target.getEyePos().y;
            if (attackerHeight > targetEyeHeight) {
                FDamageSources damageSources = FDamageSources.getDamageSources(this.getWorld());
                target.damage(world, damageSources.frostiful$iceSkate(this), 1.0f);
            }
        }
    }

    private void updateSlowness(BlockState velocityAffectingBlock) {

        boolean shouldBeSlowed = this.isOnGround()
                && this.frostiful$isWearingSkates()
                && !velocityAffectingBlock.isIn(BlockTags.ICE);

        if (shouldBeSlowed != frostiful$wasSlowed) {
            IceSkater.frostiful$updateSkateWalkPenalityModifier((LivingEntity) (Object) this, shouldBeSlowed);
        }

        frostiful$wasSlowed = shouldBeSlowed;
    }

    private void applyStopEffects(BlockState velocityAffectingBlock) {
        float pitch = this.random.nextFloat() * 0.75f + 0.5f;
        this.playSound(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_STOP, 1.0f, pitch);

        World world = this.getWorld();

        if (!world.isClient) {
            return;
        }
        ParticleEffect iceParticles = new BlockStateParticleEffect(ParticleTypes.BLOCK, velocityAffectingBlock);

        Vec3d velocity = this.getVelocity();
        Vec3d pos = this.getPos();

        for (int i = 0; i < 25; i++) {
            world.addParticleClient(
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
