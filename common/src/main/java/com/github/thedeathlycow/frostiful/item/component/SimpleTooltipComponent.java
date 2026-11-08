/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record SimpleTooltipComponent(
        Component text
) implements TooltipProvider {
    public static final Codec<SimpleTooltipComponent> CODEC = ComponentSerialization.CODEC.xmap(
            SimpleTooltipComponent::new,
            SimpleTooltipComponent::text
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleTooltipComponent> STREAM_CODEC = StreamCodec.composite(
            ComponentSerialization.STREAM_CODEC,
            SimpleTooltipComponent::text,
            SimpleTooltipComponent::new
    );

    public static final String INERT_KEY = "item.frostiful.inert_frostology_cloak.tooltip";

    public static final Component INERT_TEXT = Component.translatable(INERT_KEY)
            .setStyle(TextStyles.INERT_FROSTOLOGY_CLOAK_TOOLTIP);

    public static final String ICE_LIKE_KEY = "item.frostiful.frostology_cloak.tooltip";

    public static final Component ICE_LIKE_TEXT = Component.translatable(ICE_LIKE_KEY)
            .setStyle(TextStyles.FROSTOLOGY_CLOAK_TOOLTIP);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        consumer.accept(text);
    }
}