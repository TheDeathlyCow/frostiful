package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.SoakingSettings;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AbstractThrownPotion.class)
public abstract class WaterPotionSoakingMixin extends ThrowableItemProjectile {
    public WaterPotionSoakingMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(
            method = "onHitAsWater",
            at = @At("TAIL")
    )
    private void soakEntitiesWithWaterbottle(ServerLevel world, CallbackInfo ci, @Local(name = "aabb") AABB box) {
        if (!FrostifulIntegrations.isModLoaded(FrostifulIntegrations.SCORCHFUL_ID)) {
            List<Player> players = level().getEntitiesOfClass(Player.class, box);
            SoakingSettings settings = FrostifulConfigYACL.soakingSettings();

            for (var player : players) {
                int soakAmount = settings.soakingFromSplashPotion(player.thermoo$getMaxWetTicks());
                soakAmount += player.thermoo$getWetTicks();
                player.thermoo$setWetTicks(soakAmount);
            }
        }
    }
}
