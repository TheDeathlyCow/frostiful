package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.model.BiterEntityModel;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.entity.BiterEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class BiterEntityRenderer extends MobRenderer<BiterEntity, BiterEntityModel> {

    public static final ResourceLocation TEXTURE = Frostiful.id("textures/entity/biter.png");

    public BiterEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BiterEntityModel(context.bakeLayer(FEntityModelLayers.BITER)), 0.5F);
    }

    @Override
    public ResourceLocation getTexture(BiterEntity entity) {
        return TEXTURE;
    }

}
