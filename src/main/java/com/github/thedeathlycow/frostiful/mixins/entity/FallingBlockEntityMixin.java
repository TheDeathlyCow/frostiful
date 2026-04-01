package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    @Shadow private BlockState blockState;

    public FallingBlockEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

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
                livingEntity.thermoo$addTemperature(
                        FrostifulConfigYACL.temperatureSourceSettings().icicleCollisionTemperatureChange(),
                        livingEntity.level().thermoo$temperatureSources().create(
                                TemperatureSources.ACTIVE,
                                this
                        )
                );
            }
        });
    }
}
