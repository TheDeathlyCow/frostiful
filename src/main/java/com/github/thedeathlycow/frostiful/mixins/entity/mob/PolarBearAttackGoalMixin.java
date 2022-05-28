package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;


@Mixin(targets = "net.minecraft.entity.passive.PolarBearEntity$AttackGoal")
public abstract class PolarBearAttackGoalMixin {

    private static final Identifier PLAYFIGHT_LOOT_TABLE = new Identifier(Frostiful.MODID, "gameplay/polar_bear_playfight");

    @Shadow
    public abstract void stop();

    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/PathAwareEntity;tryAttack(Lnet/minecraft/entity/Entity;)Z",
                    shift = At.Shift.AFTER
            )
    )
    private void stopAttackingPolarBears(LivingEntity target, double squaredDistance, CallbackInfo ci) {
        if (target instanceof PolarBearEntity) {
            this.stop();
            dropFur(target);
        }
    }

    private static void dropFur(LivingEntity target) {
        World world = target.getWorld();
        LootTable lootTable = Objects.requireNonNull(world.getServer())
                .getLootManager().getTable(PLAYFIGHT_LOOT_TABLE);
        LootContext.Builder builder = new LootContext.Builder((ServerWorld) world)
                .random(target.getRandom());
        List<ItemStack> generatedItems = lootTable.generateLoot(builder.build(LootContextTypes.EMPTY));
        Vec3d pos = target.getPos();
        for (ItemStack stack : generatedItems) {
            world.spawnEntity(new ItemEntity(world, pos.x, pos.y, pos.z, stack));
        }
    }
}
