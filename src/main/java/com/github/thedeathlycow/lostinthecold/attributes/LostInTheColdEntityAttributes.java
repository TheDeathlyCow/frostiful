package com.github.thedeathlycow.lostinthecold.attributes;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LostInTheColdEntityAttributes {

    /**
     * Reduces incoming frost damage by a percentage. In units of 10%.
     */
    public static final EntityAttribute FROST_RESISTANCE = register("generic.frost_resistance", (new ClampedEntityAttribute(getTranslateKey("generic.frost_resistance"), 0.0D, 0.0D, 10.0D)).setTracked(true));

    /**
     * Determines the base amount of frost an entity can have before
     * they begin taking damage. In units of 140 ticks.
     */
    public static final EntityAttribute MAX_FROST = register("generic.max_frost", (new ClampedEntityAttribute(getTranslateKey("generic.max_frost"), 1.0, 0.0, 1048.0D)).setTracked(true));

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, new Identifier(LostInTheCold.MODID, id), attribute);
    }

    private static String getTranslateKey(String attributeName) {
        return "attribute." + LostInTheCold.MODID + '.' + attributeName;
    }
}
