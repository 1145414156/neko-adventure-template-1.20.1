package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.animation.mob.ChestMimicEntityAnimation;
import com.nekoadventure.entity.animation.mob.CobraEntityAnimation;
import com.nekoadventure.entity.mob.ChestMimicEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

// Made with Blockbench 5.2.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class ChestMimicEntityModel extends SinglePartEntityModel<ChestMimicEntity> {
	private final ModelPart main;
	private final ModelPart chest_base;
	private final ModelPart teeth_lower;
	private final ModelPart tongue_root;
	private final ModelPart tongue_segment_01;
	private final ModelPart tongue_segment_02;
	private final ModelPart tongue_segment_03;
	private final ModelPart tongue_segment_04;
	private final ModelPart tongue_segment_05;
	private final ModelPart tongue_segment_06;
	private final ModelPart tongue_segment_07;
	private final ModelPart tongue_tip;
	private final ModelPart chest_up;
	private final ModelPart teeth_upper;
	private final ModelPart lock;
	public ChestMimicEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.chest_base = this.main.getChild("chest_base");
		this.teeth_lower = this.chest_base.getChild("teeth_lower");
		this.tongue_root = this.chest_base.getChild("tongue_root");
		this.tongue_segment_01 = this.tongue_root.getChild("tongue_segment_01");
		this.tongue_segment_02 = this.tongue_segment_01.getChild("tongue_segment_02");
		this.tongue_segment_03 = this.tongue_segment_02.getChild("tongue_segment_03");
		this.tongue_segment_04 = this.tongue_segment_03.getChild("tongue_segment_04");
		this.tongue_segment_05 = this.tongue_segment_04.getChild("tongue_segment_05");
		this.tongue_segment_06 = this.tongue_segment_05.getChild("tongue_segment_06");
		this.tongue_segment_07 = this.tongue_segment_06.getChild("tongue_segment_07");
		this.tongue_tip = this.tongue_segment_07.getChild("tongue_tip");
		this.chest_up = this.main.getChild("chest_up");
		this.teeth_upper = this.chest_up.getChild("teeth_upper");
		this.lock = this.chest_up.getChild("lock");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData chest_base = main.addChild("chest_base", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, -15.75F, 16.0F, 11.0F, 15.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.0F, 7.75F));

		ModelPartData teeth_lower = chest_base.addChild("teeth_lower", ModelPartBuilder.create().uv(24, 57).cuboid(5.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(36, 57).cuboid(1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 58).cuboid(-3.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 58).cuboid(-7.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, -14.25F));

		ModelPartData tongue_root = chest_base.addChild("tongue_root", ModelPartBuilder.create(), ModelTransform.of(0.0F, 1.85F, -7.5F, -0.7854F, 0.0F, 0.0F));

		ModelPartData tongue_root_cube_r1 = tongue_root.addChild("tongue_root_cube_r1", ModelPartBuilder.create().uv(18, 52).cuboid(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -1.575F, -0.1178F, 0.0F, 0.0F));

		ModelPartData tongue_segment_01 = tongue_root.addChild("tongue_segment_01", ModelPartBuilder.create().uv(0, 46).cuboid(-2.0F, -1.55F, -4.0F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.0F, 0.2784F, 0.0F, 0.0F));

		ModelPartData tongue_segment_02 = tongue_segment_01.addChild("tongue_segment_02", ModelPartBuilder.create().uv(18, 46).cuboid(-2.0F, -1.45F, -4.375F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.64F, 0.5976F, 0.0F, 0.0F));

		ModelPartData tongue_segment_03 = tongue_segment_02.addChild("tongue_segment_03", ModelPartBuilder.create().uv(36, 46).cuboid(-2.0F, -1.5F, -5.0F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.9F, 0.3732F, 0.0F, 0.0F));

		ModelPartData tongue_segment_04 = tongue_segment_03.addChild("tongue_segment_04", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, -4.74F, 0.0433F, 0.0F, 0.0F));

		ModelPartData tongue_segment_04_cube_r1 = tongue_segment_04.addChild("tongue_segment_04_cube_r1", ModelPartBuilder.create().uv(34, 52).cuboid(-2.0F, -0.65F, -1.865F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.675F, -2.225F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tongue_segment_05 = tongue_segment_04.addChild("tongue_segment_05", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, -3.64F, -0.3651F, 0.0F, 0.0F));

		ModelPartData tongue_segment_05_cube_r1 = tongue_segment_05.addChild("tongue_segment_05_cube_r1", ModelPartBuilder.create().uv(54, 46).cuboid(-2.0F, -0.45F, -1.1F, 4.0F, 1.0F, 2.5F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.35F, -1.85F, 0.1745F, 0.0F, 0.0F));

		ModelPartData tongue_segment_06 = tongue_segment_05.addChild("tongue_segment_06", ModelPartBuilder.create().uv(0, 52).cuboid(-2.0F, -0.5F, -5.0F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -2.5F, -0.9273F, 0.0F, 0.0F));

		ModelPartData tongue_segment_07 = tongue_segment_06.addChild("tongue_segment_07", ModelPartBuilder.create().uv(50, 52).cuboid(-2.0F, -0.3F, -3.0F, 4.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.5F, -0.9599F, 0.0F, 0.0F));

		ModelPartData tongue_tip = tongue_segment_07.addChild("tongue_tip", ModelPartBuilder.create().uv(50, 56).cuboid(-1.5F, -0.525F, -1.975F, 3.0F, 1.0F, 2.5F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.0F, -1.0472F, 0.0F, 0.0F));

		ModelPartData chest_up = main.addChild("chest_up", ModelPartBuilder.create().uv(0, 26).cuboid(-8.0F, -5.0F, -15.0F, 16.0F, 5.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.0F, 7.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData teeth_upper = chest_up.addChild("teeth_upper", ModelPartBuilder.create().uv(30, 57).cuboid(5.0F, -1.0F, -1.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(42, 57).cuboid(1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 58).cuboid(-3.0F, -1.0F, -1.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(48, 60).cuboid(-7.0F, -1.0F, -1.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, -13.5F));

		ModelPartData lock = chest_up.addChild("lock", ModelPartBuilder.create().uv(18, 57).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -15.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(ChestMimicEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(ChestMimicEntityAnimation.walk,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(entity.attackAnimationState, ChestMimicEntityAnimation.attack, animationProgress);
	}
}