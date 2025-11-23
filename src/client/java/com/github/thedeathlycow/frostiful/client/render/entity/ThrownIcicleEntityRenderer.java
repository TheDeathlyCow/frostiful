package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.ThrownIcicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ThrownIcicleEntityRenderer extends ArrowRenderer<ThrownIcicleEntity> {

    public static final ResourceLocation TEXTURE = Frostiful.id("textures/entity/projectiles/thrown_icicle.png");


    public ThrownIcicleEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTexture(ThrownIcicleEntity entity) {
        return TEXTURE;
    }
}
