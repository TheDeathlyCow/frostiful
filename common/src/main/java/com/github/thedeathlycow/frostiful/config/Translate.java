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
        return categoryKey(handler, FrostifulConfigYACL.MAIN_CATEGORY_NAME);
    }

    public static String groupKey(ConfigClassHandler<?> handler, String category, String group) {
        return prefixKey(handler) + ".category." + category + ".group." + group;
    }

    public static String mainGroupKey(ConfigClassHandler<?> handler, String group) {
        return groupKey(handler, FrostifulConfigYACL.MAIN_CATEGORY_NAME, group);
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