package com.github.thedeathlycow.frostiful.attributes;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FEntityAttributes {

    /**
     * Reduces incoming frost damage by a percentage. In units of 10%.
     */
    public static final EntityAttribute FROST_RESISTANCE = new ClampedEntityAttribute(getTranslateKey("generic.frost_resistance"), 00.0D, -10.0D, 10.0D).setTracked(true);

    /**
     * Determines the base amount of frost an entity can have before
     * they begin taking damage. In units of 140 ticks.
     */
    public static final EntityAttribute MAX_FROST = new ClampedEntityAttribute(getTranslateKey("generic.max_frost"), 40.0, 0.0, 8192.0D).setTracked(true);

    public static void registerAttributes() {
        register("generic.frost_resistance", FROST_RESISTANCE);
        register("generic.max_frost", MAX_FROST);
    }

    private static void register(String id, EntityAttribute attribute) {
        Registry.register(Registry.ATTRIBUTE, new Identifier(Frostiful.MODID, id), attribute);
    }

    private static String getTranslateKey(String attributeName) {
        return "attribute." + Frostiful.MODID + '.' + attributeName;
    }
}
