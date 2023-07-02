package com.github.thedeathlycow.frostiful.mixins.entity.ice_skating;

import com.github.thedeathlycow.frostiful.entity.IceSkater;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntitySoundMixin {

    @Shadow public int age;
    @Shadow @Final protected Random random;

    @Shadow public abstract Vec3d getVelocity();

    @Inject(
            method = "playStepSounds",
            at = @At("HEAD"),
            cancellable = true
    )
    private void playSkateSlideSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        ci.cancel();

        final Entity instance = (Entity) (Object) this;

        if (instance instanceof IceSkater iceSkater) {

            boolean playGlideSound = iceSkater.frostiful$isIceSkating()
                    && IceSkater.isMoving(instance);

            if (playGlideSound) {
                float pitch = random.nextFloat() * 0.75f + 0.5f;
                instance.playSound(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_GLIDE, 1.0f, pitch);
            }


            boolean playSkateSound = playGlideSound
                    && instance.isSprinting()
                    && random.nextFloat() < 0.1f;

            if (playSkateSound) {
                float pitch = random.nextFloat() * 0.2f + 0.9f;
                instance.playSound(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_SKATE, 1.0f, pitch);
            }
        }



    }

}
