package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(FallingBlockEntity.class)
public abstract class FallingIcicleMixin {

    @Shadow private BlockState blockState;

    @ModifyArg(
            method = "causeFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"
            ),
            index = 0
    )
    private Consumer<Entity> freezeVictimsOnFall(Consumer<Entity> par1) {

        if (this.blockState.getBlock() != FBlocks.ICICLE) {
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
