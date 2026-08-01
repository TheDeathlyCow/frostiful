package com.github.thedeathlycow.frostiful.entity.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.ValidationContextSource;

import java.util.*;

public class FrozenByFrostWandTrigger extends SimpleCriterionTrigger<FrozenByFrostWandTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Collection<LivingEntity> frozenEntities) {
        List<LootContext> victimContexts = new ArrayList<>(frozenEntities.size());

        for (LivingEntity frozenEntity : frozenEntities) {
            victimContexts.add(EntityPredicate.createContext(player, frozenEntity));
        }

        this.trigger(player, triggerInstance -> triggerInstance.matches(victimContexts));
    }

    public record TriggerInstance(
            Optional<ContextAwarePredicate> player,
            List<ContextAwarePredicate> victims,
            MinMaxBounds.Ints entitiesFrozen
    ) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .optionalFieldOf("player")
                                        .forGetter(TriggerInstance::player),
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .listOf()
                                        .optionalFieldOf("victims", List.of())
                                        .forGetter(TriggerInstance::victims),
                                MinMaxBounds.Ints.CODEC
                                        .optionalFieldOf("entities_frozen", MinMaxBounds.Ints.ANY)
                                        .forGetter(TriggerInstance::entitiesFrozen)
                        )
                        .apply(instance, TriggerInstance::new)
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
        public void validate(ValidationContextSource validator) {
            SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
            Validatable.validate(validator.entityContext(), "victims", this.victims);
        }
    }
}