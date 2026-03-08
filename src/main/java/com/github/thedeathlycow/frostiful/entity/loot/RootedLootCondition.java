package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.entity.attachment.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.registry.FLootConditionTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record RootedLootCondition(
        MinMaxBounds.Ints rootTicksRemaining
) implements LootItemCondition {

    public static final MapCodec<RootedLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    MinMaxBounds.Ints.CODEC
                            .fieldOf("root_ticks_remaining")
                            .forGetter(RootedLootCondition::rootTicksRemaining)
            ).apply(instance, RootedLootCondition::new)
    );

    @Override
    public LootItemConditionType getType() {
        return FLootConditionTypes.ROOTED;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (entity instanceof LivingEntity livingEntity) {
            FrostWandRootComponent component = FrostWandRootComponent.get(livingEntity);
            return this.rootTicksRemaining.matches(component.getRootedTicks());
        }

        return false;
    }
}
