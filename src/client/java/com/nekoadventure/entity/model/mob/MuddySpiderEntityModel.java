
package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.animation.mob.MuddySpiderModelAnimation;
import com.nekoadventure.entity.mob.MuddySpiderEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class MuddySpiderEntityModel extends SinglePartEntityModel<MuddySpiderEntity> {
	private final ModelPart main;
	private final ModelPart rightHand;
	private final ModelPart hand1;
	private final ModelPart head;
	private final ModelPart right;
	private final ModelPart leg2;
	private final ModelPart leg22;
	private final ModelPart leg3;
	private final ModelPart leg33;
	private final ModelPart leg1;
	private final ModelPart leg11;
	private final ModelPart body;
	private final ModelPart body0;
	private final ModelPart body1;
	private final ModelPart left;
	private final ModelPart leg4;
	private final ModelPart leg5;
	private final ModelPart leg7;
	private final ModelPart leg8;
	private final ModelPart leg9;
	private final ModelPart leg10;
	private final ModelPart leftHand;
	private final ModelPart hand2;
	public MuddySpiderEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.rightHand = main.getChild("rightHand");
		this.hand1 = rightHand.getChild("hand1");
		this.head = main.getChild("head");
		this.right = main.getChild("right");
		this.leg2 = right.getChild("leg2");
		this.leg22 = leg2.getChild("leg22");
		this.leg3 = right.getChild("leg3");
		this.leg33 = leg3.getChild("leg33");
		this.leg1 = right.getChild("leg1");
		this.leg11 = leg1.getChild("leg11");
		this.body = main.getChild("body");
		this.body0 = body.getChild("body0");
		this.body1 = body.getChild("body1");
		this.left = main.getChild("left");
		this.leg4 = left.getChild("leg4");
		this.leg5 = leg4.getChild("leg5");
		this.leg7 = left.getChild("leg7");
		this.leg8 = leg7.getChild("leg8");
		this.leg9 = left.getChild("leg9");
		this.leg10 = leg9.getChild("leg10");
		this.leftHand = main.getChild("leftHand");
		this.hand2 = leftHand.getChild("hand2");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, 0.0F));

		ModelPartData rightHand = main.addChild("rightHand", ModelPartBuilder.create(), ModelTransform.of(-4.0F, 0.0F, -4.0F, -2.5901F, -1.3039F, 0.9723F));

		ModelPartData leg6_r1 = rightHand.addChild("leg6_r1", ModelPartBuilder.create().uv(24, 27).cuboid(-14.0F, -10.0F, -2.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 9.0F, 1.0F, 0.0F, -0.4363F, 0.0F));

		ModelPartData hand1 = rightHand.addChild("hand1", ModelPartBuilder.create().uv(28, 19).cuboid(-1.7321F, -0.8768F, -0.7025F, 2.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0179F, 0.3768F, -6.0475F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(0, 27).cuboid(-3.0F, -1.0F, -7.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -3.0F));

		ModelPartData right = main.addChild("right", ModelPartBuilder.create(), ModelTransform.of(-3.0F, -2.0F, -0.3F, 0.4019F, 1.0952F, -0.4783F));

		ModelPartData leg2 = right.addChild("leg2", ModelPartBuilder.create(), ModelTransform.of(-1.1266F, 1.4975F, -0.3522F, -2.1753F, -1.0285F, 0.4427F));

		ModelPartData leg2_r1 = leg2.addChild("leg2_r1", ModelPartBuilder.create().uv(30, 9).cuboid(-0.8542F, -2.2718F, -1.8402F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0699F, 1.3276F, -5.219F, 0.0F, -1.0005F, 0.0F));

		ModelPartData leg22 = leg2.addChild("leg22", ModelPartBuilder.create(), ModelTransform.pivot(-4.3963F, -0.1316F, -5.096F));

		ModelPartData leg22_r1 = leg22.addChild("leg22_r1", ModelPartBuilder.create().uv(30, 0).cuboid(0.1613F, -1.9351F, -0.6883F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-1.3414F, 1.1277F, -0.8432F, 0.0F, -1.0899F, 0.0F));

		ModelPartData leg3 = right.addChild("leg3", ModelPartBuilder.create(), ModelTransform.of(-1.1266F, 1.4975F, -0.3522F, 2.4518F, -1.3007F, 2.7091F));

		ModelPartData leg3_r1 = leg3.addChild("leg3_r1", ModelPartBuilder.create().uv(48, 0).cuboid(-0.8542F, -2.2718F, -1.8402F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0699F, 1.3276F, -5.219F, 0.0F, -1.0005F, 0.0F));

		ModelPartData leg33 = leg3.addChild("leg33", ModelPartBuilder.create(), ModelTransform.pivot(-4.6621F, 0.0706F, -5.2776F));

		ModelPartData leg33_r1 = leg33.addChild("leg33_r1", ModelPartBuilder.create().uv(24, 31).cuboid(0.1613F, -1.9351F, -0.6883F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-1.0757F, 0.9256F, -0.7616F, 0.0F, -1.2208F, 0.0F));

		ModelPartData leg1 = right.addChild("leg1", ModelPartBuilder.create(), ModelTransform.of(-1.1266F, 1.4975F, -0.3522F, -2.3389F, -0.3624F, -0.2023F));

		ModelPartData leg1_r1 = leg1.addChild("leg1_r1", ModelPartBuilder.create().uv(48, 4).cuboid(-0.8542F, -2.2718F, -1.8402F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0699F, 1.3276F, -5.219F, 0.0F, -1.0005F, 0.0F));

		ModelPartData leg11 = leg1.addChild("leg11", ModelPartBuilder.create(), ModelTransform.pivot(-5.3581F, -0.0293F, -4.4168F));

		ModelPartData leg11_r1 = leg11.addChild("leg11_r1", ModelPartBuilder.create().uv(0, 39).cuboid(0.1613F, -1.9351F, -0.6883F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-0.3797F, 1.0255F, -1.5225F, 0.0F, -1.0899F, 0.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData body0 = body.addChild("body0", ModelPartBuilder.create().uv(0, 15).cuboid(-3.0F, -2.0F, -4.0F, 6.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

		ModelPartData body1 = body.addChild("body1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 4.0F));

		ModelPartData body1_r1 = body1.addChild("body1_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -4.0F, 1.0F, 6.0F, 6.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -1.0F, 0.3054F, 0.0F, 0.0F));

		ModelPartData left = main.addChild("left", ModelPartBuilder.create(), ModelTransform.of(3.0F, -2.0F, -0.3F, 0.4019F, -1.0952F, 0.4783F));

		ModelPartData leg4 = left.addChild("leg4", ModelPartBuilder.create(), ModelTransform.of(1.1266F, 1.4975F, -0.3522F, -2.1753F, 1.0285F, -0.4427F));

		ModelPartData leg2_r2 = leg4.addChild("leg2_r2", ModelPartBuilder.create().uv(0, 49).cuboid(-7.1458F, -2.2718F, -1.8402F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0699F, 1.3276F, -5.219F, 0.0F, 1.0005F, 0.0F));

		ModelPartData leg5 = leg4.addChild("leg5", ModelPartBuilder.create(), ModelTransform.pivot(5.2103F, 0.3512F, -4.4346F));

		ModelPartData leg22_r2 = leg5.addChild("leg22_r2", ModelPartBuilder.create().uv(18, 40).cuboid(-2.1613F, -1.9351F, -0.6883F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.5275F, 0.6449F, -1.5046F, 0.0F, 1.0899F, 0.0F));

		ModelPartData leg7 = left.addChild("leg7", ModelPartBuilder.create(), ModelTransform.of(1.1266F, 1.4975F, -0.3522F, 2.4518F, 1.3007F, -2.7091F));

		ModelPartData leg3_r2 = leg7.addChild("leg3_r2", ModelPartBuilder.create().uv(20, 49).cuboid(-7.1458F, -2.2718F, -1.8402F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0699F, 1.3276F, -5.219F, 0.0F, 1.0005F, 0.0F));

		ModelPartData leg8 = leg7.addChild("leg8", ModelPartBuilder.create(), ModelTransform.pivot(5.4821F, 0.3012F, -4.6258F));

		ModelPartData leg33_r2 = leg8.addChild("leg33_r2", ModelPartBuilder.create().uv(36, 40).cuboid(-2.1613F, -1.9351F, -0.6883F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.2557F, 0.6949F, -1.4134F, 0.0F, 1.2208F, 0.0F));

		ModelPartData leg9 = left.addChild("leg9", ModelPartBuilder.create(), ModelTransform.of(1.1266F, 1.4975F, -0.3522F, -2.3389F, 0.3624F, 0.2023F));

		ModelPartData leg1_r2 = leg9.addChild("leg1_r2", ModelPartBuilder.create().uv(40, 49).cuboid(-7.1458F, -2.2718F, -1.8402F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0699F, 1.3276F, -5.219F, 0.0F, 1.0005F, 0.0F));

		ModelPartData leg10 = leg9.addChild("leg10", ModelPartBuilder.create(), ModelTransform.pivot(5.1633F, 0.1779F, -4.555F));

		ModelPartData leg11_r2 = leg10.addChild("leg11_r2", ModelPartBuilder.create().uv(42, 31).cuboid(-2.1613F, -1.9351F, -0.6883F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.5745F, 0.8183F, -1.3842F, 0.0F, 1.0899F, 0.0F));

		ModelPartData leftHand = main.addChild("leftHand", ModelPartBuilder.create(), ModelTransform.of(4.0F, -1.0F, -3.0F, -2.5901F, 1.3039F, -0.9723F));

		ModelPartData leg6_r2 = leftHand.addChild("leg6_r2", ModelPartBuilder.create().uv(28, 15).cuboid(3.0F, -10.0F, -2.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.2533F, 9.0759F, 2.1986F, 0.0F, 0.4363F, 0.0F));

		ModelPartData hand2 = leftHand.addChild("hand2", ModelPartBuilder.create().uv(46, 19).cuboid(-0.421F, -0.3686F, -0.7979F, 2.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(8.9177F, -0.0555F, -4.7535F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(MuddySpiderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(MuddySpiderModelAnimation.WALK,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(MuddySpiderEntity.attackAnimation, MuddySpiderModelAnimation.JUMP_ATTACK,animationProgress,1.0f);

		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = headPitch * (float) (Math.PI / 180.0);
	}
}