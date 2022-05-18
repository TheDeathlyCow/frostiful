package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.FrostifulEntityTypes;
import com.github.thedeathlycow.frostiful.entity.damage.FrostifulDamageSource;
import com.github.thedeathlycow.frostiful.entity.loot.StrayLootTableListener;
import com.github.thedeathlycow.frostiful.item.FrostifulItems;
import com.github.thedeathlycow.frostiful.server.command.FreezeCommand;
import com.github.thedeathlycow.frostiful.server.command.GetTemperatureCommand;
import com.github.thedeathlycow.frostiful.world.FrostifulGameRules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final FrostifulConfig CONFIG = new FrostifulConfig();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Frostiful...");

        CONFIG.readConfigFromFile();
        CONFIG.saveConfigToFile();
        ServerLifecycleEvents.SERVER_STOPPED.register(instance -> CONFIG.saveConfigToFile());

        CommandRegistrationCallback.EVENT.register(
                ((dispatcher, dedicated) -> {
                    GetTemperatureCommand.register(dispatcher);
                    FreezeCommand.register(dispatcher);
                })
        );

        LootTableLoadingCallback.EVENT.register(StrayLootTableListener::addFrostTippedArrows);

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(CONFIG);

        FrostifulEntityAttributes.registerAttributes();
        FrostifulDamageSource.registerDamageSources();
        FrostifulBlocks.registerBlocks();
        FrostifulItems.registerItems();
        FrostifulEntityTypes.registerEntities();
        FrostifulGameRules.registerGamerules();

        LOGGER.info("Initialized Frostiful!");
    }

}
