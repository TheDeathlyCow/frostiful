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

package com.github.thedeathlycow.frostiful.item.enchantment;

import com.github.thedeathlycow.frostiful.particle.HeatDrainParticleEffect;
import com.github.thedeathlycow.frostiful.util.FMathHelper;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public record HeatDrainEnchantmentEffect(
        LevelBasedValue heatToDrain,
        float efficiency,
        boolean drainFromEnchanted
) implements EnchantmentEntityEffect {

    public static final MapCodec<HeatDrainEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    LevelBasedValue.CODEC
                            .fieldOf("heat_to_drain")
                            .forGetter(HeatDrainEnchantmentEffect::heatToDrain),
                    rangedFloat(0.0f, 1f, v -> "Value must be between 0 and 1 (inclusive): " + v)
                            .fieldOf("efficiency")
                            .forGetter(HeatDrainEnchantmentEffect::efficiency),
                    Codec.BOOL
                            .fieldOf("drain_from_enchanted")
                            .orElse(false)
                            .forGetter(HeatDrainEnchantmentEffect::drainFromEnchanted)
            ).apply(instance, HeatDrainEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
        LivingEntity owner = context.owner();
        if (owner != null && user instanceof LivingEntity livingVictim) {
            if (drainFromEnchanted) {
                // as in frozen touch curse
                this.drainHeat(owner, livingVictim, level);
            } else {
                // as in enervation
                this.drainHeat(livingVictim, owner, level);
            }
        }
    }

    private void drainHeat(LivingEntity source, LivingEntity destination, int level) {

        if (!source.thermoo$canFreeze()) {
            return;
        }

        int heatDrainedFromTarget = Mth.floor(this.heatToDrain.calculate(level));
        if (source.thermoo$isCold()) {
            source.thermoo$addTemperature(
                    -heatDrainedFromTarget,
                    source.level().thermoo$temperatureSources().create(
                            TemperatureSources.ACTIVE,
                            source
                    )
            );
        }

        if (destination.thermoo$isCold()) {
            int heatAddedToOwner = Mth.floor(heatDrainedFromTarget * this.efficiency);
            destination.thermoo$addTemperature(heatAddedToOwner, source.level().thermoo$temperatureSources().create(
                    TemperatureSources.ACTIVE,
                    source
            ));
        }

        if (heatDrainedFromTarget != 0) {
            addHeatDrainParticles(source, destination, level);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

    public static void addHeatDrainParticles(LivingEntity source, LivingEntity destination, int level) {
        Level world = destination.level();
        if (world instanceof ServerLevel serverWorld) {
            addHeatDrainParticles(serverWorld, source, destination, level, 0.5);
        }
    }

    public static void addHeatDrainParticles(
            ServerLevel serverWorld,
            LivingEntity source, LivingEntity destination,
            int level, double delta
    ) {
        Vec3 from = FMathHelper.getMidPoint(source.getEyePosition(), source.position());
        final int numParticles = (level * 3) + 15;

        double fromX = from.x();
        double fromY = from.y();
        double fromZ = from.z();
        var effect = new HeatDrainParticleEffect(destination.getEyePosition());
        serverWorld.sendParticles(effect, fromX, fromY, fromZ, numParticles, delta, delta, delta, 0.3);
    }

    private static Codec<Float> rangedFloat(float min, float max, Function<Float, String> messageFactory) {
        return Codec.FLOAT
                .validate(
                        value -> value.compareTo(min) >= 0 && value.compareTo(max) <= 0
                                ? DataResult.success(value)
                                : DataResult.error(() -> messageFactory.apply(value))
                );
    }
}
