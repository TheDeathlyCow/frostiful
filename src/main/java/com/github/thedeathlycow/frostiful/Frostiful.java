package com.github.thedeathlycow.frostiful;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.component.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.entity.loot.StrayLootTableModifier;
import com.github.thedeathlycow.frostiful.item.FrostedBanner;
import com.github.thedeathlycow.frostiful.registry.*;
import com.github.thedeathlycow.frostiful.server.command.RootCommand;
import com.github.thedeathlycow.frostiful.server.command.WindCommand;
import com.github.thedeathlycow.frostiful.server.network.PointWindSpawnPacket;
import com.github.thedeathlycow.frostiful.survival.ActiveTemperatureEffects;
import com.github.thedeathlycow.frostiful.survival.PassiveTemperatureEffects;
import com.github.thedeathlycow.frostiful.survival.ServerPlayerEnvironmentTickListeners;
import com.github.thedeathlycow.frostiful.survival.SoakingEffects;
import com.github.thedeathlycow.frostiful.datafix.StructureUpdateHelper;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final int CONFIG_VERSION = 3;

    @Nullable
    private static ConfigHolder<FrostifulConfig> configHolder = null;

    @Override
    public void onInitialize() {
        AutoConfig.register(FrostifulConfig.class, GsonConfigSerializer::new);
        configHolder = AutoConfig.getConfigHolder(FrostifulConfig.class); //NOSONAR this is fine
        FrostifulConfig.updateConfig(configHolder);

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CommandRegistrationCallback.EVENT.register(
                    (dispatcher, registryAccess, environment) -> {
                        RootCommand.register(dispatcher);
                        WindCommand.register(dispatcher);
                        FrostedBanner.registerCommand(dispatcher);
                    });
        }

        LootTableEvents.MODIFY.register(StrayLootTableModifier::addFrostTippedArrows);

        FBlocks.initialize();
        FDataComponentTypes.initialize();
        FItems.initialize();
        FEntityTypes.initialize();
        FGameRules.initialize();
        FSoundEvents.initialize();
        FStatusEffects.initialize();
        FParticleTypes.initialize();
        FPotions.initialize();
        FItemGroups.initialize();
        FLootConditionTypes.initialize();
        FFeatures.initialize();
        FPlacedFeatures.initialize();
        FEntityAttributes.initialize();
        FCriteria.initialize();
        StructureUpdateHelper.initialize();

        ServerLivingEntityEvents.AFTER_DAMAGE.register(FrostWandRootComponent::afterDamage);

        this.registerThermooEventListeners();
        PayloadTypeRegistry.playS2C().register(
                PointWindSpawnPacket.PACKET_ID,
                PointWindSpawnPacket.PACKET_CODEC
        );

        LOGGER.info("Initialized Frostiful!");
    }

    private void registerThermooEventListeners() {
        ServerPlayerEnvironmentTickListeners.initialize();
        PassiveTemperatureEffects.initialize();
        ActiveTemperatureEffects.initialize();
        SoakingEffects.initialize();
    }

    public static FrostifulConfig getConfig() {
        if (configHolder == null) {
            configHolder = AutoConfig.getConfigHolder(FrostifulConfig.class);
        }

        return configHolder.getConfig();
    }

    /**
     * Creates a new {@link Identifier} in the namespace {@value MODID}.
     *
     * @param path The path of the uuid
     * @return Returns a new {@link Identifier}
     */
    @Contract("_->new")
    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }
}
