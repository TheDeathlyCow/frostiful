package com.github.thedeathlycow.frostiful.item.enchantment;

import com.github.thedeathlycow.frostiful.particle.HeatDrainParticleEffect;
import com.github.thedeathlycow.frostiful.util.FMathHelper;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Function;

public record HeatDrainEnchantmentEffect(
        EnchantmentLevelBasedValue heatToDrain,
        float efficiency
) implements EnchantmentEntityEffect {

    public static final MapCodec<HeatDrainEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    EnchantmentLevelBasedValue.CODEC
                            .fieldOf("heat_to_drain")
                            .forGetter(HeatDrainEnchantmentEffect::heatToDrain),
                    rangedFloat(0.0f, 1f, v -> "Value must be between 0 and 1 (inclusive): " + v)
                            .fieldOf("efficiency")
                            .forGetter(HeatDrainEnchantmentEffect::efficiency)
            ).apply(instance, HeatDrainEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {

        if (user instanceof TemperatureAware temperatureAware && temperatureAware.thermoo$canFreeze()) {

            int heatDrainedFromTarget = MathHelper.floor(this.heatToDrain.getValue(level));
            temperatureAware.thermoo$addTemperature(-heatDrainedFromTarget, HeatingModes.ACTIVE);

            LivingEntity owner = context.owner();
            if (owner == null) {
                return;
            }

            int heatAddedToOwner = MathHelper.floor(heatDrainedFromTarget * this.efficiency);
            owner.thermoo$addTemperature(heatAddedToOwner, HeatingModes.ACTIVE);

            if (owner.thermoo$isWarm()) {
                owner.thermoo$setTemperature(0);
            }

            if (heatDrainedFromTarget != 0) {
                addHeatDrainParticles(owner, user, level);
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    public static void addHeatDrainParticles(Entity destination, Entity source, int level) {
        World world = destination.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            addHeatDrainParticles(serverWorld, destination, source, level, 0.5);
        }
    }

    public static void addHeatDrainParticles(ServerWorld serverWorld, Entity destination, Entity source, int level, double delta) {
        Vec3d from = FMathHelper.getMidPoint(source.getEyePos(), source.getPos());
        final int numParticles = (level << 1) + 5;

        double fromX = from.getX();
        double fromY = from.getY();
        double fromZ = from.getZ();
        var effect = new HeatDrainParticleEffect(destination.getEyePos());
        serverWorld.spawnParticles(effect, fromX, fromY, fromZ, numParticles, delta, delta, delta, 0.3);
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
