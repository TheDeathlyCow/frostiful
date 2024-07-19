package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import com.github.thedeathlycow.frostiful.registry.FLootConditionTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.NumberRange;

public record RootedLootCondition(
        NumberRange.IntRange rootTicksRemaining
) implements LootCondition {

    public static final MapCodec<RootedLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    NumberRange.IntRange.CODEC
                            .fieldOf("root_ticks_remaining")
                            .forGetter(RootedLootCondition::rootTicksRemaining)
            ).apply(instance, RootedLootCondition::new)
    );

    @Override
    public LootConditionType getType() {
        return FLootConditionTypes.ROOTED;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);
        if (entity instanceof RootedEntity rootable) {
            return this.rootTicksRemaining.test(rootable.frostiful$getRootedTicks());
        }

        return false;
    }
}
