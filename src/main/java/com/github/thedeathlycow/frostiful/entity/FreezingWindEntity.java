package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

public class FreezingWindEntity extends Entity {

    private static final IntProvider LIFE_TICKS = UniformIntProvider.create(80, 140);

    private float windSpeed = 0.2f;

    private int lifeTicks;

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.setLifeTicks(LIFE_TICKS.get(this.random));
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float speed) {
        this.windSpeed = speed;
    }

    public int getLifeTicks() {
        return this.lifeTicks;
    }

    public void setLifeTicks(int lifeTicks) {
        this.lifeTicks = lifeTicks;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isRemoved()) {
            return;
        }

        Vec3d velocity = Vec3d.ZERO.add(-this.windSpeed, 0, 0);

        float pathAroundSpeed = this.windSpeed / 3;
        if (this.horizontalCollision) {
            velocity = Vec3d.ZERO.add(0, pathAroundSpeed, 0);
        }
        if (this.verticalCollision) {
            velocity = Vec3d.ZERO.add(0, 0, pathAroundSpeed);
        }

        this.setVelocity(velocity);
        this.move(MovementType.SELF, velocity);

        if (!this.world.isClient && this.age % 5 == 0) {
            this.checkCollidingEntities();
        }

        if (this.world.isClient) {
            for(int i = 0; i < 2; ++i) {
                WindParticleEffect particle = new WindParticleEffect();

                this.world.addParticle(
                        particle,
                        this.getParticleX(0.5),
                        this.getRandomBodyY(),
                        this.getParticleZ(0.5),
                        0.0, 0.0, 0.0
                );
            }
        }

        this.lifeTicks--;
        if (this.lifeTicks <= 0) {
            this.discard();
        }
    }

    @Override
    protected void initDataTracker() {

    }

    protected MoveEffect getMoveEffect() {
        return MoveEffect.NONE;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("WindSpeed")) {
            this.setWindSpeed(nbt.getFloat("WindSpeed"));
        }

        if (nbt.contains("LifeTicks")) {
            this.setLifeTicks(nbt.getInt("LifeTicks"));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("WindSpeed", this.getWindSpeed());

        if (this.isAlive()) {
            nbt.putInt("LifeTicks", this.getLifeTicks());
        }
    }

    private void checkCollidingEntities() {
        for (var victim : this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox())) {
            FrostHelper.addLivingFrost(victim, 100);
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
