package com.github.thedeathlycow.frostiful.particle;

import com.mojang.brigadier.StringReader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public class WindParticleEffect implements ParticleEffect {

    public static final Factory FACTORY = new Factory();

    @Override
    public ParticleType<?> getType() {
        return FParticleTypes.WIND;
    }

    @Override
    public void write(PacketByteBuf buf) {

    }

    @Override
    public String asString() {
        Identifier id = Registry.PARTICLE_TYPE.getId(this.getType());
        assert id != null;
        return String.format(Locale.ROOT, "%s", id);
    }

    @SuppressWarnings("deprecation")
    public static class Factory implements ParticleEffect.Factory<WindParticleEffect> {

        @Override
        public WindParticleEffect read(ParticleType<WindParticleEffect> type, StringReader reader) {
            return new WindParticleEffect();
        }

        @Override
        public WindParticleEffect read(ParticleType<WindParticleEffect> type, PacketByteBuf buf) {
            return new WindParticleEffect();
        }
    }
}
