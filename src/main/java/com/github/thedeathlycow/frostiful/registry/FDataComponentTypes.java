package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.item.attribute.FrostResistanceComponent;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.github.thedeathlycow.frostiful.item.component.IceLikeComponent;
import com.github.thedeathlycow.frostiful.item.component.SimpleTooltipComponent;
import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistryKeys;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.List;
import java.util.function.UnaryOperator;

public final class FDataComponentTypes {
    public static final DataComponentType<FrostResistanceComponent> FROST_RESISTANCE = register(
            "frost_resistance",
            builder -> builder
                    .persistent(FrostResistanceComponent.CODEC)
                    .networkSynchronized(FrostResistanceComponent.PACKET_CODEC)
                    .cacheEncoding()
    );

    public static final DataComponentType<CapeComponent> CAPE = register(
            "cape",
            builder -> builder
                    .persistent(CapeComponent.CODEC)
                    .networkSynchronized(CapeComponent.PACKET_CODEC)
                    .cacheEncoding()
    );

    public static final DataComponentType<IceLikeComponent> ICE_LIKE = register(
            "ice_like",
            builder -> builder
                    .persistent(IceLikeComponent.CODEC)
                    .networkSynchronized(IceLikeComponent.PACKET_CODEC)
                    .cacheEncoding()
    );

    public static final DataComponentType<HolderSet<TemperatureStatus>> ENABLE_TEMPERATURE_STATUSES = register(
            "enable_temperature_statuses",
            builder -> builder
                    .persistent(RegistryCodecs.homogeneousList(ThermooRegistryKeys.TEMPERATURE_STATUS))
                    .networkSynchronized(ByteBufCodecs.holderSet(ThermooRegistryKeys.TEMPERATURE_STATUS))
                    .cacheEncoding()
    );

    public static final DataComponentType<HolderSet<TemperatureStatus>> DISABLE_TEMPERATURE_STATUSES = register(
            "disable_temperature_statuses",
            builder -> builder
                    .persistent(RegistryCodecs.homogeneousList(ThermooRegistryKeys.TEMPERATURE_STATUS))
                    .networkSynchronized(ByteBufCodecs.holderSet(ThermooRegistryKeys.TEMPERATURE_STATUS))
                    .cacheEncoding()
    );

    public static final DataComponentType<SimpleTooltipComponent> SIMPLE_TOOLTIP = register(
            "simple_tooltip",
            builder -> builder
                    .persistent(SimpleTooltipComponent.CODEC)
                    .networkSynchronized(SimpleTooltipComponent.STREAM_CODEC)
                    .cacheEncoding()
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful item components");

        ItemComponentTooltipProviderRegistry.addLast(FDataComponentTypes.SIMPLE_TOOLTIP);

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

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Frostiful.id(id),
                builderOperator.apply(DataComponentType.builder()).build()
        );
    }

    private FDataComponentTypes() {

    }
}