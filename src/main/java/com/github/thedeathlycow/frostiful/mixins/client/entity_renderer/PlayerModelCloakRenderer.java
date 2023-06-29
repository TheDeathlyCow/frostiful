package com.github.thedeathlycow.frostiful.mixins.client.entity_renderer;

import com.github.thedeathlycow.frostiful.item.FrostologyCloakItem;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
@Environment(EnvType.CLIENT)
public abstract class PlayerModelCloakRenderer extends PlayerEntity {


    public PlayerModelCloakRenderer(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(
            method = "getCapeTexture",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getFrostologyCloakTexture(CallbackInfoReturnable<Identifier> cir) {
        if (FrostologyCloakItem.isWornBy(this)) {
            cir.setReturnValue(FrostologyCloakItem.TEXTURE_ID);
        }
    }
}
