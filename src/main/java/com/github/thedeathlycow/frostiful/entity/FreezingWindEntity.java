package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.biome.FBiomeTags;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class FreezingWindEntity extends WindEntity {

    private int frost;

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, World world) {
        super(type, world);
        this.frost = Frostiful.getConfig().freezingConfig.getFreezingWindFrost();
    }

    public void onEntityCollision(LivingEntity entity) {
        super.onEntityCollision(entity);
        if (entity.isPlayer()) {
            FrostHelper.addLivingFrost(entity, this.frost);
        }
    }

    protected ParticleEffect getDustParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Frost", NbtElement.INT_TYPE)) {
            this.frost = nbt.getInt("Frost");
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putInt("Frost", this.frost);
    }
}
