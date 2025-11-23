package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.registry.FLootConditionTypes;
import com.github.thedeathlycow.frostiful.survival.PassiveTemperatureEffects;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record LocationWarmthLootCondition(
        MinMaxBounds.Ints value
) implements LootItemCondition {

    public static final MapCodec<LocationWarmthLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    MinMaxBounds.Ints.CODEC
                            .fieldOf("value")
                            .orElse(MinMaxBounds.Ints.ANY)
                            .forGetter(LocationWarmthLootCondition::value)
            ).apply(instance, LocationWarmthLootCondition::new)
    );

    @Override
    public LootItemConditionType getType() {
        return FLootConditionTypes.LOCATION_WARMTH;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Level world = lootContext.getLevel();
        BlockPos pos = BlockPos.containing(Objects.requireNonNull(lootContext.getParamOrNull(LootContextParams.ORIGIN)));

        int areaWarmth = PassiveTemperatureEffects.getBlockLightTemperatureChange(world, pos);
        return this.value.matches(areaWarmth);
    }

    public static LootItemCondition.Builder builder(MinMaxBounds.Ints value) {
        return () -> new LocationWarmthLootCondition(value);
    }
}