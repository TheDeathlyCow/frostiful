package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.entity.SoakableEntity;
import com.github.thedeathlycow.frostiful.tag.entitytype.FEntityTypeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class FreezableEntityImplMixin extends Entity implements FreezableEntity {

    public FreezableEntityImplMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract AttributeContainer getAttributes();

    @Override
    public float frostiful$getFrostProgress() {
        return this.getFreezingScale();
    }

    @Override
    @Unique
    public int frostiful$getCurrentFrost() {
        return this.getFrozenTicks();
    }

    @Override
    @Unique
    public int frostiful$getMaxFrost() {
        final AttributeContainer attributes = this.getAttributes();
        final double maxFrost = attributes.getValue(FEntityAttributes.MAX_FROST);
        return getTicksFromMaxFrost(maxFrost);
    }

    @Override
    @Unique
    public void frostiful$setFrost(int amount) {
        this.setFrozenTicks(amount);
    }

    @Override
    @Unique
    public boolean frostiful$canFreeze() {
        final LivingEntity instance = (LivingEntity) (Object) this;
        EntityType<?> type = instance.getType();
        if (instance.isSpectator()) {
            return false;
        } else if (instance.isPlayer()) {
            return !((PlayerEntity) instance).isCreative();
        } else if (type.isIn(FEntityTypeTags.BENEFITS_FROM_COLD)) {
            return true;
        } else {
            return !type.isIn(FEntityTypeTags.FREEZE_IMMUNE);
        }
    }

    private static int getTicksFromMaxFrost(double maxFrost) {
        return (int) (140 * maxFrost);
    }
}
