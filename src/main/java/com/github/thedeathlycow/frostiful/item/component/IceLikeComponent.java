package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public record IceLikeComponent(
        TagKey<DamageType> blockedDamageTypes
) implements TooltipProvider {
    public static final IceLikeComponent DEFAULT = new IceLikeComponent(DamageTypeTags.IS_FREEZING);

    public static final Codec<IceLikeComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    TagKey.hashedCodec(Registries.DAMAGE_TYPE)
                            .optionalFieldOf("block_damage_types", DamageTypeTags.IS_FREEZING)
                            .forGetter(IceLikeComponent::blockedDamageTypes)
            ).apply(instance, IceLikeComponent::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, IceLikeComponent> PACKET_CODEC = StreamCodec.composite(
            TagKey.streamCodec(Registries.DAMAGE_TYPE),
            IceLikeComponent::blockedDamageTypes,
            IceLikeComponent::new
    );

    public static boolean isWearing(LivingEntity entity) {
        return !getAllEquipped(entity).isEmpty();
    }

    public static List<IceLikeComponent> getAllEquipped(LivingEntity entity) {
        List<IceLikeComponent> components = new ArrayList<>();

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            IceLikeComponent component = stack.get(FDataComponentTypes.ICE_LIKE);
            if (!stack.isEmpty() && component != null) {
                components.add(component);
            }
        }

        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.TRINKETS_ID)) {
            components.addAll(
                    TrinketsIntegration.getEquippedTrinket(entity, FDataComponentTypes.ICE_LIKE)
                            .stream()
                            .map(p -> p.getB().get(FDataComponentTypes.ICE_LIKE))
                            .toList()
            );
        }

        return components;
    }

    public boolean blockDamage(DamageSource source) {
        return source.is(this.blockedDamageTypes);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
        textConsumer.accept(
                Component.translatable("item.frostiful.frostology_cloak.tooltip")
                        .setStyle(TextStyles.FROSTOLOGY_CLOAK_TOOLTIP)
        );
    }
}