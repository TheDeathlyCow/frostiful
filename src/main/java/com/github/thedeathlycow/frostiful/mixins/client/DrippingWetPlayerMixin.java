package com.github.thedeathlycow.frostiful.mixins.client;

import com.github.thedeathlycow.frostiful.entity.SoakableEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(PlayerEntity.class)
@Environment(EnvType.CLIENT)
public abstract class DrippingWetPlayerMixin extends LivingEntity {

    protected DrippingWetPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void dripParticles(CallbackInfo ci) {
        if (this.world.isClient) { // only render on client to save bandwidth

            SoakableEntity soakableEntity = (SoakableEntity) this;

            if (soakableEntity.frostiful$getWetnessScale() > 0.5f) {
                World world = this.getWorld();
                Box boundingBox = this.getBoundingBox();
                ThreadLocalRandom random = ThreadLocalRandom.current();
                Vec3d pos = this.getPos();

                double x = pos.x + random.nextDouble(boundingBox.getXLength());
                double y = pos.y + random.nextDouble(boundingBox.getYLength());
                double z = pos.z + random.nextDouble(boundingBox.getZLength());

                world.addParticle(
                        ParticleTypes.DRIPPING_WATER,
                        x, y, z,
                        0, 0, 0
                );
            }
        }
    }

}
