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