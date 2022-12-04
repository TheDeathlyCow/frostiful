package com.github.thedeathlycow.frostiful.mixins.client;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.SoakableEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(PlayerEntity.class)
@Environment(EnvType.CLIENT)
public abstract class DrippingWetPlayerMixin extends LivingEntity {

    private static final float SLOW_DRIP_MULTIPLIER = 2.0f;

    @Shadow protected boolean isSubmergedInWater;

    protected DrippingWetPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * Renders water particles on players that are wet. The chance of a drip spawning is the same
     * as the player's wetness scale.
     * This is done on the client side to avoid sending unnecessary packets and save bandwidth.
     *
     * @param ci Callback info
     */
    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void dripParticles(CallbackInfo ci) {
        if (this.world.isClient) { // only show particles on client to save bandwidth

            // allow config to disable particles
            if (!Frostiful.getConfig().clientConfig.renderDripParticles()) {
                return;
            }

            // only spawn particles when out of water
            if (this.isSubmergedInWater || this.isWet()) {
                return;
            }
            SoakableEntity soakableEntity = (SoakableEntity) this;

            // Ensure that only players with non-zero wetness have particles
            // (I mostly just don't trust floats lol)
            if (!soakableEntity.frostiful$isWet()) {
                return;
            }

            ThreadLocalRandom random = ThreadLocalRandom.current();

            // Spawn drip with probability equal to wetness scale
            if (SLOW_DRIP_MULTIPLIER * random.nextFloat() < soakableEntity.frostiful$getWetnessScale()) {

                World world = this.getWorld();
                Box boundingBox = this.getBoundingBox();

                // pick random pos in player bounding box
                double x = boundingBox.getMin(Direction.Axis.X) + random.nextDouble(boundingBox.getXLength());
                double y = boundingBox.getMin(Direction.Axis.Y) + random.nextDouble(boundingBox.getYLength());
                double z = boundingBox.getMin(Direction.Axis.Z) + random.nextDouble(boundingBox.getZLength());

                world.addParticle(
                        ParticleTypes.FALLING_DRIPSTONE_WATER,
                        x, y, z,
                        0, 0, 0
                );
            }
        }
    }

}
