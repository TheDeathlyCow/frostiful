package com.github.thedeathlycow.frostiful.entity.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class SunLichenDischargeCriterion extends SimpleCriterionTrigger<SunLichenDischargeCriterion.Conditions> {

    public void trigger(ServerPlayer player, int temperatureImparted) {
        this.trigger(player, conditions -> conditions.temperatureImparted.matches(temperatureImparted));
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
            Optional<ContextAwarePredicate> player,
            MinMaxBounds.Ints temperatureImparted
    ) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC
                                .optionalFieldOf("player")
                                .forGetter(Conditions::player),
                        MinMaxBounds.Ints.CODEC
                                .fieldOf("temperature_imparted")
                                .orElse(MinMaxBounds.Ints.ANY)
                                .forGetter(Conditions::temperatureImparted)
                ).apply(instance, Conditions::new)
        );
    }
}