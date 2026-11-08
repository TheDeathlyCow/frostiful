/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.render.model.*;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.monster.illager.IllagerModel;


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
