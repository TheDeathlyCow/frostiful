package com.github.thedeathlycow.frostiful.mixins.client.ice_skate_fx;

import com.github.thedeathlycow.frostiful.entity.IceSkater;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
@Debug(export = true)
public class ModelMixin<T extends LivingEntity> {

    @Shadow
    public boolean sneaking;
    @Shadow
    @Final
    public ModelPart leftLeg;
    @Shadow
    @Final
    public ModelPart rightLeg;

    @Shadow
    @Final
    public ModelPart leftArm;

    @Shadow
    @Final
    public ModelPart rightArm;

    private boolean frostiful$freezeLegs = false;

    private final float[] frostiful$LimbPitches = new float[4];

    private static final float FROSTIFUL_PITCH_REDUCTION = 0.75f;

    @Inject(
            method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",
            at = @At("HEAD")
    )
    private void collectSkaterData(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity instanceof IceSkater iceSkater) {

            boolean wasLegsFrozen = frostiful$freezeLegs;
            frostiful$freezeLegs = iceSkater.frostiful$isIceSkating()
                    && iceSkater.frostiful$isGliding();

            if (!wasLegsFrozen && frostiful$freezeLegs) {
                frostiful$LimbPitches[0] = FROSTIFUL_PITCH_REDUCTION * this.leftLeg.pitch;
                frostiful$LimbPitches[1] = FROSTIFUL_PITCH_REDUCTION * this.rightLeg.pitch;
                frostiful$LimbPitches[2] = FROSTIFUL_PITCH_REDUCTION * this.leftArm.pitch;
                frostiful$LimbPitches[3] = FROSTIFUL_PITCH_REDUCTION * this.rightArm.pitch;
            }

        }
    }

    @Inject(
            method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",
            at = @At("TAIL")
    )
    private void resetLegPitchIfGliding(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (frostiful$freezeLegs) {
            this.leftLeg.pitch = frostiful$LimbPitches[0];
            this.rightLeg.pitch = frostiful$LimbPitches[1];
            this.leftArm.pitch = frostiful$LimbPitches[2];
            this.rightArm.pitch = frostiful$LimbPitches[3];
        }
    }

}
