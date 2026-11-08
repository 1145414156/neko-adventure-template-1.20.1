package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.animation.mob.JarGremlinEntityAnimation;
import com.nekoadventure.entity.mob.JarGremlinHeadEntity;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.model.*;

public class JarGremlinHeadEntityModel extends SinglePartEntityModel<JarGremlinHeadEntity> {
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart jar;
	public JarGremlinHeadEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.head = this.body.getChild("head");
		this.jar = this.head.getChild("jar");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.75F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -5.75F, 0.0F));

		ModelPartData jar = head.addChild("jar", ModelPartBuilder.create().uv(0, 0).cuboid(-3.375F, -5.95F, -3.0F, 7.0F, 6.0F, 6.0F, new Dilation(0.0F))
				.uv(22, 12).cuboid(-1.925F, -6.95F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 22).cuboid(-2.425F, -7.95F, -2.5F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.75F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(JarGremlinHeadEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(JarGremlinEntityAnimation.move,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(entity.rangedAttackAnimationState, JarGremlinEntityAnimation.shoot, animationProgress);

		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = headPitch * (float) (Math.PI / 180.0);
	}
}