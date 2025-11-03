package com.github.thedeathlycow.frostiful.client.mixin.gui;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.HeartType.class)
public abstract class HeartTypeMixin {
    @ModifyReturnValue(
            method = "forPlayer",
            at = @At("TAIL")
    )
    private static Gui.HeartType frostifulIsFrozen(Gui.HeartType original, Player player) {
        return original == Gui.HeartType.NORMAL && player.thermoo$getTemperatureScale() <= -0.99f
                ? Gui.HeartType.FROZEN
                : original;
    }
}
