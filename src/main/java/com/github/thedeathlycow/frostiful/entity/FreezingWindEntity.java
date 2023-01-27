package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FreezingWindEntity extends Entity {

    private float windSpeed = 0.2f;

    private int timeToLive = 100;

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public float getSpeed() {
        return windSpeed;
    }

    public void setSpeed(float speed) {
        this.windSpeed = speed;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Override
    public void tick() {
        super.tick();
        this.move(MovementType.SELF, Vec3d.ZERO.add(0, 0, this.windSpeed));



        float pathAroundSpeed = this.windSpeed / 3;
        if (this.horizontalCollision) {
            this.move(MovementType.SELF, Vec3d.ZERO.add(0, pathAroundSpeed, 0));
        }

        if (this.verticalCollision) {
            this.move(MovementType.SELF, Vec3d.ZERO.add(pathAroundSpeed, 0, 0));
        }

        if (this.age % 5 == 0) {
            this.checkCollidingEntities();
        }

        this.timeToLive--;

        if (this.timeToLive <= 0) {
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
        nbt.putFloat("WindSpeed", this.windSpeed);
        nbt.putInt("TimeToLive", this.timeToLive);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        this.windSpeed = nbt.getFloat("WindSpeed");
        this.timeToLive = nbt.getInt("TimeToLive");
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
