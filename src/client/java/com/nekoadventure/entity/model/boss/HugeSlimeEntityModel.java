// Made with Blockbench 5.1.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.nekoadventure.entity.model.boss;

import com.nekoadventure.entity.animation.boss.HugeSlimeEntityModelAnimation;
import com.nekoadventure.entity.animation.mob.MinderAnimation;
import com.nekoadventure.entity.animation.mob.MuddySpiderModelAnimation;
import com.nekoadventure.entity.animation.mob.ShooterAnimation;
import com.nekoadventure.entity.animation.mob.TreasureHunterAnimation;
import com.nekoadventure.entity.boss.HugeSlimeEntity;
import com.nekoadventure.entity.mob.MuddySpiderEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.AnimationState;
import org.jetbrains.annotations.NotNull;

public class HugeSlimeEntityModel extends SinglePartEntityModel<HugeSlimeEntity> {
	private final ModelPart main;
	private final ModelPart inner;
	private final ModelPart eyes;
	private final ModelPart mouth;
	private final ModelPart headLayer;
	private final ModelPart eyes1;
	private final ModelPart outer;
	private final boolean isOuterLayer;

	public HugeSlimeEntityModel(ModelPart root, boolean isOuterLayer) {
		this.main = root.getChild("main");
		this.inner = main.getChild("inner");
		this.eyes = inner.getChild("eyes");
		this.mouth = inner.getChild("mouth");
		this.headLayer = inner.getChild("headLayer");
		this.eyes1 = inner.getChild("eyes1");
		this.outer = main.getChild("outer");

		this.isOuterLayer = isOuterLayer;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

		ModelPartData inner = main.addChild("inner", ModelPartBuilder.create().uv(0, 48).cuboid(-11.0F, -11.0F, -11.0F, 22.0F, 22.0F, 22.0F, new Dilation(-1.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData eyes = inner.addChild("eyes", ModelPartBuilder.create().uv(88, 88).cuboid(4.0F, -3.0F, 7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 92).cuboid(-8.0F, -3.0F, 7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData mouth = inner.addChild("mouth", ModelPartBuilder.create().uv(16, 92).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 5.0F, 10.0F));

		ModelPartData headLayer = inner.addChild("headLayer", ModelPartBuilder.create().uv(88, 48).cuboid(-12.5F, -16.0F, -12.5F, 25.0F, 5.0F, 25.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData eyes1 = inner.addChild("eyes1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = eyes1.addChild("cube_r1", ModelPartBuilder.create().uv(88, 83).cuboid(-5.0F, -1.0F, -3.0F, 6.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-4.475F, -2.2F, 10.65F, 0.0F, 0.0F, 0.2182F));

		ModelPartData cube_r2 = eyes1.addChild("cube_r2", ModelPartBuilder.create().uv(88, 78).cuboid(-1.0F, -1.0F, -3.0F, 6.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(4.475F, -2.2F, 10.65F, 0.0F, 0.0F, -0.1745F));

		ModelPartData outer = main.addChild("outer", ModelPartBuilder.create().uv(0, 0).cuboid(-12.0F, -12.0F, -12.0F, 24.0F, 24.0F, 24.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		float scale = 2.5f;
		matrices.scale(scale, scale, scale);
		inner.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}



	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(HugeSlimeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(HugeSlimeEntity.TRAMPLE_SKILL_ANI, HugeSlimeEntityModelAnimation.TRAMPLE,animationProgress,1.0f);
		this.updateAnimation(HugeSlimeEntity.SUMMON_SKILL_ANI, HugeSlimeEntityModelAnimation.SUMMON,animationProgress,1.0f);
		this.updateAnimation(HugeSlimeEntity.BULLET_SKILL_ANI, HugeSlimeEntityModelAnimation.BULLET,animationProgress,1.0f);
		this.updateAnimation(HugeSlimeEntity.DASH_SKILL_ANI, HugeSlimeEntityModelAnimation.DASH,animationProgress,1.0f);

	}
}