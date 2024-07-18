package com.github.thedeathlycow.frostiful;

import com.github.thedeathlycow.frostiful.block.CampfireUseEventListener;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.loot.StrayLootTableModifier;
import com.github.thedeathlycow.frostiful.item.FSmithingTemplateItem;
import com.github.thedeathlycow.frostiful.item.FrostologyCloakItem;
import com.github.thedeathlycow.frostiful.item.event.FrostResistanceProvider;
import com.github.thedeathlycow.frostiful.registry.*;
import com.github.thedeathlycow.frostiful.server.command.RootCommand;
import com.github.thedeathlycow.frostiful.server.command.WindCommand;
import com.github.thedeathlycow.frostiful.server.network.PointWindSpawnPacket;
import com.github.thedeathlycow.frostiful.server.world.gen.feature.FFeatures;
import com.github.thedeathlycow.frostiful.server.world.gen.feature.FPlacedFeatures;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.frostiful.survival.*;
import com.github.thedeathlycow.thermoo.api.armor.material.ArmorMaterialEvents;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentControllerInitializeEvent;
import com.github.thedeathlycow.thermoo.api.temperature.event.PlayerEnvironmentEvents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final int CONFIG_VERSION = 2;

    @Nullable
    private static ConfigHolder<FrostifulConfig> configHolder = null;

    @Override
    public void onInitialize() {
        AutoConfig.register(FrostifulConfig.class, GsonConfigSerializer::new);
        configHolder = AutoConfig.getConfigHolder(FrostifulConfig.class); //NOSONAR this is fine
        FrostifulConfig.updateConfig(configHolder);

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    RootCommand.register(dispatcher);
                    WindCommand.register(dispatcher);
                });

        LootTableEvents.MODIFY.register(StrayLootTableModifier::addFrostTippedArrows);

        FArmorMaterials.initialize();
        FBlocks.registerBlocks();
        FItems.registerItems();
        FEntityTypes.registerEntities();
        FGameRules.initialize();
        FSoundEvents.registerSoundEvents();
        FStatusEffects.initialize();
        FParticleTypes.registerParticleTypes();
        FPotions.initialize();
        FItemGroups.registerAll();
        FLootConditionTypes.registerAll();

        FFeatures.registerAll();
        FPlacedFeatures.placeFeatures();

        this.registerThermooEventListeners();
        FSmithingTemplateItem.addTemplatesToLoot();
        PayloadTypeRegistry.playS2C().register(
                PointWindSpawnPacket.PACKET_ID,
                PointWindSpawnPacket.PACKET_CODEC
        );
        UseBlockCallback.EVENT.register(new CampfireUseEventListener());

        LOGGER.info("Initialized Frostiful!");
    }

    private void registerThermooEventListeners() {

        ArmorMaterialEvents.GET_FROST_RESISTANCE.register(new FrostResistanceProvider());

        PlayerEnvironmentEvents.CAN_APPLY_PASSIVE_TEMPERATURE_CHANGE.register(
                (change, player) -> {
                    if (change > 0) {
                        return TriState.DEFAULT;
                    }

                    FrostifulConfig config = getConfig();

                    int tickInterval = config.freezingConfig.getPassiveFreezingTickInterval();
                    if (tickInterval > 1 && player.age % tickInterval != 0) {
                        return TriState.FALSE;
                    }

                    if (player.thermoo$getTemperatureScale() < -config.freezingConfig.getMaxPassiveFreezingPercent()) {
                        return TriState.FALSE;
                    }

                    boolean doPassiveFreezing = config.freezingConfig.doPassiveFreezing()
                            && player.getWorld().getGameRules().getBoolean(FGameRules.DO_PASSIVE_FREEZING);

                    if (doPassiveFreezing) {
                        return TriState.TRUE;
                    } else {
                        return TriState.of(FrostologyCloakItem.isWornBy(player));
                    }
                }
        );

        EnvironmentControllerInitializeEvent.EVENT.register(controller -> {
            return new AmbientTemperatureController(
                    FrostifulIntegrations.isModLoaded(FrostifulIntegrations.SCORCHFUL_ID),
                    controller
            );
        });
        EnvironmentControllerInitializeEvent.EVENT.register(
                EnvironmentControllerInitializeEvent.LISTENER_PHASE,
                ControllerListeners::new
        );
        EnvironmentControllerInitializeEvent.EVENT.register(EntityTemperatureController::new);
        EnvironmentControllerInitializeEvent.EVENT.register(
                EnvironmentControllerInitializeEvent.MODIFY_PHASE,
                ModifyTemperatureController::new
        );
        EnvironmentControllerInitializeEvent.EVENT.register(
                controller -> FrostifulIntegrations.isModLoaded(FrostifulIntegrations.SCORCHFUL_ID)
                        ? controller
                        : new SoakingController(controller)
        );
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
