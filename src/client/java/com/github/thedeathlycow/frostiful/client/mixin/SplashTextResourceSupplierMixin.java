package com.github.thedeathlycow.frostiful.client.mixin;

import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;

@Mixin(SplashManager.class)
public class SplashTextResourceSupplierMixin {
    @Unique
    private static final SplashRenderer frostiful_DOWNLOAD_MUSESWIPR = new SplashRenderer(Component.translatable("frostiful.splash.download_museswipr"));

    @Inject(
            method = "getSplash",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getDownloadMuseSwiprSplash(CallbackInfoReturnable<SplashRenderer> cir) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DATE) == 21) {
            cir.setReturnValue(frostiful_DOWNLOAD_MUSESWIPR);
        }
    }

}
