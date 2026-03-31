package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.IntField;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class IcicleConfig {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("icicle.json5");

    public static final ConfigClassHandler<IcicleConfig> HANDLER = ConfigClassHandler.createBuilder(IcicleConfig.class)
            .id(Frostiful.id("common/icicle"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    private static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Glacial Arrow freezing")
    @SerialEntry(comment = "The temperature point reduction applied to a target struck by a Glacial Arrow. Must be at least 0.")
    @IntField(min = 0)
    int frostArrowFreezeAmount = 1000;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Thrown Icicle freezing")
    @SerialEntry(comment = "The temperature point reduction applied to a target struck by a Thrown Icicle. Must be at least 0.")
    @IntField(min = 0)
    int thrownIcicleFreezeAmount = 1500;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Icicle Collision freezing")
    @SerialEntry(comment = "The temperature point reduction applied to a target that falls on an icicle or is struck by a falling icicle. Must be at least 0.")
    @IntField(min = 0)
    int icicleCollisionFreezeAmount = 3000;


    public int getFrostArrowFreezeAmount() {
        return frostArrowFreezeAmount;
    }

    public int getThrownIcicleFreezeAmount() {
        return thrownIcicleFreezeAmount;
    }

    public int getIcicleCollisionFreezeAmount() {
        return icicleCollisionFreezeAmount;
    }
}