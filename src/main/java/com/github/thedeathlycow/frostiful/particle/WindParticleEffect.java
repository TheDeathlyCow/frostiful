package com.github.thedeathlycow.frostiful.particle;

import com.github.thedeathlycow.frostiful.registry.FParticleTypes;
import com.mojang.brigadier.StringReader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Locale;

public class WindParticleEffect implements ParticleEffect {

    public static final Factory FACTORY = new Factory();

    private final boolean flipped;

    public WindParticleEffect(boolean flipped) {
        this.flipped = flipped;
    }

    @Override
    public ParticleType<?> getType() {
        return this.flipped ? FParticleTypes.WIND_FLIPPED : FParticleTypes.WIND;
    }

    @Override
    public void write(PacketByteBuf buf) {

    }

    @Override
    public String asString() {
        Identifier id = Registries.PARTICLE_TYPE.getId(this.getType());
        assert id != null;
        return String.format(Locale.ROOT, "%s", id);
    }

    @SuppressWarnings("deprecation")
    public static class Factory implements ParticleEffect.Factory<WindParticleEffect> {

        @Override
        public WindParticleEffect read(ParticleType<WindParticleEffect> type, StringReader reader) {
            return new WindParticleEffect(type == FParticleTypes.WIND_FLIPPED);
        }

        @Override
        public WindParticleEffect read(ParticleType<WindParticleEffect> type, PacketByteBuf buf) {
            return new WindParticleEffect(type == FParticleTypes.WIND_FLIPPED);
        }
    }
}
