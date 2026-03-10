package com.github.thedeathlycow.frostiful.neoforge;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.AccessoriesIntegration;
import com.github.thedeathlycow.frostiful.entity.BiterEntity;
import com.github.thedeathlycow.frostiful.entity.ChillagerEntity;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FrostifulEntityAttachments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod(Frostiful.MODID)
public class FrostifulMod {
    public FrostifulMod(IEventBus modBus) {
        modBus.addListener(FrostifulMod::createDefaultAttributes);
        FrostifulEntityAttachments.REGISTRY.register(modBus);
        AccessoriesIntegration.removeAccessoriesRenderer();
    }

    private static void createDefaultAttributes(EntityAttributeCreationEvent event) {
        event.put(FEntityTypes.FROSTOLOGER, FrostologerEntity.createFrostologerAttributes().build());
        event.put(FEntityTypes.CHILLAGER, ChillagerEntity.createChillagerAttributes().build());
        event.put(FEntityTypes.BITER, BiterEntity.createBiterAttributes().build());
    }
}