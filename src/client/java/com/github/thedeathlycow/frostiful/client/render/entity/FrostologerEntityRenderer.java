package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostLayer;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostologerCloakFeatureRenderer;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostologerEyesFeatureRenderer;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostologerFrostFeatureRenderer;
import com.github.thedeathlycow.frostiful.client.render.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.CrossbowItem;

@Environment(EnvType.CLIENT)
public class FrostologerEntityRenderer extends MobRenderer<FrostologerEntity, FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {


    private static final Identifier TEXTURE = Frostiful.id("textures/entity/illager/frostologer.png");

    public FrostologerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new FrostologerEntityModel<>(context.bakeLayer(FEntityModelLayers.FROSTOLOGER)), 0.5F);

        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getPlayerSkinRenderCache()));

        this.addLayer(new ItemInHandLayer<>(this));
        this.addLayer(new FrostologerCloakFeatureRenderer(this, context.getModelSet(), context.getEquipmentAssets()));
        this.addLayer(
                new FrostologerEyesFeatureRenderer<>(
                        this,
                        Frostiful.id("textures/entity/illager/frostologer/glow.png")
                )
        );
        this.addLayer(new FrostologerFrostFeatureRenderer(this));
    }

    @Override
    public FrostologerEntityRenderState createRenderState() {
        return new FrostologerEntityRenderState();
    }

    @Override
    public void extractRenderState(FrostologerEntity frostologer, FrostologerEntityRenderState state, float tickDelta) {
        super.extractRenderState(frostologer, state, tickDelta);
        ArmedEntityRenderState.extractArmedEntityRenderState(frostologer, state, this.itemModelResolver);
        state.isRiding = frostologer.isPassenger();
        state.mainArm = frostologer.getMainArm();
        state.armPose = frostologer.getArmPose();
        state.maxCrossbowChargeDuration = state.armPose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE
                ? CrossbowItem.getChargeDuration(frostologer.getUseItem(), frostologer)
                : 0;
        state.ticksUsingItem = frostologer.getTicksUsingItem();
        state.attackAnim = frostologer.getAttackAnim(tickDelta);
        state.isAggressive = frostologer.isAggressive();


        state.usingFrostWand = frostologer.isUsingFrostWand();
        state.frostLayer = FrostLayer.fromFrostologer(frostologer);
        state.glowingEyes = frostologer.isAtMaxPower();

        float rgColorMul = 0.625f * frostologer.thermoo$getTemperatureScale() + 1f;
        state.tint = ARGB.colorFromFloat(1f, rgColorMul, rgColorMul, 1f);

        CapeComponent cape = frostologer.getItemBySlot(EquipmentSlot.CHEST).get(FDataComponentTypes.CAPE);
        if (cape != null) {
            state.capeTexture = cape.capeAsset();
            updateCape(frostologer, state, tickDelta);
        } else {
            state.capeTexture = null;
        }
    }

    @Override
    protected int getModelTint(FrostologerEntityRenderState state) {
        return state.tint;
    }

    // i dont know what the purpose of this was so im removing it
//    @Override
//    protected void scale(FrostologerEntityRenderState state, MatrixStack matrices) {
//        float scale = 15f / 16f;
//        matrices.scale(scale, scale, scale);
//    }

    @Override
    public Identifier getTextureLocation(FrostologerEntityRenderState pillagerEntity) {
        return TEXTURE;
    }

    private static void updateCape(FrostologerEntity frostologer, FrostologerEntityRenderState state, float tickDelta) {
        double deltaX = Mth.lerp(tickDelta, frostologer.prevCapeX, frostologer.capeX) - Mth.lerp(tickDelta, frostologer.xo, frostologer.getX());
        double deltaY = Mth.lerp(tickDelta, frostologer.prevCapeY, frostologer.capeY) - Mth.lerp(tickDelta, frostologer.yo, frostologer.getY());
        double deltaZ = Mth.lerp(tickDelta, frostologer.prevCapeZ, frostologer.capeZ) - Mth.lerp(tickDelta, frostologer.zo, frostologer.getZ());

        float bodyYaw = Mth.rotLerp(tickDelta, frostologer.yBodyRotO, frostologer.yBodyRot);
        double sinYaw = Mth.sin(bodyYaw * (float) (Math.PI / 180.0));
        double cosYaw = -Mth.cos(bodyYaw * (float) (Math.PI / 180.0));

        state.capePitch = (float) deltaY * 10.0f;
        state.capePitch = Mth.clamp(state.capePitch, -6.0f, 32.0f);

        state.capeSwing = (float) (deltaX * sinYaw + deltaZ * cosYaw) * 100.0f;
        state.capeSwing = Mth.clamp(state.capeSwing, 0.0f, 150.0f);

        state.capeStrafe = (float) (deltaX * cosYaw - deltaZ * sinYaw) * 100.0f;
        state.capeStrafe = Mth.clamp(state.capeStrafe, -20.0f, 20.0f);

        if (state.isDiscrete) {
            state.capePitch += 25.0F;
        }

        // TODO: prevHorizontalSpeed, horizontalSpeed need to be done for the frostloger specifically now, but i dont feel like it (and it doesnt seem to have a big effect?)
//        float stride = MathHelper.lerp(tickDelta, frostologer.prevStrideDistance, frostologer.strideDistance);
//        float distanceMoved = MathHelper.lerp(tickDelta, frostologer.prevHorizontalSpeed, frostologer.horizontalSpeed);
//        state.capePitch += MathHelper.sin(distanceMoved * 6.0f) * 32.0f * stride;
    }
}
