package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class PolarBearPlayFightGoal extends PlayFightGoal {

    private static final Identifier PLAYFIGHT_LOOT_TABLE = new Identifier(Frostiful.MODID, "gameplay/polar_bear_playfight");
    private boolean droppedFur = false;

    public PolarBearPlayFightGoal(PolarBearEntity mob, float adultChance, float babyChance) {
        super(mob, adultChance, babyChance, PLAYFIGHT_LOOT_TABLE);
    }

    @Override
    public void stop() {
        ((PolarBearEntity) this.mob).setWarning(false);
        if (this.target != null) {
            ((PolarBearEntity) this.target).setWarning(false);
        }
        this.droppedFur = false;
        super.stop();
    }

    @Override
    protected void playFight() {
        ((PolarBearEntity) this.mob).setWarning(true);
        if (this.target != null) {
            ((PolarBearEntity) this.target).setWarning(true);
        }
        super.playFight();
    }
}
