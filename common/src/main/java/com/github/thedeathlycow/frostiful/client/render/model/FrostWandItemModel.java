package com.github.thedeathlycow.frostiful.client.render.model;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/**
 * Made with Block Bench
 */

public class FrostWandItemModel extends EntityModel<FrostologerEntityRenderState> {
    public static final Identifier TEXTURE = Frostiful.id("textures/entity/frost_wand.png");

    public FrostWandItemModel(ModelPart root) {
        super(root, RenderTypes::entityTranslucent);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rod = root.addOrReplaceChild("rod", CubeListBuilder.create().texOffs(32, 9).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 8).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -33.0F, -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition top = root.addOrReplaceChild("top", CubeListBuilder.create().texOffs(8, 0).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -37.0F, 0.0F));

        PartDefinition icicles = top.addOrReplaceChild("icicles", CubeListBuilder.create().texOffs(20, 25).addBox(-3.0F, 1.0F, -3.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(20, 20).addBox(3.0F, 1.0F, -3.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition south_r1 = icicles.addOrReplaceChild("south_r1", CubeListBuilder.create().texOffs(20, 10).addBox(-3.0F, 1.0F, -3.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(20, 15).addBox(3.0F, 1.0F, -3.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition icicle = root.addOrReplaceChild("icicle", CubeListBuilder.create(), PartPose.offset(0.0F, -26.0F, 0.0F));

        PartDefinition main = icicle.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition cube_r1 = main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 8).addBox(-3.0F, -26.0F, 0.0F, 6.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r2 = main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(8, 8).addBox(-3.0F, -26.0F, 0.0F, 6.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition sides = icicle.addOrReplaceChild("sides", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition west_r1 = sides.addOrReplaceChild("west_r1", CubeListBuilder.create().texOffs(32, 16).addBox(-3.0F, -6.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -16.0F, 0.0F, -0.1745F, 1.5708F, 0.0F));

        PartDefinition east_r1 = sides.addOrReplaceChild("east_r1", CubeListBuilder.create().texOffs(32, 16).addBox(-3.0F, -6.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -16.0F, 0.0F, -0.1745F, -1.5708F, 0.0F));

        PartDefinition south_r2 = sides.addOrReplaceChild("south_r2", CubeListBuilder.create().texOffs(32, 16).addBox(-3.0F, -6.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 4.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition north_r1 = sides.addOrReplaceChild("north_r1", CubeListBuilder.create().texOffs(32, 16).addBox(-3.0F, -6.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, -4.0F, 0.1745F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }
}
