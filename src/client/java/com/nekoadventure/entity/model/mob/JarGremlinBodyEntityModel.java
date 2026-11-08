package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.animation.mob.JarGremlinEntityAnimation;
import com.nekoadventure.entity.mob.JarGremlinBodyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
public class JarGremlinBodyEntityModel extends SinglePartEntityModel<JarGremlinBodyEntity> {
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart arm_left;
	private final ModelPart arm_right;
	private final ModelPart leg_left;
	private final ModelPart leg_right;
	public JarGremlinBodyEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.arm_left = this.body.getChild("arm_left");
		this.arm_right = this.body.getChild("arm_right");
		this.leg_left = this.main.getChild("leg_left");
		this.leg_right = this.main.getChild("leg_right");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(0, 12).cuboid(-3.0F, -4.95F, -2.5F, 6.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.25F, 0.0F));

		ModelPartData arm_left = body.addChild("arm_left", ModelPartBuilder.create(), ModelTransform.of(-3.0F, -4.75F, 0.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData arm_left_r1 = arm_left.addChild("arm_left_r1", ModelPartBuilder.create().uv(20, 24).cuboid(-2.0F, -4.8F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.45F, 0.0F, 1.425F, -1.5708F, -0.6981F, 0.0F));

		ModelPartData arm_right = body.addChild("arm_right", ModelPartBuilder.create(), ModelTransform.of(3.0F, -4.75F, 0.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData arm_right_r1 = arm_right.addChild("arm_right_r1", ModelPartBuilder.create().uv(22, 17).cuboid(0.0F, -4.8F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.45F, 0.0F, 1.425F, -1.5708F, 0.6981F, 0.0F));

		ModelPartData leg_left = main.addChild("leg_left", ModelPartBuilder.create().uv(26, 0).cuboid(-1.25F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.25F, -5.0F, 0.0F));

		ModelPartData leg_right = main.addChild("leg_right", ModelPartBuilder.create().uv(26, 6).cuboid(-0.5F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -5.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(JarGremlinBodyEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(JarGremlinEntityAnimation.move,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(entity.attackAnimationState, JarGremlinEntityAnimation.attack, animationProgress);
	}
}