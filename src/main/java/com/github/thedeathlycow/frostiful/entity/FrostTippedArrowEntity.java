package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.config.IcicleConfig;
import com.github.thedeathlycow.frostiful.item.FrostifulItems;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class FrostTippedArrowEntity extends PersistentProjectileEntity {

    private int freezeAmount = IcicleConfig.CONFIG.get(IcicleConfig.FROST_ARROW_FREEZE_AMOUNT);;

    public FrostTippedArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public FrostTippedArrowEntity(World world, LivingEntity owner) {
        super(FrostifulEntityTypes.FROST_TIPPED_ARROW, owner, world);
    }

    public FrostTippedArrowEntity(World world, double x, double y, double z) {
        super(FrostifulEntityTypes.FROST_TIPPED_ARROW, x, y, z, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(FrostifulItems.FROST_TIPPED_ARROW);
    }

    public void tick() {
        super.tick();
        if (this.world.isClient && !this.inGround) {
            this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    protected void onHit(LivingEntity target) {
        super.onHit(target);
        FrostHelper.addLivingFrost(target, freezeAmount);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("FreezeAmount")) {
            this.freezeAmount = nbt.getInt("FreezeAmount");
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("FreezeAmount", this.freezeAmount);
    }
}
