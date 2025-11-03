package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractThrownPotion;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

@Mixin(AbstractThrownPotion.class)
public abstract class WaterPotionSoakingMixin extends ThrowableItemProjectile {
    public WaterPotionSoakingMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(
            method = "onHitAsWater",
            at = @At("TAIL")
    )
    private void soakEntitiesWithWaterbottle(ServerLevel world, CallbackInfo ci, @Local AABB box) {
        List<Player> players = level().getEntitiesOfClass(Player.class, box);
        FrostifulConfig config = Frostiful.getConfig();
        float soakPercent = config.freezingConfig.getSoakPercentFromWaterPotion();

        for (var player : players) {
            int soakAmount = (int) (player.thermoo$getMaxWetTicks() * soakPercent);
            soakAmount += player.thermoo$getWetTicks();
            player.thermoo$setWetTicks(soakAmount);
        }
    }
}
