package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.block.FBlocks;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(FallingBlockEntity.class)
public abstract class FallingIcicleMixin {

    @Shadow private BlockState block;

    @ModifyArg(
            method = "handleFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"
            ),
            index = 0
    )
    private Consumer<Entity> freezeVictimsOnFall(Consumer<Entity> par1) {

        if (this.block.getBlock() != FBlocks.ICICLE) {
            return par1;
        }

        return par1.andThen((entity) -> {
            if (entity instanceof LivingEntity livingEntity) {
                FrostifulConfig config = Frostiful.getConfig();
                livingEntity.thermoo$addTemperature(
                        config.icicleConfig.getIcicleCollisionFreezeAmount(), HeatingModes.ACTIVE
                );
            }
        });
    }

}
