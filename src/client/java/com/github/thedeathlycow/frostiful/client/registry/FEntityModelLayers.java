package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.render.model.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.monster.illager.IllagerModel;

@Environment(EnvType.CLIENT)
public class FEntityModelLayers {

    public static final ModelLayerLocation FROST_WAND = new ModelLayerLocation(Frostiful.id("frost_wand"), "main");
    public static final ModelLayerLocation FROSTOLOGER = new ModelLayerLocation(Frostiful.id("frostologer"), "main");
    public static final ModelLayerLocation FROSTOLOGER_CAPE = new ModelLayerLocation(Frostiful.id("frostologer"), "cape");
    public static final ModelLayerLocation CHILLAGER = new ModelLayerLocation(Frostiful.id("chillager"), "main");

    public static final ModelLayerLocation BITER = new ModelLayerLocation(Frostiful.id("biter"), "main");

    public static final ModelLayerLocation ICE_SKATES = new ModelLayerLocation(Frostiful.id("ice_skates"), "main");
    public static final ModelLayerLocation ICE_SKATES_BABY = new ModelLayerLocation(Frostiful.id("ice_skates_baby"), "main");

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful entity model layers");
        ModelLayerRegistry.registerModelLayer(FROST_WAND, FrostWandItemModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(FROSTOLOGER, FrostologerEntityModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(FROSTOLOGER_CAPE, FrostologerCapeModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(CHILLAGER, IllagerModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(BITER, BiterEntityModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ICE_SKATES, IceSkateModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ICE_SKATES_BABY, IceSkateModel::getBabyTexturedModelData);
    }

    private FEntityModelLayers() {

    }
}
