package com.github.thedeathlycow.frostiful.client.model;

import com.github.thedeathlycow.frostiful.Frostiful;
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

    public static final EntityModelLayer BITER = new EntityModelLayer(Frostiful.id("biter"), "main");

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(FROST_WAND, FrostWandItemModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FROSTOLOGER, FrostologerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CHILLAGER, IllagerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BITER, BiterEntityModel::getTexturedModelData);
    }
}
