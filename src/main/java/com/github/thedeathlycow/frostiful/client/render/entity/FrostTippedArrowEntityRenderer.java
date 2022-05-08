package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.entity.FrostTippedArrowEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class FrostTippedArrowEntityRenderer extends ProjectileEntityRenderer<FrostTippedArrowEntity> {

    public static final Identifier TEXTURE = new Identifier(Frostiful.MODID, "textures/entity/projectiles/frost_tipped_arrow.png");

    public FrostTippedArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(FrostTippedArrowEntity entity) {
        return TEXTURE;
    }
}
