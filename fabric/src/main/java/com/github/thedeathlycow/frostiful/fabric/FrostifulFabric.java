package com.github.thedeathlycow.frostiful.fabric;

import com.github.thedeathlycow.frostiful.entity.Biter;
import com.github.thedeathlycow.frostiful.entity.Chillager;
import com.github.thedeathlycow.frostiful.entity.frostologer.Frostologer;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class FrostifulFabric implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        FabricDefaultAttributeRegistry.register(FEntityTypes.FROSTOLOGER, Frostologer.createFrostologerAttributes());
        FabricDefaultAttributeRegistry.register(FEntityTypes.CHILLAGER, Chillager.createChillagerAttributes());
        FabricDefaultAttributeRegistry.register(FEntityTypes.BITER, Biter.createBiterAttributes());
    }
}