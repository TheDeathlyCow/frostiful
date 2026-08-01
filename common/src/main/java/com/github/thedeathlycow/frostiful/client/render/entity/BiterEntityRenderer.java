package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.BiterEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.BiterEntityRenderState;
import com.github.thedeathlycow.frostiful.entity.Biter;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;


public class BiterEntityRenderer extends MobRenderer<Biter, BiterEntityRenderState, BiterEntityModel> {

    public static final Identifier TEXTURE = Frostiful.id("textures/entity/biter.png");

    public BiterEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BiterEntityModel(context.bakeLayer(FEntityModelLayers.BITER)), 0.5F);
    }

    @Override
    public void extractRenderState(Biter entity, BiterEntityRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.biteAnimationState.copyFrom(entity.bitingAnimation);
    }

    @Override
    public BiterEntityRenderState createRenderState() {
        return new BiterEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(BiterEntityRenderState state) {
        return TEXTURE;
    }
}
