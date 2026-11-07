package com.nekoadventure.entity.model.mob;


import com.nekoadventure.entity.animation.mob.CobraEntityAnimation;
import com.nekoadventure.entity.mob.CobraEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class CobraEntityModel extends SinglePartEntityModel<CobraEntity> {
	private final ModelPart main;
	private final ModelPart tail_seg_01;
	private final ModelPart body_seg_02;
	private final ModelPart body_seg_03;
	private final ModelPart body_seg_04;
	private final ModelPart body_seg_05;
	private final ModelPart body_seg_06;
	private final ModelPart body_seg_07;
	private final ModelPart body_seg_08;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart fang_left;
	private final ModelPart tongue_root;
	private final ModelPart hood;
	public CobraEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.tail_seg_01 = this.main.getChild("tail_seg_01");
		this.body_seg_02 = this.tail_seg_01.getChild("body_seg_02");
		this.body_seg_03 = this.body_seg_02.getChild("body_seg_03");
		this.body_seg_04 = this.body_seg_03.getChild("body_seg_04");
		this.body_seg_05 = this.body_seg_04.getChild("body_seg_05");
		this.body_seg_06 = this.body_seg_05.getChild("body_seg_06");
		this.body_seg_07 = this.body_seg_06.getChild("body_seg_07");
		this.body_seg_08 = this.body_seg_07.getChild("body_seg_08");
		this.neck = this.body_seg_08.getChild("neck");
		this.head = this.neck.getChild("head");
		this.fang_left = this.head.getChild("fang_left");
		this.tongue_root = this.head.getChild("tongue_root");
		this.hood = this.head.getChild("hood");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.3F, 24.0F, 0.0F));

		ModelPartData tail_seg_01 = main.addChild("tail_seg_01", ModelPartBuilder.create().uv(20, 31).cuboid(-1.0F, -0.5F, 0.0F, 2.0F, 2.0F, 1.5F, new Dilation(0.0F))
				.uv(32, 5).cuboid(-0.5F, -1.0F, 1.5F, 1.0F, 2.0F, 1.25F, new Dilation(0.0F)), ModelTransform.of(-0.15F, -1.5F, 5.57F, 0.0F, 0.0087F, 0.0F));

		ModelPartData body_seg_02 = tail_seg_01.addChild("body_seg_02", ModelPartBuilder.create().uv(26, 26).cuboid(-1.0F, -0.5F, -3.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0087F, 0.0F));

		ModelPartData body_seg_03 = body_seg_02.addChild("body_seg_03", ModelPartBuilder.create().uv(28, 19).cuboid(-1.0F, -0.5F, -3.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.0F, -0.7418F, 0.0087F, 0.0F));

		ModelPartData body_seg_04 = body_seg_03.addChild("body_seg_04", ModelPartBuilder.create().uv(0, 29).cuboid(-1.0F, -0.5F, -3.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.0F, 1.4835F, 0.0087F, 0.0F));

		ModelPartData body_seg_05 = body_seg_04.addChild("body_seg_05", ModelPartBuilder.create().uv(10, 30).cuboid(-1.0F, -0.5F, -3.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.0F, -0.7418F, -0.0349F, 0.0F));

		ModelPartData body_seg_06 = body_seg_05.addChild("body_seg_06", ModelPartBuilder.create().uv(16, 12).cuboid(-1.0F, -0.5F, -4.7F, 2.0F, 2.0F, 4.7F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.0F, -1.1345F, 0.0F, 0.0F));

		ModelPartData body_seg_07 = body_seg_06.addChild("body_seg_07", ModelPartBuilder.create().uv(14, 19).cuboid(-1.0F, -0.5F, -4.7F, 2.0F, 2.0F, 4.7F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.7F, -0.5236F, 0.0F, 0.0F));

		ModelPartData body_seg_08 = body_seg_07.addChild("body_seg_08", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -0.5F, -4.7F, 2.0F, 2.0F, 4.7F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.7F, -0.3491F, 0.0F, 0.0F));

		ModelPartData neck = body_seg_08.addChild("neck", ModelPartBuilder.create().uv(20, 5).cuboid(-1.25F, -1.25F, -3.0F, 2.5F, 2.5F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.7F, 0.4363F, 0.0F, 0.0F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 12).cuboid(-2.0F, -1.5F, -4.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(30, 14).cuboid(-1.0F, -2.0F, -2.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(20, 0).cuboid(-1.5F, 0.25F, -3.5F, 3.0F, 1.0F, 3.5F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -2.5F, 1.5708F, 0.0F, 0.0F));

		ModelPartData fang_left = head.addChild("fang_left", ModelPartBuilder.create(), ModelTransform.pivot(-1.125F, 0.5F, -3.4F));

		ModelPartData tongue_root = head.addChild("tongue_root", ModelPartBuilder.create().uv(30, 11).cuboid(-0.35F, 0.25F, -1.8F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F))
				.uv(28, 24).cuboid(-1.35F, 0.25F, -3.4F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F))
				.uv(28, 31).cuboid(0.65F, 0.25F, -3.4F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F))
				.uv(30, 17).cuboid(-1.4F, 0.25F, -3.4F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.5F, -3.5F, 0.1396F, 0.0F, 0.0F));

		ModelPartData hood = head.addChild("hood", ModelPartBuilder.create().uv(14, 26).cuboid(0.75F, -0.5F, -2.5F, 3.0F, 1.0F, 2.5F, new Dilation(0.0F))
				.uv(14, 26).mirrored().cuboid(-6.1F, -0.5F, -2.5F, 3.0F, 1.0F, 2.5F, new Dilation(0.0F)).mirrored(false)
				.uv(0, 6).cuboid(0.6F, -0.5F, 0.0F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F))
				.uv(0, 6).mirrored().cuboid(-7.05F, -0.5F, 0.0F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.25F, 0.0F, -0.5F, -1.6144F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}



	@Override
	public ModelPart getPart() {
		return this.main;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(CobraEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(CobraEntityAnimation.walk,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(entity.attackAnimationState, CobraEntityAnimation.attack, animationProgress);
	}
}