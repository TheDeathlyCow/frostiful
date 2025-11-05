package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.entity.ChillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ChillagerEntityRenderer extends IllagerRenderer<ChillagerEntity, IllagerRenderState> {

    private static final ResourceLocation TEXTURE = Frostiful.id("textures/entity/illager/chillager.png");


    public ChillagerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(FEntityModelLayers.CHILLAGER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this));

        this.model.getHat().visible = true;
    }

    @Override
    public IllagerRenderState createRenderState() {
        return new IllagerRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(IllagerRenderState state) {
        return TEXTURE;
    }
}
