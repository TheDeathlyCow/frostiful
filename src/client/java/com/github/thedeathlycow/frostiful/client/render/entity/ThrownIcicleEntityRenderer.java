package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.ThrownIcicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ThrownIcicleEntityRenderer extends ArrowRenderer<ThrownIcicleEntity, ArrowRenderState> {

    public static final ResourceLocation TEXTURE = Frostiful.location("textures/entity/projectiles/thrown_icicle.png");


    public ThrownIcicleEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }


    @Override
    public ResourceLocation getTextureLocation(ArrowRenderState entity) {
        return TEXTURE;
    }
}
