package com.github.thedeathlycow.frostiful.client.mixin;

import com.github.thedeathlycow.frostiful.item.cloak.AbstractFrostologyCloakItem;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public class CapeFeatureRendererMixin {

    @Unique
    @Nullable
    private AbstractClientPlayer scorchful$renderedPlayer = null;

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At("HEAD")
    )
    private void capturePlayer(
            PoseStack matrixStack,
            MultiBufferSource vertexConsumerProvider,
            int i,
            AbstractClientPlayer player,
            float f, float g, float h, float j, float k, float l,
            CallbackInfo ci
    ) {
        this.scorchful$renderedPlayer = player;
    }

    @WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/PlayerSkin;capeTexture()Lnet/minecraft/resources/ResourceLocation;"
            )
    )
    private ResourceLocation getFrostologyCloakTexture(PlayerSkin instance, Operation<ResourceLocation> original) {

        boolean renderFrostologyCloak = this.scorchful$renderedPlayer != null
                && AbstractFrostologyCloakItem.isWearing(this.scorchful$renderedPlayer, stack -> stack.is(FItemTags.FROSTOLOGY_CLOAKS));

        if (renderFrostologyCloak) {
            return AbstractFrostologyCloakItem.MODEL_TEXTURE_ID;
        } else {
            return original.call(instance);
        }
    }
}
