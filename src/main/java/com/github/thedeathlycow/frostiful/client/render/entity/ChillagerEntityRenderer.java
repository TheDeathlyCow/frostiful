package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.model.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.entity.ChillagerEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ChillagerEntityRenderer extends IllagerEntityRenderer<ChillagerEntity> {

    private static final Identifier TEXTURE = new Identifier(Frostiful.MODID, "textures/entity/illager/chillager.png");


    protected ChillagerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new IllagerEntityModel<>(context.getPart(FEntityModelLayers.CHILLAGER)), 0.5F);
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));

        this.model.getHat().visible = true;
    }

    @Override
    public Identifier getTexture(ChillagerEntity entity) {
        return TEXTURE;
    }
}
