package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.enchantment.FrostifulEnchantments;
import com.github.thedeathlycow.frostiful.entity.FrostifulEntityTypes;
import com.github.thedeathlycow.frostiful.entity.damage.FrostifulDamageSource;
import com.github.thedeathlycow.frostiful.entity.effect.FrostifulStatusEffects;
import com.github.thedeathlycow.frostiful.entity.loot.StrayLootTableModifier;
import com.github.thedeathlycow.frostiful.item.FrostifulItems;
import com.github.thedeathlycow.frostiful.particle.FrostifulParticleTypes;
import com.github.thedeathlycow.frostiful.server.command.FrostCommand;
import com.github.thedeathlycow.frostiful.server.command.FrostifulCommand;
import com.github.thedeathlycow.frostiful.sound.FrostifulSoundEvents;
import com.github.thedeathlycow.frostiful.world.FrostifulGameRules;
import com.github.thedeathlycow.frostiful.world.gen.feature.FrostifulPlacedFeatures;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        AutoConfig.register(FrostifulConfig.class, GsonConfigSerializer::new);

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    FrostifulCommand.register(dispatcher);
                    FrostCommand.register(dispatcher);
                }
        );

        LootTableEvents.MODIFY.register(StrayLootTableModifier::addFrostTippedArrows);

        FrostifulEntityAttributes.registerAttributes();
        FrostifulDamageSource.registerDamageSources();
        FrostifulBlocks.registerBlocks();
        FrostifulItems.registerItems();
        FrostifulEntityTypes.registerEntities();
        FrostifulGameRules.registerGamerules();
        FrostifulSoundEvents.registerSoundEvents();
        FrostifulPlacedFeatures.placeFeatures();
        FrostifulStatusEffects.registerStatusEffects();
        FrostifulEnchantments.registerEnchantments();
        FrostifulParticleTypes.registerParticleTypes();

        LOGGER.info("Initialized Frostiful!");
    }

    public static FrostifulConfig getConfig() {
        return AutoConfig.getConfigHolder(FrostifulConfig.class).getConfig();
    }

    /**
     * Creates a new {@link Identifier} in the namespace {@value MODID}.
     * @param path The path of the identifier
     * @return Returns a new {@link Identifier}
     */
    public static Identifier id(String path) {
        return new Identifier(Frostiful.MODID, path);
    }
}
