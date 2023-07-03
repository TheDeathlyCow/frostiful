package com.github.thedeathlycow.frostiful.mixins.entity.ice_skating;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.IceSkater;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.frostiful.tag.FItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
@Debug(export = true)
public abstract class LivingEntityMovementMixin extends Entity implements IceSkater {


    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Shadow
    protected abstract float getVelocityMultiplier();

    @Shadow public abstract void setNoDrag(boolean noDrag);

    public LivingEntityMovementMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Unique
    private static final int FROSTIFUL_IS_SKATING_INDEX = 0;

    @Unique
    private static final int FROSTIFUL_IS_GLIDING_INDEX = 1;

    private int frostiful$lastStopSoundTime = 0;

    @Unique
    private static final TrackedData<Byte> FROSTIFUL_SKATE_FLAGS = DataTracker.registerData(
            LivingEntityMovementMixin.class,
            TrackedDataHandlerRegistry.BYTE
    );

    private boolean frostiful$getSkateFlag(int index) {
        return (this.dataTracker.get(FROSTIFUL_SKATE_FLAGS) & 1 << index) != 0;
    }

    private void frostiful$setSkateFlag(int index, boolean value) {
        byte data = this.dataTracker.get(FROSTIFUL_SKATE_FLAGS);
        if (value) {
            this.dataTracker.set(FROSTIFUL_SKATE_FLAGS, (byte) (data | 1 << index));
        } else {
            this.dataTracker.set(FROSTIFUL_SKATE_FLAGS, (byte) (data & ~(1 << index)));
        }
    }

    @Override
    @Unique
    public boolean frostiful$isIceSkating() {
        return frostiful$getSkateFlag(FROSTIFUL_IS_SKATING_INDEX);
    }

    @Override
    @Unique
    public boolean frostiful$isGliding() {
        return frostiful$getSkateFlag(FROSTIFUL_IS_GLIDING_INDEX);
    }

    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void initIceSkateData(CallbackInfo ci) {
        this.dataTracker.startTracking(FROSTIFUL_SKATE_FLAGS, (byte) 0);
    }

    @Inject(
            method = "tickMovement",
            at = @At("TAIL")
    )
    private void updateIsIceSkating(CallbackInfo ci) {

        BlockState velocityAffectingBlock = this.getWorld().getBlockState(this.getVelocityAffectingPos());

        this.frostiful$setSkateFlag(
                FROSTIFUL_IS_SKATING_INDEX,
                velocityAffectingBlock.isIn(BlockTags.ICE)
                        && this.getEquippedStack(EquipmentSlot.FEET).isIn(FItemTags.ICE_SKATES)
        );

        if (this.frostiful$isIceSkating() && IceSkater.isMoving(this)) {
            this.spawnSprintingParticles();
            if (this.isSneaking()) {
                this.applyStopEffects(velocityAffectingBlock);
            }
        }
    }

    @ModifyVariable(
            method = "travel",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/entity/LivingEntity;isOnGround()Z"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/block/Block;getSlipperiness()F"
                    )
            )
    )
    private float getSlipperinessForIceSkates(float slipperiness) {
        if (this.frostiful$isIceSkating()) {
            slipperiness = IceSkater.getSlipperinessForEntity(this);
        }
        return slipperiness;
    }

    @Inject(
            method = "applyMovementInput",
            at = @At("HEAD")
    )
    private void updateGliding(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        this.frostiful$setSkateFlag(FROSTIFUL_IS_GLIDING_INDEX, movementInput.horizontalLengthSquared() < 1e-3);
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
