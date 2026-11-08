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

package com.github.thedeathlycow.frostiful.datafix;

import net.minecraft.util.datafix.schemas.NamespacedSchema;

public final class FAttributeIdPrefixFix {
    private static final String PREFIX = "frostiful:generic.";

    public static String fixPrefixedAttributeIds(String id) {
        String normalizedID = NamespacedSchema.ensureNamespaced(id);

        String normalizedPrefix = NamespacedSchema.ensureNamespaced(PREFIX);
        if (normalizedID.startsWith(normalizedPrefix)) {
            return "frostiful:" + normalizedID.substring(normalizedPrefix.length());
        }

        return id;
    }

    private FAttributeIdPrefixFix() {

    }
}