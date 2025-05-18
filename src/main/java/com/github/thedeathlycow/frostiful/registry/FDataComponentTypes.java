package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.item.attribute.FrostResistanceComponent;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.github.thedeathlycow.frostiful.item.component.IceLikeComponent;
import com.github.thedeathlycow.frostiful.item.component.InertTooltipComponent;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;
import java.util.function.UnaryOperator;

public final class FDataComponentTypes {
    public static final ComponentType<FrostResistanceComponent> FROST_RESISTANCE = register(
            "frost_resistance",
            builder -> builder
                    .codec(FrostResistanceComponent.CODEC)
                    .packetCodec(FrostResistanceComponent.PACKET_CODEC)
                    .cache()
    );

    public static final ComponentType<InertTooltipComponent> INERT_TOOLTIP = register(
            "inert_tooltip",
            builder -> builder
                    .codec(Codec.unit(InertTooltipComponent.INSTANCE))
                    .packetCodec(PacketCodec.unit(InertTooltipComponent.INSTANCE))
                    .cache()
    );

    public static final ComponentType<CapeComponent> CAPE = register(
            "cape",
            builder -> builder
                    .codec(CapeComponent.CODEC)
                    .packetCodec(CapeComponent.PACKET_CODEC)
                    .cache()
    );

    public static final ComponentType<IceLikeComponent> ICE_LIKE = register(
            "ice_like",
            builder -> builder
                    .codec(IceLikeComponent.CODEC)
                    .packetCodec(IceLikeComponent.PACKET_CODEC)
                    .cache()
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful item components");

        ComponentTooltipAppenderRegistry.addLast(FDataComponentTypes.INERT_TOOLTIP);
        ComponentTooltipAppenderRegistry.addLast(FDataComponentTypes.ICE_LIKE);

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            List<IceLikeComponent> components = IceLikeComponent.getAllEquipped(entity);
            for (IceLikeComponent component : components) {
                if (component.blockDamage(source)) {
                    return false;
                }
            }

            return true;
        });
    }

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Frostiful.id(id),
                builderOperator.apply(ComponentType.builder()).build()
        );
    }

    private FDataComponentTypes() {

    }
}