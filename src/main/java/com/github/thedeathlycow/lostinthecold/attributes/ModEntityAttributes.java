package com.github.thedeathlycow.lostinthecold.attributes;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntityAttributes {

    /**
     * Frost resistance is an attribute that determines the amount of time (in increments of 30 seconds) that a player
     * can last in the cold.
     */
    public static final EntityAttribute FROST_RESISTANCE = register("generic.frost_resistance", (new ClampedEntityAttribute("attribute." + LostInTheCold.MODID + ".generic.frost_resistance", 3.0D, 0.0D, 2048.0D)).setTracked(true));

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, new Identifier(LostInTheCold.MODID, id), attribute);
    }
}
