package com.github.thedeathlycow.frostiful.mixins.client.sound;

import com.github.thedeathlycow.frostiful.client.sound.WindSoundInstance;
import com.github.thedeathlycow.frostiful.entity.WindEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class SpawnSoundMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(
            method = "playSpawnSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onPlaySpawnSound(Entity entity, CallbackInfo ci) {
        if (entity instanceof WindEntity windEntity) {
            this.client.getSoundManager().playNextTick(new WindSoundInstance(windEntity));
            ci.cancel();
        }
    }

}
