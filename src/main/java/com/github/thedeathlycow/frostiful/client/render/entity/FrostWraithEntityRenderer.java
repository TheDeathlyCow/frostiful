package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.model.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.model.FrostWraithEntityModel;
import com.github.thedeathlycow.frostiful.entity.FrostWraithEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

@Environment(EnvType.CLIENT)
public class FrostWraithEntityRenderer extends BipedEntityRenderer<FrostWraithEntity, FrostWraithEntityModel> {


    public FrostWraithEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FrostWraithEntityModel(context.getPart(FEntityModelLayers.FROST_WRAITH)), 0.3F);
    }

}
