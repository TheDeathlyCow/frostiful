package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.IceSkater;
import com.github.thedeathlycow.frostiful.tag.FItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
@Debug(export = true)
public abstract class IceSkateMixin extends Entity implements IceSkater {


    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Shadow
    protected abstract float getVelocityMultiplier();

    public IceSkateMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Unique
    private static final TrackedData<Boolean> FROSTIFUL_IS_ICE_SKATING = DataTracker.registerData(
            IceSkateMixin.class,
            TrackedDataHandlerRegistry.BOOLEAN
    );


    @Override
    @Unique
    public boolean frostiful$isIceSkating() {
        return this.dataTracker.get(FROSTIFUL_IS_ICE_SKATING);
    }

    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void initIceSkateData(CallbackInfo ci) {
        this.dataTracker.startTracking(FROSTIFUL_IS_ICE_SKATING, false);
    }

    @Inject(
            method = "tickMovement",
            at = @At("TAIL")
    )
    private void updateIsIceSkating(CallbackInfo ci) {
        this.dataTracker.set(
                FROSTIFUL_IS_ICE_SKATING,
                this.getEquippedStack(EquipmentSlot.FEET).isIn(FItemTags.ICE_SKATES)
                        && this.getWorld().getBlockState(this.getVelocityAffectingPos()).isIn(BlockTags.ICE)
        );
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
            var config = Frostiful.getConfig().freezingConfig;

            if (this.isSneaking()) {
                slipperiness = config.getIceSkateBrakeSlipperiness();
            } else if (this.isSprinting()) {
                slipperiness = config.getIceSkateSprintSlipperiness();
            } else {
                slipperiness = config.getIceSkateSlipperiness();
            }
        }
        return slipperiness;
    }

//    @ModifyArg(
//            method = "applyMovementInput",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"
//            ),
//            index = 1
//    )
//    private Vec3d applyLookMovementIfIceSkating(Vec3d movementInput) {
//
//        if (!frostiful$isIceSkating) {
//            return movementInput;
//        }
//
//        double intermediateResult;
//
//        // Apply a limit to the falling speed
//        this.limitFallDistance();
//
//        // Get current entity's velocity and direction (as vector)
//        Vec3d velocity = movementInput;
//
//        if (this.isSneaking()) {
//            velocity.multiply(0.5);
//        }
//
//        Vec3d direction = this.getRotationVector();
//
//        // Calculate pitch in radians, direction's horizontal length, total length and some other parameters
//        float pitchInRadians = this.getPitch() * ((float)Math.PI / 180);
//        double directionHorizontalLength = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
//        double currentSpeed = velocity.horizontalLength();
//        double directionLength = direction.length();
//        double pitchCosSquared = Math.cos(pitchInRadians);
//        pitchCosSquared = pitchCosSquared * pitchCosSquared * Math.min(1.0, directionLength / 0.4);
//
//        if (velocity.y < 0.0 && directionHorizontalLength > 0.0) {
//            intermediateResult = velocity.y * -0.1 * pitchCosSquared;
//            velocity = velocity.add(direction.x * intermediateResult / directionHorizontalLength, intermediateResult, direction.z * intermediateResult / directionHorizontalLength);
//        }
//        if (pitchInRadians < 0.0f && directionHorizontalLength > 0.0) {
//            intermediateResult = currentSpeed * (double)(-MathHelper.sin(pitchInRadians)) * 0.04;
//            velocity = velocity.add(-direction.x * intermediateResult / directionHorizontalLength, intermediateResult * 3.2, -direction.z * intermediateResult / directionHorizontalLength);
//        }
//        if (directionHorizontalLength > 0.0) {
//            velocity = velocity.add((direction.x / directionHorizontalLength * currentSpeed - velocity.x) * 0.1, 0.0, (direction.z / directionHorizontalLength * currentSpeed - velocity.z) * 0.1);
//        }
//
//        return velocity;
//    }

}
