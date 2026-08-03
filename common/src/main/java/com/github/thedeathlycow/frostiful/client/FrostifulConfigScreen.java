package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.client.config.section.AccessibilitySettings;
import com.github.thedeathlycow.frostiful.client.config.section.DisplaySettings;
import com.github.thedeathlycow.frostiful.config.Translate;
import com.github.thedeathlycow.frostiful.config.section.*;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Function;


public class FrostifulConfigScreen {
    public static final String TITLE = "frostiful.title";
    public static final String CLIENT_TITLE = "frostiful.config.client.title";
    public static final String COMMON_TITLE = "frostiful.config.common.title";

    public static final String ACCESSIBILITY_CATEGORY = Translate.mainCategoryKey(AccessibilitySettings.HANDLER);
    public static final String DISPLAY_CATEGORY = Translate.mainCategoryKey(DisplaySettings.HANDLER);
    public static final String TEMPERATURE_SOURCE_CATEGORY = Translate.mainCategoryKey(TemperatureSourceSettings.HANDLER);
    public static final String ENVIRONMENT_CATEGORY = Translate.mainCategoryKey(EnvironmentSettings.HANDLER);
    public static final String SOAKING_CATEGORY = Translate.mainCategoryKey(SoakingSettings.HANDLER);
    public static final String BLOCK_CATEGORY = Translate.mainCategoryKey(BlockSettings.HANDLER);
    public static final String ENTITY_CATEGORY = Translate.mainCategoryKey(EntitySettings.HANDLER);
    public static final String ITEM_CATEGORY = Translate.mainCategoryKey(ItemSettings.HANDLER);
    public static final String WEATHER_CATEGORY = Translate.mainCategoryKey(WeatherSettings.HANDLER);

    public static final String ACCESSIBILITY_DESC = Translate.descKey(AccessibilitySettings.HANDLER);
    public static final String DISPLAY_DESC = Translate.descKey(DisplaySettings.HANDLER);
    public static final String TEMPERATURE_SOURCE_DESC = Translate.descKey(TemperatureSourceSettings.HANDLER);
    public static final String ENVIRONMENT_DESC = Translate.descKey(EnvironmentSettings.HANDLER);
    public static final String SOAKING_DESC = Translate.descKey(SoakingSettings.HANDLER);
    public static final String BLOCK_DESC = Translate.descKey(BlockSettings.HANDLER);
    public static final String ENTITY_DESC = Translate.descKey(EntitySettings.HANDLER);
    public static final String ITEM_DESC = Translate.descKey(ItemSettings.HANDLER);
    public static final String WEATHER_DESC = Translate.descKey(WeatherSettings.HANDLER);

    public static Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.createBuilder()
                .title(Component.translatable(TITLE))
                .category(
                        ConfigCategory.createBuilder()
                                .name(Component.translatable(TITLE))
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable(CLIENT_TITLE))
                                                .option(createSubsectionButton(AccessibilitySettings.HANDLER, ACCESSIBILITY_CATEGORY, ACCESSIBILITY_DESC))
                                                .option(createSubsectionButton(DisplaySettings.HANDLER, DISPLAY_CATEGORY, DISPLAY_DESC))
                                                .build()
                                )
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable(COMMON_TITLE))
                                                .option(createSubsectionButton(TemperatureSourceSettings.HANDLER, TEMPERATURE_SOURCE_CATEGORY, TEMPERATURE_SOURCE_DESC))
                                                .option(createSubsectionButton(EnvironmentSettings.HANDLER, ENVIRONMENT_CATEGORY, ENVIRONMENT_DESC))
                                                .option(createSubsectionButton(SoakingSettings.HANDLER, SOAKING_CATEGORY, SOAKING_DESC))
                                                .option(createSubsectionButton(BlockSettings.HANDLER, BLOCK_CATEGORY, BLOCK_DESC))
                                                .option(createSubsectionButton(EntitySettings.HANDLER, ENTITY_CATEGORY, ENTITY_DESC))
                                                .option(createSubsectionButton(ItemSettings.HANDLER, ITEM_CATEGORY, ITEM_DESC))
                                                .option(createSubsectionButton(WeatherSettings.HANDLER, WEATHER_CATEGORY, WEATHER_DESC))
                                                .build()
                                )
                                .build()
                )
                .build()
                .generateScreen(parent);
    }

    private static ButtonOption createSubsectionButton(ConfigClassHandler<?> handler, String titleKey, String descKey) {
        return ButtonOption.createBuilder()
                .name(Component.translatable(titleKey))
                .description(
                        OptionDescription.createBuilder()
                                .text(Component.translatable(descKey))
                                .build()
                )
                .text(Component.literal(""))
                .action((yaclScreen, buttonOption) -> {
                    Minecraft.getInstance()
                            .setScreen(handler
                                    .generateGui()
                                    .generateScreen(yaclScreen));
                }).build();
    }
}
