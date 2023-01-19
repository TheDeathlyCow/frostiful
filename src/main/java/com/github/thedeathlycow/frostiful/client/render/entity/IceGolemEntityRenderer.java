package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.model.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.model.IceGolemEntityModel;
import com.github.thedeathlycow.frostiful.entity.IceGolemEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class IceGolemEntityRenderer extends MobEntityRenderer<IceGolemEntity, IceGolemEntityModel> {

    public static final Identifier TEXTURE = Frostiful.id("textures/entity/ice_golem.png");

    public IceGolemEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new IceGolemEntityModel(context.getPart(FEntityModelLayers.ICE_GOLEM)), 0.5F);
    }

    @Override
    public Identifier getTexture(IceGolemEntity entity) {
        return TEXTURE;
    }

}
