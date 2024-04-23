package com.github.thedeathlycow.frostiful;

import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.effect.FPotions;
import com.github.thedeathlycow.frostiful.entity.effect.FStatusEffects;
import com.github.thedeathlycow.frostiful.entity.loot.StrayLootTableModifier;
import com.github.thedeathlycow.frostiful.item.FSmithingTemplateItem;
import com.github.thedeathlycow.frostiful.item.FrostWandItem;
import com.github.thedeathlycow.frostiful.item.FrostologyCloakItem;
import com.github.thedeathlycow.frostiful.particle.FParticleTypes;
import com.github.thedeathlycow.frostiful.registry.*;
import com.github.thedeathlycow.frostiful.server.command.RootCommand;
import com.github.thedeathlycow.frostiful.server.command.WindCommand;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.frostiful.survival.*;
import com.github.thedeathlycow.frostiful.tag.FEnchantmentTags;
import com.github.thedeathlycow.frostiful.tag.FItemTags;
import com.github.thedeathlycow.frostiful.world.gen.feature.FFeatures;
import com.github.thedeathlycow.frostiful.world.gen.feature.FPlacedFeatures;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentControllerInitializeEvent;
import com.github.thedeathlycow.thermoo.api.temperature.event.PlayerEnvironmentEvents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.registry.Registries;
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
                    WindCommand.register(dispatcher);
                });

        LootTableEvents.MODIFY.register(StrayLootTableModifier::addFrostTippedArrows);

        FBlocks.registerBlocks();
        FItems.registerItems();
        FEntityTypes.registerEntities();
        FGameRules.registerGamerules();
        FSoundEvents.registerSoundEvents();
        FStatusEffects.registerStatusEffects();
        FEnchantments.registerEnchantments();
        FParticleTypes.registerParticleTypes();
        FPotions.register();
        FItemGroups.registerAll();

        FFeatures.registerAll();
        FPlacedFeatures.placeFeatures();

        this.registerThermooEventListeners();
        FSmithingTemplateItem.addTemplatesToLoot();

        EnchantmentEvents.ALLOW_ENCHANTING.register(
                (enchantment, target, enchantingContext) -> {
                    boolean isWeapon = target.isIn(FItemTags.SUPPORTS_HEAT_DRAIN);

                    boolean isFrostWandWeaponEnchantment = Registries.ENCHANTMENT
                            .getEntry(enchantment)
                            .isIn(FEnchantmentTags.HEAT_DRAIN);

                    return isWeapon && isFrostWandWeaponEnchantment && enchantingContext != EnchantingContext.RANDOM_ENCHANTMENT
                            ? TriState.TRUE
                            : TriState.DEFAULT;
                }
        );

        EnchantmentEvents.ALLOW_ENCHANTING.register(
                (enchantment, target, enchantingContext) -> {
                    boolean isFrostWand = target.getItem() instanceof FrostWandItem;

                    boolean isFrostWandAnvilEnchantment = Registries.ENCHANTMENT
                            .getEntry(enchantment)
                            .isIn(FEnchantmentTags.FROST_WAND_ANVIL);

                    return isFrostWand && isFrostWandAnvilEnchantment && enchantingContext != EnchantingContext.RANDOM_ENCHANTMENT
                            ? TriState.TRUE
                            : TriState.DEFAULT;
                }
        );

        LOGGER.info("Initialized Frostiful!");
    }

    private void registerThermooEventListeners() {
        PlayerEnvironmentEvents.CAN_APPLY_PASSIVE_TEMPERATURE_CHANGE.register(
                (change, player) -> {
                    if (change > 0) {
                        return true;
                    }

                    FrostifulConfig config = getConfig();

                    if (player.thermoo$getTemperatureScale() < -config.freezingConfig.getMaxPassiveFreezingPercent()) {
                        return false;
                    }

                    boolean doPassiveFreezing = config.freezingConfig.doPassiveFreezing()
                            && player.getWorld().getGameRules().getBoolean(FGameRules.DO_PASSIVE_FREEZING);

                    if (doPassiveFreezing) {
                        return true;
                    } else {
                        return FrostologyCloakItem.isWornBy(player);
                    }
                }
        );

        EnvironmentControllerInitializeEvent.EVENT.register(AmbientTemperatureController::new);
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
