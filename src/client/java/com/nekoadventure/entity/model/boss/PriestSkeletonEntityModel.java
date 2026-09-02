
package com.nekoadventure.entity.model.boss;

import com.nekoadventure.entity.animation.boss.PriestSkeletonModelAnimation;
import com.nekoadventure.entity.boss.PriestSkeletonEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class PriestSkeletonEntityModel extends SinglePartEntityModel<PriestSkeletonEntity> {
	private final ModelPart main;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart LeftArm;
	private final ModelPart leftItem;
	private final ModelPart RightArm;
	private final ModelPart Head;
	private final ModelPart HeadLayer;
	private final ModelPart waist;
	private final ModelPart Body;
	private final ModelPart BodyLayer;
	public PriestSkeletonEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.LeftLeg = main.getChild("LeftLeg");
		this.RightLeg = main.getChild("RightLeg");
		this.LeftArm = main.getChild("LeftArm");
		this.leftItem = LeftArm.getChild("leftItem");
		this.RightArm = main.getChild("RightArm");
		this.Head = main.getChild("Head");
		this.HeadLayer = Head.getChild("Head Layer");
		this.waist = main.getChild("waist");
		this.Body = waist.getChild("Body");
		this.BodyLayer = Body.getChild("Body Layer");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 6.6667F, 0.0F));

		ModelPartData LeftLeg = main.addChild("LeftLeg", ModelPartBuilder.create().uv(48, 59).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 5.3333F, 0.0F));

		ModelPartData RightLeg = main.addChild("RightLeg", ModelPartBuilder.create().uv(40, 59).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 5.3333F, 0.0F));

		ModelPartData LeftArm = main.addChild("LeftArm", ModelPartBuilder.create().uv(32, 59).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F))
				.uv(62, 44).cuboid(-1.5F, 4.8F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -4.6667F, 0.0F));

		ModelPartData leftItem = LeftArm.addChild("leftItem", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -0.5F, -16.0F, 1.0F, 1.0F, 27.0F, new Dilation(0.0F))
				.uv(56, 70).cuboid(-0.5F, -0.5F, -25.6F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
				.uv(20, 65).cuboid(-0.5F, -0.5F, -19.9F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(56, 66).cuboid(-0.9F, -1.0F, 6.8F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData cube_r1 = leftItem.addChild("cube_r1", ModelPartBuilder.create().uv(16, 70).cuboid(-0.5F, 0.4F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.3064F, -23.3429F, 1.7715F, 0.0F, 0.0F));

		ModelPartData cube_r2 = leftItem.addChild("cube_r2", ModelPartBuilder.create().uv(64, 66).cuboid(-0.5F, 0.5F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.7084F, -22.3885F, 0.9425F, 0.0F, 0.0F));

		ModelPartData cube_r3 = leftItem.addChild("cube_r3", ModelPartBuilder.create().uv(64, 42).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.8131F, -15.1896F, -0.8552F, 0.0F, 0.0F));

		ModelPartData cube_r4 = leftItem.addChild("cube_r4", ModelPartBuilder.create().uv(72, 17).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.1869F, -20.4896F, -1.4661F, 0.0F, 0.0F));

		ModelPartData cube_r5 = leftItem.addChild("cube_r5", ModelPartBuilder.create().uv(70, 63).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.1869F, -18.2895F, -1.6755F, 0.0F, 0.0F));

		ModelPartData cube_r6 = leftItem.addChild("cube_r6", ModelPartBuilder.create().uv(0, 65).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.7869F, -19.3895F, 0.0262F, 0.0F, 0.0F));

		ModelPartData cube_r7 = leftItem.addChild("cube_r7", ModelPartBuilder.create().uv(10, 65).cuboid(-0.5F, -0.5F, -3.8F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.3F, -15.5F, 0.9163F, 0.0F, 0.0F));

		ModelPartData cube_r8 = leftItem.addChild("cube_r8", ModelPartBuilder.create().uv(64, 37).cuboid(-0.5F, -0.5F, -3.8F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -15.5F, -0.9163F, 0.0F, 0.0F));

		ModelPartData cube_r9 = leftItem.addChild("cube_r9", ModelPartBuilder.create().uv(70, 59).cuboid(-0.5F, -0.75F, -2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.1F, 0.45F, 12.3F, -0.6155F, 0.5236F, 0.6155F));

		ModelPartData cube_r10 = leftItem.addChild("cube_r10", ModelPartBuilder.create().uv(56, 59).cuboid(-0.5F, -0.75F, -2.5F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-0.1F, -0.55F, -13.5F, 0.0F, 0.0F, 0.7854F));

		ModelPartData RightArm = main.addChild("RightArm", ModelPartBuilder.create().uv(24, 59).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F))
				.uv(62, 49).cuboid(-1.5F, 4.8F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -4.6667F, 0.0F));

		ModelPartData Head = main.addChild("Head", ModelPartBuilder.create().uv(0, 28).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.6667F, 0.0F));

		ModelPartData HeadLayer = Head.addChild("Head Layer", ModelPartBuilder.create().uv(32, 28).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F))
				.uv(64, 34).cuboid(-7.8F, -10.0F, -0.5F, 5.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(64, 31).cuboid(2.8F, -10.0F, -0.5F, 5.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r11 = HeadLayer.addChild("cube_r11", ModelPartBuilder.create().uv(64, 28).cuboid(-1.9F, -1.0F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -14.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		ModelPartData cube_r12 = HeadLayer.addChild("cube_r12", ModelPartBuilder.create().uv(62, 54).cuboid(-2.1F, -1.0F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -14.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		ModelPartData cube_r13 = HeadLayer.addChild("cube_r13", ModelPartBuilder.create().uv(56, 25).cuboid(-2.5F, -1.0F, -1.0F, 7.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -10.0F, 0.0F, 0.0F, 0.0F, -2.0508F));

		ModelPartData cube_r14 = HeadLayer.addChild("cube_r14", ModelPartBuilder.create().uv(56, 22).cuboid(-4.5F, -1.0F, -1.0F, 7.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -10.0F, 0.0F, 0.0F, 0.0F, 2.0508F));

		ModelPartData cube_r15 = HeadLayer.addChild("cube_r15", ModelPartBuilder.create().uv(56, 17).cuboid(-2.8F, -1.0F, -0.5F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, -9.0F, -3.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData cube_r16 = HeadLayer.addChild("cube_r16", ModelPartBuilder.create().uv(63, 14).mirrored().cuboid(-2.5F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(63, 14).mirrored().cuboid(-2.5F, -0.5F, 1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(63, 14).mirrored().cuboid(-2.5F, -0.5F, 3.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(63, 14).mirrored().cuboid(-2.5F, -0.5F, 5.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.5756F, -5.9629F, -3.0F, 0.0F, 0.0F, 1.7453F));

		ModelPartData cube_r17 = HeadLayer.addChild("cube_r17", ModelPartBuilder.create().uv(62, 14).cuboid(-1.5F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(62, 14).cuboid(-1.5F, -0.5F, -3.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(62, 14).cuboid(-1.5F, -0.5F, -5.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(62, 14).cuboid(-1.5F, -0.5F, -7.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.5756F, -5.9629F, 3.0F, 0.0F, 0.0F, -1.7453F));

		ModelPartData cube_r18 = HeadLayer.addChild("cube_r18", ModelPartBuilder.create().uv(62, 14).cuboid(-1.5F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(62, 14).cuboid(-1.5F, -0.5F, -3.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(62, 14).cuboid(-1.5F, -0.5F, -5.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(62, 14).cuboid(-1.5F, -0.5F, -7.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.5756F, -3.2371F, 3.0F, 0.0F, 0.0F, 1.7453F));

		ModelPartData cube_r19 = HeadLayer.addChild("cube_r19", ModelPartBuilder.create().uv(63, 15).cuboid(0.8F, 0.2F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(4.5756F, -3.2371F, -3.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r20 = HeadLayer.addChild("cube_r20", ModelPartBuilder.create().uv(63, 15).mirrored().cuboid(-4.8F, 0.2F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.5756F, -3.2371F, -3.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r21 = HeadLayer.addChild("cube_r21", ModelPartBuilder.create().uv(62, 14).mirrored().cuboid(-3.5F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(62, 14).mirrored().cuboid(-3.5F, -0.5F, 1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(62, 14).mirrored().cuboid(-3.5F, -0.5F, 3.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(62, 14).mirrored().cuboid(-3.5F, -0.5F, 5.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.5756F, -3.2371F, -3.0F, 0.0F, 0.0F, -1.7453F));

		ModelPartData cube_r22 = HeadLayer.addChild("cube_r22", ModelPartBuilder.create().uv(62, 8).cuboid(-5.2F, -2.7F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -8.0F, 0.0F, 0.0F, 0.0F, -1.3963F));

		ModelPartData cube_r23 = HeadLayer.addChild("cube_r23", ModelPartBuilder.create().uv(62, 5).cuboid(0.2F, -2.7F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -8.0F, 0.0F, 0.0F, 0.0F, 1.3963F));

		ModelPartData cube_r24 = HeadLayer.addChild("cube_r24", ModelPartBuilder.create().uv(62, 57).cuboid(-2.5F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 5.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData waist = main.addChild("waist", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 5.3333F, 0.0F));

		ModelPartData Body = waist.addChild("Body", ModelPartBuilder.create().uv(24, 44).cuboid(-4.0F, 0.0F, -1.5F, 8.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData BodyLayer = Body.addChild("Body Layer", ModelPartBuilder.create().uv(0, 44).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 17.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r25 = BodyLayer.addChild("cube_r25", ModelPartBuilder.create().uv(46, 44).cuboid(-4.0F, -6.0F, 0.0F, 8.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 6.0F, 2.5F, 0.0873F, 0.0F, 0.0F));

		ModelPartData cube_r26 = BodyLayer.addChild("cube_r26", ModelPartBuilder.create().uv(56, 0).cuboid(-0.5F, -6.5F, -1.0F, 1.0F, 15.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.95F, 14.0874F, 2.4052F, 1.1781F, 0.0F, 0.0F));

		ModelPartData cube_r27 = BodyLayer.addChild("cube_r27", ModelPartBuilder.create().uv(8, 70).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.8F, 12.6F, -2.2F, 0.0F, 0.0F, -0.2356F));

		ModelPartData cube_r28 = BodyLayer.addChild("cube_r28", ModelPartBuilder.create().uv(0, 70).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.8F, 11.7F, -2.2F, 0.0F, 0.0F, 0.0698F));

		ModelPartData cube_r29 = BodyLayer.addChild("cube_r29", ModelPartBuilder.create().uv(62, 0).cuboid(-1.0F, -2.0F, -1.0F, 5.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.5408F, 1.571F, -1.3F, 0.2618F, 0.0F, 0.0F));

		ModelPartData cube_r30 = BodyLayer.addChild("cube_r30", ModelPartBuilder.create().uv(64, 70).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.6F, 9.6F, -2.2F, 0.0F, 0.0F, 0.5934F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		float scale = 1.5f;
		matrices.scale(scale, scale, scale);
		float translate = -0.5f;
		matrices.translate(0f, translate,0f);
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(PriestSkeletonEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(PriestSkeletonModelAnimation.WALK,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(PriestSkeletonEntity.FIRE_SKILL, PriestSkeletonModelAnimation.FIRE,animationProgress,1.0f);
		this.updateAnimation(PriestSkeletonEntity.SWEEP_SKILL, PriestSkeletonModelAnimation.SWEEP,animationProgress,1.0f);
		this.updateAnimation(PriestSkeletonEntity.SPIKE_SKILL, PriestSkeletonModelAnimation.SPIKE,animationProgress,1.0f);
		this.updateAnimation(PriestSkeletonEntity.BULLET_SKILL, PriestSkeletonModelAnimation.BULLET,animationProgress,1.0f);

		this.Head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.Head.pitch = headPitch * (float) (Math.PI / 180.0);
	}
}