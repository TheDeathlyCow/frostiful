package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record IceLikeComponent(
        TagKey<DamageType> blockedDamageTypes
) implements TooltipAppender {
    public static final IceLikeComponent DEFAULT = new IceLikeComponent(DamageTypeTags.IS_FREEZING);

    public static final Codec<IceLikeComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    TagKey.codec(RegistryKeys.DAMAGE_TYPE)
                            .optionalFieldOf("block_damage_types", DamageTypeTags.IS_FREEZING)
                            .forGetter(IceLikeComponent::blockedDamageTypes)
            ).apply(instance, IceLikeComponent::new)
    );

    public static final PacketCodec<RegistryByteBuf, IceLikeComponent> PACKET_CODEC = PacketCodec.tuple(
            TagKey.packetCodec(RegistryKeys.DAMAGE_TYPE),
            IceLikeComponent::blockedDamageTypes,
            IceLikeComponent::new
    );

    public static boolean isWearing(LivingEntity entity) {
        return !getAllEquipped(entity).isEmpty();
    }

    public static List<IceLikeComponent> getAllEquipped(LivingEntity entity) {
        List<IceLikeComponent> components = new ArrayList<>();

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getEquippedStack(slot);
            IceLikeComponent component = stack.get(FDataComponentTypes.ICE_LIKE);
            if (!stack.isEmpty() && component != null) {
                components.add(component);
            }
        }

        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.TRINKETS_ID)) {
            components.addAll(
                    TrinketsIntegration.getEquippedTrinket(entity, FDataComponentTypes.ICE_LIKE)
                            .stream()
                            .map(p -> p.getRight().get(FDataComponentTypes.ICE_LIKE))
                            .toList()
            );
        }

        return components;
    }

    public boolean blockDamage(DamageSource source) {
        return source.isIn(this.blockedDamageTypes);
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(
                Text.translatable("item.frostiful.frostology_cloak.tooltip")
                        .setStyle(TextStyles.FROSTOLOGY_CLOAK_TOOLTIP)
        );
    }
}