package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.model.IceSkateModel;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class IceSkateFeatureRenderer<
        T extends LivingEntity,
        M extends HumanoidModel<T>,
        I extends IceSkateModel<T>
        > extends RenderLayer<T, M> {

    private final I model;

    private static final ResourceLocation SKATE_TEXTURE = Frostiful.id("textures/entity/skates.png");

    public IceSkateFeatureRenderer(
            RenderLayerParent<T, M> context,
            I model
    ) {
        super(context);
        this.model = model;
    }


    @Override
    public void render(
            PoseStack matrices,
            MultiBufferSource vertexConsumers,
            int light,
            T entity,
            float limbAngle, float limbDistance,
            float tickDelta, float animationProgress,
            float headYaw, float headPitch
    ) {
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(FItemTags.ICE_SKATES)) {
            this.getParentModel().copyPropertiesTo(model);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(SKATE_TEXTURE));
            this.model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
    }
}
