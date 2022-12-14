package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.entity.FrostologerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostologerEntityRenderer extends IllagerEntityRenderer<FrostologerEntity> {


    private static final Identifier TEXTURE = new Identifier("textures/entity/illager/pillager.png");

    public FrostologerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FrostologerEntityModel<>(context.getPart(EntityModelLayers.PILLAGER)), 0.5F);

        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    public Identifier getTexture(FrostologerEntity pillagerEntity) {
        return TEXTURE;
    }
}
