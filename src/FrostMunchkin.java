// Made with Blockbench 4.6.0
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class FrostMunchkin extends EntityModel<Entity> {
	private final ModelPart body;
	private final ModelPart nose;
	private final ModelPart arms;
	public FrostMunchkin(ModelPart root) {
		this.body = root.getChild("body");
		this.arms = root.getChild("arms");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -30.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F))
		.uv(0, 24).cuboid(-5.0F, -18.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F))
		.uv(40, 16).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 21.0F, 0.0F));

		ModelPartData nose = body.addChild("nose", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -22.0F, -8.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(6, 0).cuboid(-1.0F, -17.0F, -8.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData arms = modelPartData.addChild("arms", ModelPartBuilder.create().uv(0, 44).cuboid(-10.0F, -25.0F, -3.0F, 4.0F, 25.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 44).mirrored().cuboid(6.0F, -25.0F, -3.0F, 4.0F, 25.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		arms.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}