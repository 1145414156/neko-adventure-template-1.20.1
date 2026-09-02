
package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.animation.mob.TreasureHunterAnimation;
import com.nekoadventure.entity.mob.TreasureHunterEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TreasureHunterEntityModel extends SinglePartEntityModel<TreasureHunterEntity> {
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart mainBody;
	private final ModelPart sword;
	private final ModelPart bone;
	private final ModelPart head;
	private final ModelPart RightArm;
	private final ModelPart bag;
	private final ModelPart LeftArm;
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;
	public TreasureHunterEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.mainBody = this.body.getChild("mainBody");
		this.sword = this.mainBody.getChild("sword");
		this.bone = this.mainBody.getChild("bone");
		this.head = this.main.getChild("head");
		this.RightArm = this.main.getChild("RightArm");
		this.bag = this.RightArm.getChild("bag");
		this.LeftArm = this.main.getChild("LeftArm");
		this.RightLeg = this.main.getChild("RightLeg");
		this.LeftLeg = this.main.getChild("LeftLeg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData mainBody = body.addChild("mainBody", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData sword = mainBody.addChild("sword", ModelPartBuilder.create().uv(19, 47).cuboid(-0.5F, -0.5F, 5.45F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(37, 33).cuboid(-1.0F, -1.0F, 4.45F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.3841F, -15.4946F, 2.6F, 0.3927F, -1.5272F, 0.0F));

		ModelPartData cube_r1 = sword.addChild("cube_r1", ModelPartBuilder.create().uv(13, 47).cuboid(-1.0F, -6.0F, 0.0F, 2.0F, 9.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.0F, 1.45F, 1.5708F, 0.0F, 1.5708F));

		ModelPartData cube_r2 = sword.addChild("cube_r2", ModelPartBuilder.create().uv(37, 29).cuboid(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.3F, -3.85F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r3 = sword.addChild("cube_r3", ModelPartBuilder.create().uv(37, 29).cuboid(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.15F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r4 = sword.addChild("cube_r4", ModelPartBuilder.create().uv(37, 29).cuboid(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.3F, -3.85F, -0.7854F, 0.0F, 0.0F));

		ModelPartData bone = mainBody.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r5 = bone.addChild("cube_r5", ModelPartBuilder.create().uv(25, 24).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -20.0F, -4.0F, 0.7363F, -0.3035F, 0.3185F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -28.0F, 0.0F));

		ModelPartData RightArm = main.addChild("RightArm", ModelPartBuilder.create().uv(24, 16).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -22.0F, 0.0F));

		ModelPartData bag = RightArm.addChild("bag", ModelPartBuilder.create().uv(31, 44).cuboid(-5.5F, -14.0F, -10.0F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 22.0F, 0.0F));

		ModelPartData LeftArm = main.addChild("LeftArm", ModelPartBuilder.create().uv(24, 30).cuboid(4.0F, -24.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData RightLeg = main.addChild("RightLeg", ModelPartBuilder.create().uv(0, 32).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -10.0F, 0.0F));

		ModelPartData cube_r6 = RightLeg.addChild("cube_r6", ModelPartBuilder.create().uv(2, 0).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.05F, 3.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData LeftLeg = main.addChild("LeftLeg", ModelPartBuilder.create().uv(43, 17).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -11.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
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
	public void setAngles(TreasureHunterEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(TreasureHunterAnimation.WALK,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(entity.attackAnimationState, TreasureHunterAnimation.ATTACK, animationProgress);

		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = headPitch * (float) (Math.PI / 180.0);
	}
}