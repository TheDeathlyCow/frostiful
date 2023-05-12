package com.github.thedeathlycow.frostiful;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.effect.FPotions;
import com.github.thedeathlycow.frostiful.entity.effect.FStatusEffects;
import com.github.thedeathlycow.frostiful.entity.loot.StrayLootTableModifier;
import com.github.thedeathlycow.frostiful.item.attribute.ItemAttributeLoader;
import com.github.thedeathlycow.frostiful.particle.FParticleTypes;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.FEnchantments;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.server.command.RootCommand;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.frostiful.survival.LivingEntityThermooEventListeners;
import com.github.thedeathlycow.frostiful.survival.PlayerEventThermooListeners;
import com.github.thedeathlycow.frostiful.world.FGameRules;
import com.github.thedeathlycow.frostiful.world.gen.feature.FFeatures;
import com.github.thedeathlycow.frostiful.world.gen.feature.FPlacedFeatures;
import com.github.thedeathlycow.thermoo.api.temperature.event.LivingEntityEnvironmentEvents;
import com.github.thedeathlycow.thermoo.api.temperature.event.PlayerEnvironmentEvents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final int CONFIG_VERSION = 2;

    @Override
    public void onInitialize() {
        AutoConfig.register(FrostifulConfig.class, GsonConfigSerializer::new);
        FrostifulConfig.updateConfig(AutoConfig.getConfigHolder(FrostifulConfig.class));

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    RootCommand.register(dispatcher);
                });

        LootTableEvents.MODIFY.register(StrayLootTableModifier::addFrostTippedArrows);

        ResourceManagerHelper serverManager = ResourceManagerHelper.get(ResourceType.SERVER_DATA);

        serverManager.registerReloadListener(ItemAttributeLoader.INSTANCE);
        ModifyItemAttributeModifiersCallback.EVENT.register(ItemAttributeLoader.INSTANCE);

        FBlocks.registerBlocks();
        FItems.registerItems();
        FEntityTypes.registerEntities();
        FGameRules.registerGamerules();
        FSoundEvents.registerSoundEvents();
        FStatusEffects.registerStatusEffects();
        FEnchantments.registerEnchantments();
        FParticleTypes.registerParticleTypes();
        FPotions.register();

        FFeatures.registerAll();
        FPlacedFeatures.placeFeatures();

        this.registerThermooEventListeners();

        LOGGER.info("Initialized Frostiful!");
    }

    private void registerThermooEventListeners() {
        PlayerEventThermooListeners player = new PlayerEventThermooListeners();

        PlayerEnvironmentEvents.TICK_BIOME_TEMPERATURE_CHANGE.register(player::applyPassiveFreezing);

        LivingEntityThermooEventListeners entity = new LivingEntityThermooEventListeners();

        LivingEntityEnvironmentEvents.TICK_IN_HEATED_LOCATION.register(entity::tickHeatSources);
        LivingEntityEnvironmentEvents.TICK_HEAT_EFFECTS.register(entity::tickHeatEffects);
        LivingEntityEnvironmentEvents.TICK_IN_WET_LOCATION.register(entity::tickWetChange);

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
    @Contract("_->new")
    public static Identifier id(String path) {
        return new Identifier(Frostiful.MODID, path);
    }
}
