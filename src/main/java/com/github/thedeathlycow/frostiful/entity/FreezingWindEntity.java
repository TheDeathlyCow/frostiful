package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.block.FrozenTorchBlock;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class FreezingWindEntity extends Entity {

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {

        super.tick();

        if (this.age % 5 == 0) {
            this.checkCollidingEntities();
        }

    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public void onBlockCollision(BlockState state) {
        if (!this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            return; // respect mob griefing game rule
        }

        BlockState frozenTorch = FrozenTorchBlock.freezeTorch(state);

        if (!frozenTorch.isAir()) {
            this.world.setBlockState(this.getBlockPos(), frozenTorch);
        }
    }

    private void checkCollidingEntities() {
        for (var victim : this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox())) {
            FrostHelper.removeLivingFrost(victim, 10);
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
