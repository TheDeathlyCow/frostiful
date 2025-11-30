package com.github.thedeathlycow.frostiful.entity.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.*;

public class FrozenByFrostWandCriterion extends SimpleCriterionTrigger<FrozenByFrostWandCriterion.Conditions> {

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayer player, Collection<LivingEntity> frozenEntities) {
        List<LootContext> victimContexts = new ArrayList<>(frozenEntities.size());

        for (LivingEntity frozenEntity : frozenEntities) {
            victimContexts.add(EntityPredicate.createContext(player, frozenEntity));
        }

        this.trigger(player, conditions -> conditions.matches(victimContexts));
    }

    public record Conditions(
            Optional<ContextAwarePredicate> player,
            List<ContextAwarePredicate> victims,
            MinMaxBounds.Ints entitiesFrozen
    ) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .optionalFieldOf("player")
                                        .forGetter(Conditions::player),
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .listOf()
                                        .optionalFieldOf("victims", List.of())
                                        .forGetter(Conditions::victims),
                                MinMaxBounds.Ints.CODEC
                                        .optionalFieldOf("entities_frozen", MinMaxBounds.Ints.ANY)
                                        .forGetter(Conditions::entitiesFrozen)
                        )
                        .apply(instance, Conditions::new)
        );

        /**
         *
         * Implementation: Finds the first victim that matches each predicate. If the predicate matches no victims,
         * returns false. Otherwise, checks the entities frozen count
         *
         * @param victims victims frozen
         * @return returns true
         */
        public boolean matches(Collection<LootContext> victims) {
            if (!this.victims.isEmpty()) {
                List<LootContext> unmatchedVictims = new ArrayList<>(victims);

                for (ContextAwarePredicate predicate : this.victims) {
                    boolean matched = false;

                    Iterator<LootContext> iterator = unmatchedVictims.iterator();
                    while (iterator.hasNext()) {
                        LootContext lootContext = iterator.next();
                        if (predicate.matches(lootContext)) {
                            iterator.remove();
                            matched = true;
                            break;
                        }
                    }

                    if (!matched) {
                        return false;
                    }
                }
            }
            return this.entitiesFrozen.matches(victims.size());
        }

        @Override
        public void validate(CriterionValidator validator) {
            SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
            validator.validateEntities(this.victims, ".victims");
        }
    }
}