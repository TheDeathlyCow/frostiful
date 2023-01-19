package com.github.thedeathlycow.frostiful.client.model;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;

@Environment(EnvType.CLIENT)
public class FEntityModelLayers {

    public static final EntityModelLayer FROST_WAND = new EntityModelLayer(Frostiful.id("frost_wand"), "main");
    public static final EntityModelLayer FROSTOLOGER = new EntityModelLayer(Frostiful.id("frostologer"), "main");
    public static final EntityModelLayer CHILLAGER = new EntityModelLayer(Frostiful.id("chillager"), "main");

    public static final EntityModelLayer ICE_GOLEM = new EntityModelLayer(Frostiful.id("ice_golem"), "main");

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(FROST_WAND, FrostWandItemModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FROSTOLOGER, FrostologerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CHILLAGER, IllagerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ICE_GOLEM, IceGolemEntityModel::getTexturedModelData);
    }
}
