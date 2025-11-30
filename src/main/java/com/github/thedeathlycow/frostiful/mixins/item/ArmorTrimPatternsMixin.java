package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.registry.FArmorTrimPatterns;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.armortrim.TrimPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrimPatterns.class)
public class ArmorTrimPatternsMixin {


    @Inject(
            method = "bootstrap",
            at = @At("TAIL")
    )
    private static void bootstrapCustomTrims(BootstrapContext<TrimPattern> registry, CallbackInfo ci) {
        FArmorTrimPatterns.bootstrap(registry);
    }

}
