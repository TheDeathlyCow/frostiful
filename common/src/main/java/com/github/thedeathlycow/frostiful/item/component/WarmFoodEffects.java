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

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.github.thedeathlycow.frostiful.registry.FMobEffects;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record WarmFoodEffects(
        List<MobEffectInstance> effects,
        Optional<Component> tooltip
) {
    public static final Codec<WarmFoodEffects> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            MobEffectInstance.CODEC.listOf()
                                    .optionalFieldOf("effects", List.of())
                                    .forGetter(WarmFoodEffects::effects),
                            ComponentSerialization.CODEC
                                    .optionalFieldOf("tooltip")
                                    .forGetter(WarmFoodEffects::tooltip)
                    )
                    .apply(instance, WarmFoodEffects::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, WarmFoodEffects> STREAM_CODEC = StreamCodec.composite(
            MobEffectInstance.STREAM_CODEC.apply(ByteBufCodecs.list()),
            WarmFoodEffects::effects,
            ByteBufCodecs.optional(ComponentSerialization.STREAM_CODEC),
            WarmFoodEffects::tooltip,
            WarmFoodEffects::new
    );

    public static final String WARMING_TOOLTIP_KEY = "item.frostiful.warming.tooltip";
    public static final String HOT_TOOLTIP_KEY = "item.frostiful.hot.tooltip";

    public static final WarmFoodEffects DEFAULT = new WarmFoodEffects(List.of());

    public static final WarmFoodEffects WARMING_FOOD = new WarmFoodEffects(
            List.of(
                    new MobEffectInstance(FMobEffects.WARMING, 5 * 20, 0),
                    new MobEffectInstance(FMobEffects.WARMHEARTED, 30 * 20, 0)
            ),
            Component.translatable(WARMING_TOOLTIP_KEY).setStyle(TextStyles.GOLD_NONITALIC)
    );

    public static final WarmFoodEffects HOT_FOOD = new WarmFoodEffects(
            List.of(
                    new MobEffectInstance(FMobEffects.WARMING, 5 * 20, 1),
                    new MobEffectInstance(FMobEffects.WARMHEARTED, 60 * 20, 0)
            ),
            Component.translatable(HOT_TOOLTIP_KEY).setStyle(TextStyles.RED_NONITALIC)
    );

    public WarmFoodEffects(List<MobEffectInstance> effects, Component tooltip) {
        this(effects, Optional.of(tooltip));
    }

    public WarmFoodEffects(List<MobEffectInstance> effects) {
        this(effects, Optional.empty());
    }

    @Nullable
    public static WarmFoodEffects getByTag(ItemStack stack) {
        if (stack.has(FDataComponentTypes.WARM_FOOD_EFFECTS)) {
            return stack.get(FDataComponentTypes.WARM_FOOD_EFFECTS);
        } else if (stack.is(FItemTags.COLD_FOODS)) {
            return DEFAULT;
        } else if (stack.is(FItemTags.HOT_FOODS)) {
            return HOT_FOOD;
        } else if (stack.is(FItemTags.WARM_FOODS)) {
            return WARMING_FOOD;
        } else {
            return DEFAULT;
        }
    }

    public void forEachEffect(final Consumer<MobEffectInstance> consumer, final float durationScale) {
        for (MobEffectInstance effect : this.effects) {
            // IMPORTANT!
            // withScaledDuration produces a copy so it can be consumed without modifying the instance stored in the
            // component
            consumer.accept(effect.withScaledDuration(durationScale));
        }
    }

    public static float getDurationScale(ItemStack stack) {
        float durationScale = stack.getOrDefault(DataComponents.POTION_DURATION_SCALE, 1.0f);
        durationScale *= FrostifulConfigYACL.itemSettings().warmingFoodDurationMultiplier();
        return durationScale;
    }

    public void onConsume(Level level, LivingEntity user, ItemStack stack) {
        if (level instanceof ServerLevel serverLevel) {

            this.forEachEffect(effect -> {
                MobEffect effectType = effect.getEffect().value();

                if (effectType.isInstantenous()) {
                    Player player = user instanceof Player playerEntity ? playerEntity : null;
                    effectType.applyInstantenousEffect(serverLevel, player, player, user, effect.getAmplifier(), 1.0f);
                } else {
                    user.addEffect(effect);
                }
            }, getDurationScale(stack));
        }
    }

    public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> lines) {
        if (tooltip.isEmpty()) {
            return;
        }

        Component line = tooltip.orElseThrow();

        if (tooltipFlag.isAdvanced()) {
            SimpleTooltipComponent.addTooltipBeforeAdvanced(stack, lines, line);
            this.addEffectTooltip(stack, tooltipContext.tickRate(), l -> SimpleTooltipComponent.addTooltipBeforeAdvanced(stack, lines, l));
        } else {
            lines.add(line);
        }
    }

    private void addEffectTooltip(ItemStack stack, float tickRate, Consumer<Component> appender) {
        float durationScale = getDurationScale(stack);

        Style style = tooltip.map(Component::getStyle).orElse(Style.EMPTY);

        this.forEachEffect(
                effect -> {
                    MutableComponent line = PotionContents.getPotionDescription(effect.getEffect(), effect.getAmplifier());

                    if (!effect.endsWithin(20)) {
                        line = Component.translatable("potion.withDuration", line, MobEffectUtil.formatDuration(effect, durationScale, tickRate));
                    }

                    line.setStyle(style);
                    appender.accept(line);
                },
                durationScale
        );
    }
}