package com.github.thedeathlycow.frostiful.client.mixin;

import com.github.thedeathlycow.frostiful.client.config.FrostifulClientConfig;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(Player.class)
@Environment(EnvType.CLIENT)
public abstract class DrippingWetPlayerMixin extends LivingEntity {

    private static final float SLOW_DRIP_MULTIPLIER = 2.0f;

    @Shadow
    protected boolean wasUnderwater;

    @Shadow
    public abstract boolean isSpectator();

    protected DrippingWetPlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
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
        Level world = this.level();
        if (world.isClientSide()) { // only show particles on client to save bandwidth

            // Scorchful does the same thing - let it handle this
            if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.SCORCHFUL_ID)) {
                return;
            }

            // spectators should not drip
            if (this.isSpectator()) {
                return;
            }

            // allow config to disable particles
            if (!FrostifulClientConfig.displaySettings().enableDripParticles()) {
                return;
            }

            // only spawn particles when out of water
            if (this.wasUnderwater) {
                return;
            }

            // Ensure that only players with non-zero wetness have particles
            // (I mostly just don't trust floats lol)
            if (!this.thermoo$isWet()) {
                return;
            }

            ThreadLocalRandom random = ThreadLocalRandom.current();

            // Spawn drip with probability proportional to wetness scale
            if (SLOW_DRIP_MULTIPLIER * random.nextFloat() < this.thermoo$getSoakedScale()) {

                AABB boundingBox = this.getBoundingBox();

                // pick random pos in player bounding box
                double x = boundingBox.min(Direction.Axis.X) + random.nextDouble(boundingBox.getXsize());
                double y = boundingBox.min(Direction.Axis.Y) + random.nextDouble(boundingBox.getYsize());
                double z = boundingBox.min(Direction.Axis.Z) + random.nextDouble(boundingBox.getZsize());

                world.addParticle(
                        ParticleTypes.FALLING_DRIPSTONE_WATER,
                        x, y, z,
                        0, 0, 0
                );
            }
        }
    }

}
