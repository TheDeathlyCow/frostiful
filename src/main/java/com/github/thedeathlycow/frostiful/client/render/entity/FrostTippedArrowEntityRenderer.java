package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.FrostTippedArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostTippedArrowEntityRenderer extends ProjectileEntityRenderer<FrostTippedArrowEntity> {

    public static final Identifier TEXTURE = Frostiful.id("textures/entity/projectiles/frost_tipped_arrow.png");

    public FrostTippedArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(FrostTippedArrowEntity entity) {
        return TEXTURE;
    }
}
