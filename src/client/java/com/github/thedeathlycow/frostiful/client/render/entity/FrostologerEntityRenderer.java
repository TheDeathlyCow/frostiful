package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostologerCloakFeatureRenderer;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostologerEyesFeatureRenderer;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostologerFrostFeatureRenderer;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class FrostologerEntityRenderer extends MobRenderer<FrostologerEntity, FrostologerEntityModel<FrostologerEntity>> {


    private static final ResourceLocation TEXTURE = Frostiful.id("textures/entity/illager/frostologer.png");

    public FrostologerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new FrostologerEntityModel<>(context.bakeLayer(FEntityModelLayers.FROSTOLOGER)), 0.5F);

        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new FrostologerCloakFeatureRenderer(this));
        this.addLayer(
                new FrostologerEyesFeatureRenderer<>(
                        this,
                        Frostiful.id("textures/entity/illager/frostologer/glow.png")
                )
        );
        this.addLayer(new FrostologerFrostFeatureRenderer(this));
    }

    @Override
    protected void scale(FrostologerEntity frostologerEntity, PoseStack matrixStack, float amount) {
        float scale = 15f / 16f;
        matrixStack.scale(scale, scale, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(FrostologerEntity pillagerEntity) {
        return TEXTURE;
    }
}
