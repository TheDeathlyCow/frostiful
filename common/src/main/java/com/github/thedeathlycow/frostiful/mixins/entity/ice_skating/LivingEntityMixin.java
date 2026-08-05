package com.github.thedeathlycow.frostiful.mixins.entity.ice_skating;

import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.survival.system.IceSkateSystem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(
            method = "aiStep",
            at = @At("TAIL")
    )
    private void updateIsIceSkating(CallbackInfo ci) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("frostiful.ice_skate_tick");

        LivingEntity self = (LivingEntity) (Object) this;
        IceSkateSystem.aiStep(self);

        profiler.pop();
    }

    @WrapOperation(
            method = "travelInAir",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;getFriction()F"
            )
    )
    private float setSlipperinessForIceSkates(Block instance, Operation<Float> original) {
        if (IceSkateSystem.isIceSkating((LivingEntity) (Object) this)) {
            return IceSkateSystem.getSlipperinessForEntity(this);
        }

        return original.call(instance);
    }

    @Inject(
            method = "push(Lnet/minecraft/world/entity/Entity;)V",
            at = @At("HEAD")
    )
    private void damageOnLandingUponEntity(Entity entity, CallbackInfo ci) {
        if (!this.getItemBySlot(EquipmentSlot.FEET).is(FItemTags.ICE_SKATES)) {
            return;
        }

        if (entity instanceof LivingEntity target && target.level() instanceof ServerLevel world) {
            double attackerHeight = this.position().y;
            double targetEyeHeight = target.getEyePosition().y;

            if (attackerHeight > targetEyeHeight) {
                FDamageSources damageSources = FDamageSources.getDamageSources(this.level());
                target.hurtServer(world, damageSources.frostiful$iceSkate(this), 1.0f);
            }
        }
    }
}
