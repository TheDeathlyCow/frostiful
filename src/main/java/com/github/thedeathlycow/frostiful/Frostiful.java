package com.github.thedeathlycow.frostiful;

import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.datafix.StructureUpdateHelper;
import com.github.thedeathlycow.frostiful.entity.component.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.entity.loot.StrayLootTableModifier;
import com.github.thedeathlycow.frostiful.item.FrostedBanner;
import com.github.thedeathlycow.frostiful.registry.*;
import com.github.thedeathlycow.frostiful.registry.tag.FTemperatureStatusTags;
import com.github.thedeathlycow.frostiful.server.command.RootCommand;
import com.github.thedeathlycow.frostiful.server.command.WindCommand;
import com.github.thedeathlycow.frostiful.server.network.PointWindSpawnPacket;
import com.github.thedeathlycow.frostiful.survival.ActiveTemperatureEffects;
import com.github.thedeathlycow.frostiful.survival.PassiveTemperatureEffects;
import com.github.thedeathlycow.frostiful.survival.ServerPlayerEnvironmentTickListeners;
import com.github.thedeathlycow.frostiful.survival.SoakingEffects;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatusEvents;
import dev.yumi.commons.TriState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        FrostifulConfigYACL.initialize();

        if (isDevelopmentEnvironment()) {
            CommandRegistrationCallback.EVENT.register(
                    (dispatcher, registryAccess, environment) -> {
                        RootCommand.register(dispatcher);
                        WindCommand.register(dispatcher);
                        FrostedBanner.registerCommand(dispatcher);
                    });
        }

        LootTableEvents.MODIFY.register(StrayLootTableModifier::addFrostTippedArrows);

        FrostifulRegistries.initialize();
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
        FAttributeTypes.initialize();
        FEnvironmentAttributes.initialize();
        FBlockTransformerTypes.initialize();
        FEnvironmentProviderTypes.initialize();

        ServerLivingEntityEvents.AFTER_DAMAGE.register(FrostWandRootComponent::afterDamage);

        this.registerThermooEventListeners();
        PayloadTypeRegistry.clientboundPlay().register(
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
        TemperatureStatusEvents.ALLOW_TEMPERATURE_STATUS.register((livingEntity, reference) -> {
            if (reference.is(FTemperatureStatusTags.NORMAL_PLAYER_STATUSES)) {
                return TriState.from(!TrinketsIntegration.wearingFrostologyCloak(livingEntity));
            } else if (reference.is(FTemperatureStatusTags.FROSTOLOGY_CLOAK_PLAYER_STATUSES)) {
                return TriState.from(TrinketsIntegration.wearingFrostologyCloak(livingEntity));
            } else {
                return TriState.DEFAULT;
            }
        });
    }

    public static boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir().resolve(MODID);
    }

    /**
     * Creates a new {@link Identifier} in the namespace {@value MODID}.
     *
     * @param path The path of the uuid
     * @return Returns a new {@link Identifier}
     */
    @Contract("_->new")
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
