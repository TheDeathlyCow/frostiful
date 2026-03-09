package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.model.BiterEntityModel;
import com.github.thedeathlycow.frostiful.client.model.FrostWandItemModel;
import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.model.IceSkateModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;

@Environment(EnvType.CLIENT)
public class FEntityModelLayers {
    public static final ModelLayerLocation FROST_WAND = new ModelLayerLocation(Frostiful.id("frost_wand"), "main");
    public static final ModelLayerLocation FROSTOLOGER = new ModelLayerLocation(Frostiful.id("frostologer"), "main");
    public static final ModelLayerLocation CHILLAGER = new ModelLayerLocation(Frostiful.id("chillager"), "main");

    public static final ModelLayerLocation BITER = new ModelLayerLocation(Frostiful.id("biter"), "main");

    public static final ModelLayerLocation ICE_SKATES = new ModelLayerLocation(Frostiful.id("ice_skates"), "main");

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful entity model layers");
        EntityModelLayerRegistry.registerModelLayer(FROST_WAND, FrostWandItemModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FROSTOLOGER, FrostologerEntityModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CHILLAGER, IllagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BITER, BiterEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ICE_SKATES, IceSkateModel::getTexturedModelData);
    }

    private FEntityModelLayers() {

    }
}
