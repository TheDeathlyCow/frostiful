package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import com.github.thedeathlycow.frostiful.block.FBlocks;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.enchantment.FEnchantments;
import com.github.thedeathlycow.frostiful.entity.FEntityTypes;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSource;
import com.github.thedeathlycow.frostiful.entity.effect.FStatusEffects;
import com.github.thedeathlycow.frostiful.entity.loot.StrayLootTableModifier;
import com.github.thedeathlycow.frostiful.item.FItems;
import com.github.thedeathlycow.frostiful.item.attribute.ItemAttributeLoader;
import com.github.thedeathlycow.frostiful.particle.FParticleTypes;
import com.github.thedeathlycow.frostiful.server.command.FrostCommand;
import com.github.thedeathlycow.frostiful.server.command.FrostifulCommand;
import com.github.thedeathlycow.frostiful.server.command.RootCommand;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.frostiful.util.survival.effects.TemperatureEffectLoader;
import com.github.thedeathlycow.frostiful.util.survival.effects.TemperatureEffects;
import com.github.thedeathlycow.frostiful.world.FGameRules;
import com.github.thedeathlycow.frostiful.world.gen.feature.FPlacedFeatures;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final int CONFIG_VERSION = 0;

    @Override
    public void onInitialize() {
        AutoConfig.register(FrostifulConfig.class, GsonConfigSerializer::new);
        FrostifulConfig.updateConfig(AutoConfig.getConfigHolder(FrostifulConfig.class));

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    FrostifulCommand.register(dispatcher);
                    FrostCommand.register(dispatcher);
                    RootCommand.register(dispatcher);
                });

        LootTableEvents.MODIFY.register(StrayLootTableModifier::addFrostTippedArrows);
        TemperatureEffects.registerAll();

        ResourceManagerHelper serverManager = ResourceManagerHelper.get(ResourceType.SERVER_DATA);

        serverManager.registerReloadListener(TemperatureEffectLoader.INSTANCE);
        serverManager.registerReloadListener(ItemAttributeLoader.INSTANCE);

        FEntityAttributes.registerAttributes();
        FDamageSource.registerDamageSources();
        FBlocks.registerBlocks();
        FItems.registerItems();
        FEntityTypes.registerEntities();
        FGameRules.registerGamerules();
        FSoundEvents.registerSoundEvents();
        FPlacedFeatures.placeFeatures();
        FStatusEffects.registerStatusEffects();
        FEnchantments.registerEnchantments();
        FParticleTypes.registerParticleTypes();

        LOGGER.info("Initialized Frostiful!");
    }

    public static FrostifulConfig getConfig() {
        return AutoConfig.getConfigHolder(FrostifulConfig.class).getConfig();
    }

    /**
     * Creates a new {@link Identifier} in the namespace {@value MODID}.
     * 
     * @param path The path of the uuid
     * @return Returns a new {@link Identifier}
     */
    public static Identifier id(String path) {
        return new Identifier(Frostiful.MODID, path);
    }
}
