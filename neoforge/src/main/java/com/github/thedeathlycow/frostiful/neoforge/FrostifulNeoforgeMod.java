package com.github.thedeathlycow.frostiful.neoforge;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.Biter;
import com.github.thedeathlycow.frostiful.entity.Chillager;
import com.github.thedeathlycow.frostiful.entity.frostologer.Frostologer;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod(Frostiful.MODID)
public class FrostifulNeoforgeMod {
    public FrostifulNeoforgeMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(FrostifulNeoforgeMod::registerEntityTypeAttributes);
    }

    private static void registerEntityTypeAttributes(EntityAttributeCreationEvent event) {
        event.put(FEntityTypes.FROSTOLOGER, Frostologer.createFrostologerAttributes().build());
        event.put(FEntityTypes.CHILLAGER, Chillager.createChillagerAttributes().build());
        event.put(FEntityTypes.BITER, Biter.createBiterAttributes().build());
    }
}