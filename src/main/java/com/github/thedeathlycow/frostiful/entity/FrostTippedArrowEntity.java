package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.item.FItems;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class FrostTippedArrowEntity extends PersistentProjectileEntity {

    private int freezeAmount = Frostiful.getConfig().icicleConfig.getFrostArrowFreezeAmount();

    public FrostTippedArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public FrostTippedArrowEntity(World world, LivingEntity owner) {
        super(FEntityTypes.FROST_TIPPED_ARROW, owner, world);
    }

    public FrostTippedArrowEntity(World world, double x, double y, double z) {
        super(FEntityTypes.FROST_TIPPED_ARROW, x, y, z, world);
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(FItems.FROST_TIPPED_ARROW);
    }

    public void tick() {
        super.tick();

        if (this.world.isClient && !this.inGround) {
            this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    protected void onHit(LivingEntity target) {
        super.onHit(target);
        target.thermoo$addTemperature(-freezeAmount, HeatingModes.ACTIVE);
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
