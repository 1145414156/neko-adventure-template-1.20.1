// Made with Blockbench 5.1.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.animation.mob.StoneGolemAnimations;
import com.nekoadventure.entity.mob.StoneGolemEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class StoneGolemEntityModel extends SinglePartEntityModel<StoneGolemEntity> {
	private final ModelPart main;
	private final ModelPart leg1;
	private final ModelPart leg0;
	private final ModelPart arm1;
	private final ModelPart arm2;
	private final ModelPart arm0;
	private final ModelPart arm3;
	private final ModelPart Head;
	private final ModelPart body;
	public StoneGolemEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.leg1 = main.getChild("leg1");
		this.leg0 = main.getChild("leg0");
		this.arm1 = main.getChild("arm1");
		this.arm2 = arm1.getChild("arm2");
		this.arm0 = main.getChild("arm0");
		this.arm3 = arm0.getChild("arm3");
		this.Head = main.getChild("Head");
		this.body = main.getChild("body");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -7.0F, 0.0F));

		ModelPartData leg1 = main.addChild("leg1", ModelPartBuilder.create().uv(0, 41).cuboid(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 18.0F, 0.0F));

		ModelPartData leg0 = main.addChild("leg0", ModelPartBuilder.create().uv(32, 23).cuboid(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 18.0F, 0.0F));

		ModelPartData arm1 = main.addChild("arm1", ModelPartBuilder.create().uv(58, 0).cuboid(0.0F, -2.5F, -3.0F, 4.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(9.0F, 5.0F, -9.0F));

		ModelPartData arm2 = arm1.addChild("arm2", ModelPartBuilder.create().uv(42, 44).cuboid(-2.0F, -0.5F, -3.0F, 4.0F, 16.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 12.0F, 0.0F, -0.3442F, -0.0121F, 0.059F));

		ModelPartData cube_r1 = arm2.addChild("cube_r1", ModelPartBuilder.create().uv(62, 50).cuboid(-9.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 12.0F, 0.0F, 0.8313F, 0.6865F, 1.1477F));

		ModelPartData cube_r2 = arm2.addChild("cube_r2", ModelPartBuilder.create().uv(68, 72).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 12.0F, -3.0F, 0.0F, 0.829F, 0.6545F));

		ModelPartData cube_r3 = arm2.addChild("cube_r3", ModelPartBuilder.create().uv(60, 72).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 7.0F, 0.0F, 0.0F, 0.829F, 0.0F));

		ModelPartData arm0 = main.addChild("arm0", ModelPartBuilder.create().uv(0, 62).cuboid(-4.0F, -2.5F, -3.0F, 4.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, 4.0F, -8.0F));

		ModelPartData cube_r4 = arm0.addChild("cube_r4", ModelPartBuilder.create().uv(62, 46).cuboid(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.4F, 0.1F, 0.0F, 1.3437F, -0.908F, -1.1531F));

		ModelPartData cube_r5 = arm0.addChild("cube_r5", ModelPartBuilder.create().uv(52, 72).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.8F, 7.0F, 0.0F, 2.9138F, -0.6253F, -2.375F));

		ModelPartData arm3 = arm0.addChild("arm3", ModelPartBuilder.create().uv(22, 44).cuboid(-2.0F, -0.5F, -3.0F, 4.0F, 16.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 12.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ModelPartData Head = main.addChild("Head", ModelPartBuilder.create().uv(0, 23).cuboid(-4.0F, -11.0F, -5.5F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F))
		.uv(44, 66).cuboid(-1.0F, -4.0F, -7.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, -12.0F));

		ModelPartData head_r1 = Head.addChild("head_r1", ModelPartBuilder.create().uv(44, 72).cuboid(-1.0F, -3.0F, -1.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -11.3F, 2.0F, 1.0617F, 0.9705F, -2.912F));

		ModelPartData head_r2 = Head.addChild("head_r2", ModelPartBuilder.create().uv(70, 66).cuboid(-1.0F, -3.0F, -1.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -11.3F, -4.0F, 2.618F, 1.4399F, -1.3963F));

		ModelPartData head_r3 = Head.addChild("head_r3", ModelPartBuilder.create().uv(70, 60).cuboid(-1.0F, -3.0F, -1.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -9.0F, -4.0F, 2.618F, 1.4399F, -1.789F));

		ModelPartData head_r4 = Head.addChild("head_r4", ModelPartBuilder.create().uv(70, 54).cuboid(-1.0F, -3.0F, -1.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -11.0F, 2.0F, -0.5236F, 0.7418F, 0.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, -9.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r6 = body.addChild("cube_r6", ModelPartBuilder.create().uv(62, 63).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -1.0F, -1.0F, 1.6428F, 1.3965F, 2.0216F));

		ModelPartData cube_r7 = body.addChild("cube_r7", ModelPartBuilder.create().uv(28, 66).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.0F, -4.0F, 1.5394F, -1.0189F, 2.3323F));

		ModelPartData cube_r8 = body.addChild("cube_r8", ModelPartBuilder.create().uv(20, 66).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 3.0F, -4.0F, 0.8235F, -0.032F, 2.7522F));

		ModelPartData cube_r9 = body.addChild("cube_r9", ModelPartBuilder.create().uv(62, 54).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -1.0F, -4.0F, 1.6428F, 1.3965F, 2.5452F));

		ModelPartData cube_r10 = body.addChild("cube_r10", ModelPartBuilder.create().uv(52, 66).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -1.0F, -1.0F, -1.6375F, -0.9199F, 1.2267F));

		ModelPartData cube_r11 = body.addChild("cube_r11", ModelPartBuilder.create().uv(62, 34).cuboid(-5.0F, -7.0F, -4.0F, 6.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 12.0F, -5.0F, -0.9445F, -0.5638F, 0.7408F));

		ModelPartData cube_r12 = body.addChild("cube_r12", ModelPartBuilder.create().uv(36, 66).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 7.0F, 5.0F, -0.9445F, -0.5638F, -0.2192F));

		ModelPartData cube_r13 = body.addChild("cube_r13", ModelPartBuilder.create().uv(54, 34).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -1.0F, 1.0F, -0.5236F, 0.3491F, 0.0F));

		ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(54, 23).cuboid(-4.5F, -1.0F, -3.0F, 9.0F, 5.0F, 6.0F, new Dilation(0.5F)), ModelTransform.of(0.0F, 11.0F, -2.0F, 0.1745F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(StoneGolemEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(StoneGolemAnimations.WALK,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(entity.attackAnimationState, StoneGolemAnimations.ATTACK, animationProgress);

		this.Head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.Head.pitch = headPitch * (float) (Math.PI / 180.0);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}