
package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.animation.mob.MinderAnimation;
import com.nekoadventure.entity.mob.MinderEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

public class MinderEntityModel extends SinglePartEntityModel<MinderEntity> {
	private final ModelPart main;
	private final ModelPart LeftArm;
	private final ModelPart RightArm;
	private final ModelPart weapon;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bone5;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart layer;
	public MinderEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.LeftArm = main.getChild("LeftArm");
		this.RightArm = main.getChild("RightArm");
		this.weapon = RightArm.getChild("weapon");
		this.bone = weapon.getChild("bone");
		this.bone2 = bone.getChild("bone2");
		this.bone3 = bone2.getChild("bone3");
		this.bone4 = bone3.getChild("bone4");
		this.bone5 = bone4.getChild("bone5");
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

		ModelPartData LeftArm = main.addChild("LeftArm", ModelPartBuilder.create().uv(32, 51).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -22.0F, 0.0F));

		ModelPartData RightArm = main.addChild("RightArm", ModelPartBuilder.create().uv(16, 51).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -22.0F, 0.0F));

		ModelPartData weapon = RightArm.addChild("weapon", ModelPartBuilder.create().uv(56, 12).cuboid(0.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 9.0F, -1.0F));

		ModelPartData bone = weapon.addChild("bone", ModelPartBuilder.create().uv(56, 12).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone2 = bone.addChild("bone2", ModelPartBuilder.create().uv(56, 12).cuboid(0.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone3 = bone2.addChild("bone3", ModelPartBuilder.create().uv(56, 12).cuboid(0.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone4 = bone3.addChild("bone4", ModelPartBuilder.create().uv(56, 12).cuboid(0.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone5 = bone4.addChild("bone5", ModelPartBuilder.create().uv(56, 12).cuboid(0.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
		.uv(31, 70).cuboid(0.5F, -1.0F, -4.0F, 0.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

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
	public void setAngles(MinderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(MinderAnimation.WALK,limbAngle,limbDistance,2f,2.5f);

		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = headPitch * (float) (Math.PI / 180.0);
	}
}