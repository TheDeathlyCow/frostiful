package com.github.thedeathlycow.frostiful.config;


import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class Translate {
    public static String prefixKey(ConfigClassHandler<?> handler) {
        return "yacl3.config." + handler.id().toString()    ;
    }

    public static String descKey(ConfigClassHandler<?> handler) {
        return prefixKey(handler) + ".desc";
    }

    public static String categoryKey(ConfigClassHandler<?> handler, String category) {
        return prefixKey(handler) + ".category." + category;
    }

    public static String mainCategoryKey(ConfigClassHandler<?> handler) {
        return categoryKey(handler, "main");
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Name {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface NoComment {

    }
}