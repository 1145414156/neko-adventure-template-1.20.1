package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.animation.mob.ScarabBeetleModelAnimation;
import com.nekoadventure.entity.mob.ScarabBeetleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class ScarabBeetleEntityModel extends SinglePartEntityModel<ScarabBeetleEntity> {
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart bone_left;
	private final ModelPart bone_right;
	private final ModelPart mandible;
	private final ModelPart leftMandible;
	private final ModelPart rightMandible;
	private final ModelPart pronotum;
	private final ModelPart shell;
	private final ModelPart wing;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart legs;
	private final ModelPart leg_front_r;
	private final ModelPart leg_front_l;
	private final ModelPart leg_mid_r;
	private final ModelPart leg_mid_l;
	private final ModelPart leg_hind_r;
	private final ModelPart leg_hind_l;
	public ScarabBeetleEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.head = this.main.getChild("head");
		this.bone_left = this.head.getChild("bone_left");
		this.bone_right = this.head.getChild("bone_right");
		this.mandible = this.head.getChild("mandible");
		this.leftMandible = this.mandible.getChild("leftMandible");
		this.rightMandible = this.mandible.getChild("rightMandible");
		this.pronotum = this.main.getChild("pronotum");
		this.shell = this.main.getChild("shell");
		this.wing = this.shell.getChild("wing");
		this.leftWing = this.wing.getChild("leftWing");
		this.rightWing = this.wing.getChild("rightWing");
		this.legs = this.main.getChild("legs");
		this.leg_front_r = this.legs.getChild("leg_front_r");
		this.leg_front_l = this.legs.getChild("leg_front_l");
		this.leg_mid_r = this.legs.getChild("leg_mid_r");
		this.leg_mid_l = this.legs.getChild("leg_mid_l");
		this.leg_hind_r = this.legs.getChild("leg_hind_r");
		this.leg_hind_l = this.legs.getChild("leg_hind_l");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 22.0F, 0.0F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(18, 39).cuboid(-3.0F, -1.5F, -2.0F, 6.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, -5.0F));

		ModelPartData bone_left = head.addChild("bone_left", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, -1.75F, -0.5F));

		ModelPartData antenna_l_r1 = bone_left.addChild("antenna_l_r1", ModelPartBuilder.create().uv(10, 43).cuboid(-0.5F, -2.5F, -2.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.7708F, -4.3698F, -2.2856F, 1.7674F, 0.6893F, -0.1966F));

		ModelPartData antenna_l_r2 = bone_left.addChild("antenna_l_r2", ModelPartBuilder.create().uv(38, 42).cuboid(-0.5F, -4.5F, -0.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 0.0F, 0.0F, 0.633F, 0.6893F, -0.1966F));

		ModelPartData bone_right = head.addChild("bone_right", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, -1.75F, -0.5F));

		ModelPartData antenna_r_r1 = bone_right.addChild("antenna_r_r1", ModelPartBuilder.create().uv(6, 43).cuboid(-0.5F, -2.5F, -2.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.7708F, -4.3698F, -2.2856F, 1.7674F, -0.6893F, 0.1966F));

		ModelPartData antenna_r_r2 = bone_right.addChild("antenna_r_r2", ModelPartBuilder.create().uv(34, 42).cuboid(-0.5F, -4.5F, -0.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.633F, -0.6893F, 0.1966F));

		ModelPartData mandible = head.addChild("mandible", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftMandible = mandible.addChild("leftMandible", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData mandible_l_r1 = leftMandible.addChild("mandible_l_r1", ModelPartBuilder.create().uv(42, 42).cuboid(-1.0F, -0.5F, -2.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, 1.125F, -2.675F, 0.3688F, -0.9269F, -0.2998F));

		ModelPartData mandible_l_r2 = leftMandible.addChild("mandible_l_r2", ModelPartBuilder.create().uv(42, 15).cuboid(-0.5F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, 1.0F, -2.5F, 0.2643F, 0.5944F, 0.1504F));

		ModelPartData rightMandible = mandible.addChild("rightMandible", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData mandible_r_r1 = rightMandible.addChild("mandible_r_r1", ModelPartBuilder.create().uv(0, 43).cuboid(0.0F, -0.5F, -2.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 1.125F, -2.675F, 0.3688F, 0.9269F, 0.2998F));

		ModelPartData mandible_r_r2 = rightMandible.addChild("mandible_r_r2", ModelPartBuilder.create().uv(42, 18).cuboid(-1.5F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 1.0F, -2.5F, 0.2643F, -0.5944F, -0.1504F));

		ModelPartData pronotum = main.addChild("pronotum", ModelPartBuilder.create().uv(28, 32).cuboid(-4.0F, -2.0F, 0.0F, 8.0F, 3.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 39).cuboid(-3.0F, -3.0F, 1.0F, 6.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, -5.0F));

		ModelPartData shell = main.addChild("shell", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -1.4F, 0.0F, 12.0F, 2.0F, 9.0F, new Dilation(0.0F))
		.uv(0, 11).cuboid(-6.0F, -2.0F, 0.0F, 12.0F, 1.0F, 9.0F, new Dilation(0.0F))
		.uv(0, 32).cuboid(-4.0F, -3.7F, 0.0F, 8.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -1.0F));

		ModelPartData wing = shell.addChild("wing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftWing = wing.addChild("leftWing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.4F, 0.0F));

		ModelPartData shell_4_r1 = leftWing.addChild("shell_4_r1", ModelPartBuilder.create().uv(0, 21).cuboid(-5.0F, -5.0F, -1.0F, 5.0F, 1.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 2.0F, 1.0F, 0.0F, 0.1745F, 0.0F));

		ModelPartData rightWing = wing.addChild("rightWing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.4F, 0.0F));

		ModelPartData shell_5_r1 = rightWing.addChild("shell_5_r1", ModelPartBuilder.create().uv(30, 21).cuboid(0.0F, -5.0F, -1.0F, 5.0F, 1.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 2.0F, 1.0F, 0.0F, -0.1745F, 0.0F));

		ModelPartData legs = main.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		ModelPartData leg_front_r = legs.addChild("leg_front_r", ModelPartBuilder.create().uv(34, 39).cuboid(0.1932F, -0.0518F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -2.3F, -3.0F, 0.0F, 0.0F, 0.2618F));

		ModelPartData leg_front_l = legs.addChild("leg_front_l", ModelPartBuilder.create().uv(42, 6).cuboid(-4.1932F, -0.0518F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -2.3F, -3.0F, 0.0F, 0.0F, -0.2618F));

		ModelPartData leg_mid_r = legs.addChild("leg_mid_r", ModelPartBuilder.create().uv(42, 0).cuboid(-0.9659F, 0.2588F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -2.3F, 1.0F, 0.0F, 0.0F, 0.2618F));

		ModelPartData leg_mid_l = legs.addChild("leg_mid_l", ModelPartBuilder.create().uv(42, 9).cuboid(-3.0341F, 0.2588F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -2.3F, 1.0F, 0.0F, 0.0F, -0.2618F));

		ModelPartData leg_hind_r = legs.addChild("leg_hind_r", ModelPartBuilder.create().uv(42, 3).cuboid(-1.1591F, 0.3106F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -2.3F, 4.5F, 0.0F, 0.0F, 0.2618F));

		ModelPartData leg_hind_l = legs.addChild("leg_hind_l", ModelPartBuilder.create().uv(42, 12).cuboid(-2.8409F, 0.3106F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -2.3F, 4.5F, 0.0F, 0.0F, -0.2618F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertices, light, overlay);
	}

	@Override
	public ModelPart getPart() {
		return main;
	}

	@Override
	public void setAngles(ScarabBeetleEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(ScarabBeetleModelAnimation.WALK,limbAngle,limbDistance,2f,2.5f);
		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = headPitch * (float) (Math.PI / 180.0);
	}
}