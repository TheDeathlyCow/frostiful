/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.client.config.handler.ClientConfigHandlers;
import com.github.thedeathlycow.frostiful.client.config.section.DisplaySettings;
import com.github.thedeathlycow.frostiful.config.Translate;
import com.github.thedeathlycow.frostiful.config.handler.ConfigHandlers;
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

    public static final String ACCESSIBILITY_CATEGORY = Translate.mainCategoryKey(ClientConfigHandlers.ACCESSIBILITY);
    public static final String DISPLAY_CATEGORY = Translate.mainCategoryKey(ClientConfigHandlers.DISPLAY);
    public static final String TEMPERATURE_SOURCE_CATEGORY = Translate.mainCategoryKey(ConfigHandlers.TEMPERATURE_SOURCE);
    public static final String ENVIRONMENT_CATEGORY = Translate.mainCategoryKey(ConfigHandlers.ENVIRONMENT);
    public static final String SOAKING_CATEGORY = Translate.mainCategoryKey(ConfigHandlers.SOAKING);
    public static final String BLOCK_CATEGORY = Translate.mainCategoryKey(ConfigHandlers.BLOCK);
    public static final String ENTITY_CATEGORY = Translate.mainCategoryKey(ConfigHandlers.ENTITY);
    public static final String ITEM_CATEGORY = Translate.mainCategoryKey(ConfigHandlers.ITEM);
    public static final String WEATHER_CATEGORY = Translate.mainCategoryKey(ConfigHandlers.WEATHER);

    public static final String ACCESSIBILITY_DESC = Translate.descKey(ClientConfigHandlers.ACCESSIBILITY);
    public static final String DISPLAY_DESC = Translate.descKey(ClientConfigHandlers.DISPLAY);
    public static final String TEMPERATURE_SOURCE_DESC = Translate.descKey(ConfigHandlers.TEMPERATURE_SOURCE);
    public static final String ENVIRONMENT_DESC = Translate.descKey(ConfigHandlers.ENVIRONMENT);
    public static final String SOAKING_DESC = Translate.descKey(ConfigHandlers.SOAKING);
    public static final String BLOCK_DESC = Translate.descKey(ConfigHandlers.BLOCK);
    public static final String ENTITY_DESC = Translate.descKey(ConfigHandlers.ENTITY);
    public static final String ITEM_DESC = Translate.descKey(ConfigHandlers.ITEM);
    public static final String WEATHER_DESC = Translate.descKey(ConfigHandlers.WEATHER);

    public static Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.createBuilder()
                .title(Component.translatable(TITLE))
                .category(
                        ConfigCategory.createBuilder()
                                .name(Component.translatable(TITLE))
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable(CLIENT_TITLE))
                                                .option(createSubsectionButton(ClientConfigHandlers.ACCESSIBILITY, ACCESSIBILITY_CATEGORY, ACCESSIBILITY_DESC))
                                                .option(createSubsectionButton(ClientConfigHandlers.DISPLAY, DISPLAY_CATEGORY, DISPLAY_DESC))
                                                .build()
                                )
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable(COMMON_TITLE))
                                                .option(createSubsectionButton(ConfigHandlers.TEMPERATURE_SOURCE, TEMPERATURE_SOURCE_CATEGORY, TEMPERATURE_SOURCE_DESC))
                                                .option(createSubsectionButton(ConfigHandlers.ENVIRONMENT, ENVIRONMENT_CATEGORY, ENVIRONMENT_DESC))
                                                .option(createSubsectionButton(ConfigHandlers.SOAKING, SOAKING_CATEGORY, SOAKING_DESC))
                                                .option(createSubsectionButton(ConfigHandlers.BLOCK, BLOCK_CATEGORY, BLOCK_DESC))
                                                .option(createSubsectionButton(ConfigHandlers.ENTITY, ENTITY_CATEGORY, ENTITY_DESC))
                                                .option(createSubsectionButton(ConfigHandlers.ITEM, ITEM_CATEGORY, ITEM_DESC))
                                                .option(createSubsectionButton(ConfigHandlers.WEATHER, WEATHER_CATEGORY, WEATHER_DESC))
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
