package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FreezingWindEntity extends Entity {

    private float windSpeed = 0.2f;

    private int lifeTicks = 100;

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.setRotation(90f, 0f);
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

        this.move(MovementType.SELF, Vec3d.ZERO.add(-this.windSpeed, 0, 0));

        float pathAroundSpeed = this.windSpeed / 3;
        if (this.horizontalCollision) {
            this.move(MovementType.SELF, Vec3d.ZERO.add(0, pathAroundSpeed, 0));
        }
        if (this.verticalCollision) {
            this.move(MovementType.SELF, Vec3d.ZERO.add(0, 0, pathAroundSpeed));
        }

        if (this.age % 5 == 0) {
            this.checkCollidingEntities();
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
