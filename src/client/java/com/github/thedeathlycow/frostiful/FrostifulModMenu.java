package com.github.thedeathlycow.frostiful;

import com.github.thedeathlycow.frostiful.client.config.section.DisplaySettings;
import com.github.thedeathlycow.frostiful.config.Translate;
import com.github.thedeathlycow.frostiful.config.section.*;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class FrostifulModMenu implements ModMenuApi {
    public static final String TITLE = "frostiful.title";
    public static final String CLIENT_TITLE = "frostiful.config.client.title";
    public static final String COMMON_TITLE = "frostiful.config.common.title";

    public static final String DISPLAY_CATEGORY = Translate.mainCategoryKey(DisplaySettings.HANDLER);
    public static final String TEMPERATURE_SOURCE_CATEGORY = Translate.mainCategoryKey(TemperatureSourceSettings.HANDLER);
    public static final String ENVIRONMENT_CATEGORY = Translate.mainCategoryKey(EnvironmentSettings.HANDLER);
    public static final String SOAKING_CATEGORY = Translate.mainCategoryKey(SoakingSettings.HANDLER);
    public static final String BLOCK_CATEGORY = Translate.mainCategoryKey(BlockSettings.HANDLER);
    public static final String ENTITY_CATEGORY = Translate.mainCategoryKey(EntitySettings.HANDLER);
    public static final String ITEM_CATEGORY = Translate.mainCategoryKey(ItemSettings.HANDLER);
    public static final String WEATHER_CATEGORY = Translate.mainCategoryKey(WeatherSettings.HANDLER);

    public static final String DISPLAY_DESC = Translate.descKey(DisplaySettings.HANDLER);
    public static final String TEMPERATURE_SOURCE_DESC = Translate.descKey(TemperatureSourceSettings.HANDLER);
    public static final String ENVIRONMENT_DESC = Translate.descKey(EnvironmentSettings.HANDLER);
    public static final String SOAKING_DESC = Translate.descKey(SoakingSettings.HANDLER);
    public static final String BLOCK_DESC = Translate.descKey(BlockSettings.HANDLER);
    public static final String ENTITY_DESC = Translate.descKey(EntitySettings.HANDLER);
    public static final String ITEM_DESC = Translate.descKey(ItemSettings.HANDLER);
    public static final String WEATHER_DESC = Translate.descKey(WeatherSettings.HANDLER);

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.createBuilder()
                .title(Component.translatable(TITLE))
                .category(
                        ConfigCategory.createBuilder()
                                .name(Component.translatable(TITLE))
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable(CLIENT_TITLE))
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
