package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.model.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.model.FrostWyrmEntityModel;
import com.github.thedeathlycow.frostiful.entity.FrostWyrmEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

@Environment(EnvType.CLIENT)
public class FrostWyrmEntityRenderer extends BipedEntityRenderer<FrostWyrmEntity, FrostWyrmEntityModel> {


    public FrostWyrmEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FrostWyrmEntityModel(context.getPart(FEntityModelLayers.FROST_WYRM)), 0.3F);
    }

}
