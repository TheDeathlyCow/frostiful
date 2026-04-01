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
    public static final String COMBAT_CATEGORY = Translate.mainCategoryKey(CombatConfig.HANDLER);
    public static final String ENVIRONMENT_CATEGORY = Translate.mainCategoryKey(EnvironmentConfig.HANDLER);
    public static final String FREEZING_CATEGORY = Translate.mainCategoryKey(FreezingConfig.HANDLER);
    public static final String ICICLE_CATEGORY = Translate.mainCategoryKey(IcicleConfig.HANDLER);
    public static final String BLOCK_CATEGORY = Translate.mainCategoryKey(BlockSettings.HANDLER);
    public static final String ENTITY_CATEGORY = Translate.mainCategoryKey(EntitySettings.HANDLER);
    public static final String ITEM_CATEGORY = Translate.mainCategoryKey(ItemSettings.HANDLER);
    public static final String WEATHER_CATEGORY = Translate.mainCategoryKey(WeatherSettings.HANDLER);

    public static final String DISPLAY_DESC = Translate.descKey(DisplaySettings.HANDLER);
    public static final String COMBAT_DESC = Translate.descKey(CombatConfig.HANDLER);
    public static final String ENVIRONMENT_DESC = Translate.descKey(EnvironmentConfig.HANDLER);
    public static final String FREEZING_DESC = Translate.descKey(FreezingConfig.HANDLER);
    public static final String ICICLE_DESC = Translate.descKey(IcicleConfig.HANDLER);
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
                                                .option(createSubsectionButton(EnvironmentConfig.HANDLER, ENVIRONMENT_CATEGORY, ENVIRONMENT_DESC))
                                                .option(createSubsectionButton(CombatConfig.HANDLER, COMBAT_CATEGORY, COMBAT_DESC))
                                                .option(createSubsectionButton(FreezingConfig.HANDLER, FREEZING_CATEGORY, FREEZING_DESC))
                                                .option(createSubsectionButton(IcicleConfig.HANDLER, ICICLE_CATEGORY, ICICLE_DESC))
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
