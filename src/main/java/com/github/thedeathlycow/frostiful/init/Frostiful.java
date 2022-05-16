package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.config.*;
import com.github.thedeathlycow.frostiful.entity.FrostifulEntityTypes;
import com.github.thedeathlycow.frostiful.entity.damage.FrostifulDamageSource;
import com.github.thedeathlycow.frostiful.item.FrostifulItems;
import com.github.thedeathlycow.frostiful.server.command.FreezeCommand;
import com.github.thedeathlycow.frostiful.server.command.GetTemperatureCommand;
import com.github.thedeathlycow.frostiful.world.FrostifulGameRules;
import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.reload.Reloadable;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Frostiful...");

        ConfigReloader reloader = new ConfigReloader();
        reloader.addListener(new Reloadable(WeatherConfig.CONFIG));
        reloader.addListener(new Reloadable(IcicleConfig.CONFIG));
        reloader.addListener(new Reloadable(AttributeConfig.CONFIG));
        reloader.addListener(new Reloadable(FreezingConfig.CONFIG));
        ResourceManagerHelper.get(ResourceType.SERVER_DATA)
                .registerReloadListener(reloader);

        CommandRegistrationCallback.EVENT.register(
                ((dispatcher, dedicated) -> {
                    GetTemperatureCommand.register(dispatcher);
                    FreezeCommand.register(dispatcher);
                })
        );

        FrostifulEntityAttributes.registerAttributes();
        FrostifulDamageSource.registerDamageSources();
        FrostifulBlocks.registerBlocks();
        FrostifulItems.registerItems();
        FrostifulEntityTypes.registerEntities();
        FrostifulGameRules.registerGamerules();

        LOGGER.info("Initialized Frostiful!");
    }

}
