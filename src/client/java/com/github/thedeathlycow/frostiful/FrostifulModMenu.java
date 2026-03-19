package com.github.thedeathlycow.frostiful;

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

    public static final String CLIENT_CATEGORY = Translate.mainCategoryKey(ClientConfig.HANDLER);
    public static final String COMBAT_CATEGORY = Translate.mainCategoryKey(CombatConfig.HANDLER);
    public static final String ENVIRONMENT_CATEGORY = Translate.mainCategoryKey(EnvironmentConfig.HANDLER);
    public static final String FREEZING_CATEGORY = Translate.mainCategoryKey(FreezingConfig.HANDLER);
    public static final String ICICLE_CATEGORY = Translate.mainCategoryKey(IcicleConfig.HANDLER);

    public static final String CLIENT_DESC = Translate.descKey(ClientConfig.HANDLER);
    public static final String COMBAT_DESC = Translate.descKey(CombatConfig.HANDLER);
    public static final String ENVIRONMENT_DESC = Translate.descKey(EnvironmentConfig.HANDLER);
    public static final String FREEZING_DESC = Translate.descKey(FreezingConfig.HANDLER);
    public static final String ICICLE_DESC = Translate.descKey(IcicleConfig.HANDLER);

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
                                                .option(createSubsectionButton(ClientConfig.HANDLER, CLIENT_CATEGORY, CLIENT_DESC))
                                                .build()
                                )
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable(COMMON_TITLE))
                                                .option(createSubsectionButton(CombatConfig.HANDLER, COMBAT_CATEGORY, COMBAT_DESC))
                                                .option(createSubsectionButton(CombatConfig.HANDLER, ENVIRONMENT_CATEGORY, ENVIRONMENT_DESC))
                                                .option(createSubsectionButton(CombatConfig.HANDLER, COMBAT_CATEGORY, COMBAT_DESC))
                                                .option(createSubsectionButton(CombatConfig.HANDLER, FREEZING_CATEGORY, FREEZING_DESC))
                                                .option(createSubsectionButton(CombatConfig.HANDLER, ICICLE_CATEGORY, ICICLE_DESC))
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
