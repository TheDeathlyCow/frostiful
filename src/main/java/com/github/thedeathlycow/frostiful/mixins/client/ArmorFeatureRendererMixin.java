package com.github.thedeathlycow.frostiful.mixins.client;

import com.github.thedeathlycow.frostiful.client.FTexturedRenderLayers;
import com.github.thedeathlycow.frostiful.item.FArmorTrimPatterns;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.trim.ArmorTrim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> {

    private SpriteAtlasTexture frostiful$customArmorTrimsAtlas;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void initCustomTrimAtlas(FeatureRendererContext<T, M> context, A innerModel, A outerModel, BakedModelManager bakery, CallbackInfo ci) {
        this.frostiful$customArmorTrimsAtlas = bakery.getAtlas(FTexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
    }


    @Inject(
            method = "renderTrim",
            at = @At("HEAD")
    )
    private void renderCustomTrim(ArmorMaterial material, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, A model, boolean leggings, CallbackInfo ci) {

        if (!trim.getPattern().matchesKey(FArmorTrimPatterns.SNOWY)) {
            return;
        }

        Sprite sprite = this.frostiful$customArmorTrimsAtlas.getSprite(
                leggings
                        ? trim.getLeggingsModelId(material)
                        : trim.getGenericModelId(material)
        );
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(
                vertexConsumers.getBuffer(FTexturedRenderLayers.ARMOR_TRIMS_RENDER_LAYER)
        );
        model.render(
                matrices,
                vertexConsumer,
                light,
                OverlayTexture.DEFAULT_UV,
                1.0f, 1.0f, 1.0f, 1.0f
        );

        ci.cancel();
    }
}
