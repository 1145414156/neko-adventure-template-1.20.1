
package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.animation.mob.ShooterAnimation;
import com.nekoadventure.entity.mob.ShooterEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

public class ShooterEntityModel extends SinglePartEntityModel<ShooterEntity> {
	private final ModelPart main;
	private final ModelPart LeftArm;
	private final ModelPart RightArm;
	private final ModelPart weapon;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart layer;
	public ShooterEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.LeftArm = main.getChild("LeftArm");
		this.RightArm = main.getChild("RightArm");
		this.weapon = RightArm.getChild("weapon");
		this.LeftLeg = main.getChild("LeftLeg");
		this.RightLeg = main.getChild("RightLeg");
		this.body = main.getChild("body");
		this.head = main.getChild("head");
		this.nose = head.getChild("nose");
		this.layer = head.getChild("layer");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData LeftArm = main.addChild("LeftArm", ModelPartBuilder.create().uv(32, 51).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -22.0F, 0.0F, -0.8942F, 0.5392F, 0.3911F));

		ModelPartData RightArm = main.addChild("RightArm", ModelPartBuilder.create().uv(16, 51).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -22.0F, 0.0F, -0.7777F, -0.1231F, -0.124F));

		ModelPartData weapon = RightArm.addChild("weapon", ModelPartBuilder.create().uv(47, 68).cuboid(1.0F, -1.0F, -20.0F, 2.0F, 2.0F, 21.0F, new Dilation(0.0F))
		.uv(61, 98).cuboid(1.0F, 1.0F, -2.0F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 9.0F, 0.0F));

		ModelPartData cube_r1 = weapon.addChild("cube_r1", ModelPartBuilder.create().uv(76, 75).cuboid(0.0F, -2.0F, -4.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, 1.6F, -8.4F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r2 = weapon.addChild("cube_r2", ModelPartBuilder.create().uv(64, 78).cuboid(0.0F, -2.0F, -4.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, 3.3F, -3.3F, -0.3491F, 0.0F, 0.0F));

		ModelPartData LeftLeg = main.addChild("LeftLeg", ModelPartBuilder.create().uv(0, 51).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -11.0F, 0.0F));

		ModelPartData RightLeg = main.addChild("RightLeg", ModelPartBuilder.create().uv(40, 0).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -11.0F, 0.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(32, 21).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 69).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, -24.0F, 0.0F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(0, 21).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.0F, 0.0F));

		ModelPartData nose = head.addChild("nose", ModelPartBuilder.create().uv(48, 51).cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

		ModelPartData layer = head.addChild("layer", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -11.0F, -5.0F, 10.0F, 11.0F, 10.0F, new Dilation(0.0F))
		.uv(1, 39).cuboid(-1.5F, -13.0F, -5.0F, 3.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData layer_r1 = layer.addChild("layer_r1", ModelPartBuilder.create().uv(41, 16).cuboid(-2.0F, -1.0F, 2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -3.0F, 6.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData layer_r2 = layer.addChild("layer_r2", ModelPartBuilder.create().uv(31, 39).cuboid(-4.0F, -1.0F, -5.0F, 3.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -6.0F, 6.0F, -1.5708F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(ShooterEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(ShooterAnimation.WALK,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(entity.attackAnimationState,ShooterAnimation.ATTACK,animationProgress,1.0f);

		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = headPitch * (float) (Math.PI / 180.0);
	}
}