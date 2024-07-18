package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(PotionEntity.class)
public abstract class WaterPotionSoakingMixin extends ThrownItemEntity {


    public WaterPotionSoakingMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "applyWater",
            at = @At("TAIL"),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private void soakEntitiesWithWaterbottle(CallbackInfo ci, Box box) {
        List<PlayerEntity> players = getWorld().getNonSpectatingEntities(PlayerEntity.class, box);
        FrostifulConfig config = Frostiful.getConfig();
        float soakPercent = config.freezingConfig.getSoakPercentFromWaterPotion();

        for (var player : players) {
            int soakAmount = (int) (player.thermoo$getMaxWetTicks() * soakPercent);
            soakAmount += player.thermoo$getWetTicks();
            player.thermoo$setWetTicks(soakAmount);
        }
    }

}
