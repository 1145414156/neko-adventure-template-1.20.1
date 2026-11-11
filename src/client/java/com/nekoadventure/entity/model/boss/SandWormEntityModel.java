package com.nekoadventure.entity.model.boss;

import com.nekoadventure.entity.animation.boss.PriestSkeletonModelAnimation;
import com.nekoadventure.entity.animation.boss.SandWormEntityModelAnimation;
import com.nekoadventure.entity.boss.SandWormEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SandWormEntityModel extends SinglePartEntityModel<SandWormEntity> {
	private final ModelPart main;
	private final ModelPart tooth;
	private final ModelPart tooth1;
	private final ModelPart tooth_up1;
	private final ModelPart tooth2;
	private final ModelPart tooth_up2;
	public SandWormEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.tooth = this.main.getChild("tooth");
		this.tooth1 = this.main.getChild("tooth1");
		this.tooth_up1 = this.tooth1.getChild("tooth_up1");
		this.tooth2 = this.main.getChild("tooth2");
		this.tooth_up2 = this.tooth2.getChild("tooth_up2");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -14.0F, -8.0F, 14.0F, 14.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData tooth = main.addChild("tooth", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = tooth.addChild("cube_r1", ModelPartBuilder.create().uv(20, 46).cuboid(3.0831F, -0.5F, -3.9288F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.4209F, -7.5F, -5.6808F, 0.2336F, -0.2528F, 0.9726F));

		ModelPartData cube_r2 = tooth.addChild("cube_r2", ModelPartBuilder.create().uv(20, 46).cuboid(3.0831F, -0.5F, -3.9288F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.4209F, -7.5F, -5.6808F, -0.2336F, -0.2528F, -0.9726F));

		ModelPartData cube_r3 = tooth.addChild("cube_r3", ModelPartBuilder.create().uv(20, 46).cuboid(3.0831F, -0.5F, -3.9288F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.4209F, -7.5F, -5.6808F, 0.0F, -0.1309F, 0.0F));

		ModelPartData cube_r4 = tooth.addChild("cube_r4", ModelPartBuilder.create().uv(20, 46).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.2156F, -3.6625F, -7.5433F, -0.2336F, -0.2528F, 2.169F));

		ModelPartData cube_r5 = tooth.addChild("cube_r5", ModelPartBuilder.create().uv(20, 46).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.2156F, -11.3375F, -7.5433F, 0.2336F, -0.2528F, -2.169F));

		ModelPartData cube_r6 = tooth.addChild("cube_r6", ModelPartBuilder.create().uv(20, 46).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.3557F, -7.5F, -8.1168F, 0.0F, -0.1309F, -3.1416F));

		ModelPartData tooth1 = main.addChild("tooth1", ModelPartBuilder.create(), ModelTransform.of(-6.9332F, -8.0F, -7.612F, 0.0F, 0.2618F, 0.0F));

		ModelPartData cube_r7 = tooth1.addChild("cube_r7", ModelPartBuilder.create().uv(34, 42).cuboid(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(24, 42).cuboid(-1.0F, -3.0F, -4.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(32, 30).cuboid(-2.0F, -2.0F, -5.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(1.7284F, 1.0F, 1.4206F, 0.0F, 0.4363F, 0.0F));

		ModelPartData cube_r8 = tooth1.addChild("cube_r8", ModelPartBuilder.create().uv(0, 30).cuboid(-3.0F, -2.0F, -9.75F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.7284F, 1.0F, 1.4206F, 0.0F, 0.0436F, 0.0F));

		ModelPartData tooth_up1 = tooth1.addChild("tooth_up1", ModelPartBuilder.create(), ModelTransform.of(-0.5853F, 0.0F, -7.1456F, 0.0F, 0.3927F, 0.0F));

		ModelPartData cube_r9 = tooth_up1.addChild("cube_r9", ModelPartBuilder.create().uv(24, 38).cuboid(2.05F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(5.2396F, 0.0F, -3.0081F, 0.0F, -1.8762F, 0.0F));

		ModelPartData cube_r10 = tooth_up1.addChild("cube_r10", ModelPartBuilder.create().uv(0, 38).cuboid(-0.7F, -1.0F, -1.05F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.1351F, -0.01F, -1.392F, 0.0F, -1.309F, 0.0F));

		ModelPartData tooth2 = main.addChild("tooth2", ModelPartBuilder.create(), ModelTransform.of(6.9332F, -8.0F, -7.612F, 0.0F, -0.2618F, 0.0F));

		ModelPartData cube_r11 = tooth2.addChild("cube_r11", ModelPartBuilder.create().uv(10, 44).cuboid(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 44).cuboid(-1.0F, -3.0F, -4.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(32, 36).cuboid(-1.0F, -2.0F, -5.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.7284F, 1.0F, 1.4206F, 0.0F, -0.4363F, 0.0F));

		ModelPartData cube_r12 = tooth2.addChild("cube_r12", ModelPartBuilder.create().uv(16, 30).cuboid(1.0F, -2.0F, -9.75F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-1.7284F, 1.0F, 1.4203F, 0.0F, -0.0436F, 0.0F));

		ModelPartData tooth_up2 = tooth2.addChild("tooth_up2", ModelPartBuilder.create(), ModelTransform.of(0.5853F, 0.0F, -7.1456F, 0.0F, -0.3927F, 0.0F));

		ModelPartData cube_r13 = tooth_up2.addChild("cube_r13", ModelPartBuilder.create().uv(44, 42).cuboid(-3.05F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-5.2396F, 0.0F, -3.0081F, 0.0F, 1.8762F, 0.0F));

		ModelPartData cube_r14 = tooth_up2.addChild("cube_r14", ModelPartBuilder.create().uv(12, 38).cuboid(-1.3F, -1.0F, -1.05F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.1351F, -0.01F, -1.392F, 0.0F, 1.309F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(SandWormEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.updateAnimation(entity.DASH_SKILL_ANI, SandWormEntityModelAnimation.dash,animationProgress,1.0f);
		this.updateAnimation(entity.BULLET_SKILL_ANI, SandWormEntityModelAnimation.missile,animationProgress,1.0f);
		this.updateAnimation(entity.SUCK_SKILL_ANI, SandWormEntityModelAnimation.suck,animationProgress,1.0f);
		this.updateAnimation(entity.ACCELERATION_SKILL_ANI, SandWormEntityModelAnimation.acceleration,animationProgress,1.0f);
		this.updateAnimation(entity.BARRAGE_SKILL_ANI, SandWormEntityModelAnimation.barrage,animationProgress,1.0f);


	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
	                   float red, float green, float blue, float alpha) {
		matrices.push();
		matrices.translate(0.0, -2.5, 0.0);
		matrices.scale(3.0f, 3.0f, 3.0f);
		main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}
}