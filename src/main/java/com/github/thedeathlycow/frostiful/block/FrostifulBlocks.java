package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.entity.FrostTippedArrowEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.item.FrostifulItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class FrostifulBlocks {

    public static final Block ICICLE = new IcicleBlock(FabricBlockSettings.of(Material.ICE, MapColor.CYAN).nonOpaque().sounds(BlockSoundGroup.GLASS).ticksRandomly().strength(0.5F).dynamicBounds().requiresTool());
    public static final Block SUN_LICHEN = new SunLichenBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.RED).noCollision().strength(0.2F).sounds(BlockSoundGroup.GLOW_LICHEN).ticksRandomly().nonOpaque().luminance((state) -> state.get(SunLichenBlock.HEAT_LEVEL) * 2));

    public static void registerBlocks() {
        register("icicle", ICICLE);
        register("sun_lichen", SUN_LICHEN);

        DispenserBlock.registerBehavior(FrostifulItems.FROST_TIPPED_ARROW, new ProjectileDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                FrostTippedArrowEntity arrowEntity = new FrostTippedArrowEntity(world, position.getX(), position.getY(), position.getZ());
                arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                return arrowEntity;
            }
        });
    }

    private static void register(String id, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(Frostiful.MODID, id), block);
    }
}
