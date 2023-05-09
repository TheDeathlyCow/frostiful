package com.github.thedeathlycow.frostiful.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Locale;

public class HeatDrainParticleEffect implements ParticleEffect {

    public static final Factory FACTORY = new Factory();

    private final Vec3d destination;

    public HeatDrainParticleEffect(Vec3d destination) {
        this.destination = destination;
    }

    @Override
    public ParticleType<?> getType() {
        return FParticleTypes.HEAT_DRAIN;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(destination.x);
        buf.writeDouble(destination.y);
        buf.writeDouble(destination.z);
    }

    @Override
    public String asString() {
        Identifier id = Registries.PARTICLE_TYPE.getId(this.getType());
        assert id != null;
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", id, destination.x, destination.y, destination.z);
    }

    public Vec3d getDestination() {
        return destination;
    }

    @SuppressWarnings("deprecation")
    public static class Factory implements ParticleEffect.Factory<HeatDrainParticleEffect> {

        @Override
        public HeatDrainParticleEffect read(ParticleType<HeatDrainParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double x = reader.readDouble();
            reader.expect(' ');
            double y = reader.readDouble();
            reader.expect(' ');
            double z = reader.readDouble();
            return new HeatDrainParticleEffect(new Vec3d(x, y, z));
        }

        @Override
        public HeatDrainParticleEffect read(ParticleType<HeatDrainParticleEffect> type, PacketByteBuf buf) {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            return new HeatDrainParticleEffect(new Vec3d(x, y, z));
        }
    }
}
