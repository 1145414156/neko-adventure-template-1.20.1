package com.nekoadventure.entity.model.mob;
import com.nekoadventure.entity.animation.mob.JarGremlinEntityAnimation;
import com.nekoadventure.entity.animation.mob.MinderAnimation;
import com.nekoadventure.entity.animation.mob.TreasureHunterAnimation;
import com.nekoadventure.entity.mob.JarGremlinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class JarGremlinEntityModel extends SinglePartEntityModel<JarGremlinEntity> {
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart jar;
	private final ModelPart arm_left;
	private final ModelPart arm_right;
	private final ModelPart leg_left;
	private final ModelPart leg_right;
	public JarGremlinEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.head = this.body.getChild("head");
		this.jar = this.head.getChild("jar");
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

		ModelPartData head = body.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -5.75F, 0.0F));

		ModelPartData jar = head.addChild("jar", ModelPartBuilder.create().uv(0, 0).cuboid(-3.375F, -5.95F, -3.0F, 7.0F, 6.0F, 6.0F, new Dilation(0.0F))
		.uv(22, 12).cuboid(-1.925F, -6.95F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 22).cuboid(-2.425F, -7.95F, -2.5F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.75F, 0.0F));

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
	public void setAngles(JarGremlinEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(JarGremlinEntityAnimation.move,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(entity.meleeAttackAnimationState, JarGremlinEntityAnimation.attack, animationProgress);
		this.updateAnimation(entity.rangedAttackAnimationState, JarGremlinEntityAnimation.shoot, animationProgress);

		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = headPitch * (float) (Math.PI / 180.0);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}