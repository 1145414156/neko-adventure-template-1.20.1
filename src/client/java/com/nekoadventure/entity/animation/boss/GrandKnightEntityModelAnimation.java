package com.nekoadventure.entity.animation.boss;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class GrandKnightEntityModelAnimation {
	public static final Animation walk = Animation.Builder.create(2.0F).looping()
		.addBoneAnimation("rightLeg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-25.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightLeg1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftLeg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(-25.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftLeg1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 15.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, -10.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("layer", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(-22.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final Animation grab = Animation.Builder.create(6.25F).looping()
			.addBoneAnimation("a", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(-27.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(-27.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("a", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createTranslationalVector(0.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 10.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 10.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(0.0F, 10.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(0.0F, 10.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightLeg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-24.5982F, -0.918F, -4.2091F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(-24.5982F, -0.918F, -4.2091F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(-24.5982F, -0.918F, -4.2091F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(53.7117F, -3.9007F, -4.5215F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(53.71F, -3.9F, -4.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(-1.29F, -3.9F, -4.52F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightLeg1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-61.3133F, -5.2813F, -8.8985F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(-61.3133F, -5.2813F, -8.8985F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(-61.3133F, -5.2813F, -8.8985F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(-12.9799F, 9.1803F, -4.9431F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(-12.98F, 9.18F, -4.94F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftLeg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(58.8707F, -4.905F, 8.6812F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(58.8707F, -4.905F, 8.6812F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(58.8707F, -4.905F, 8.6812F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(-22.142F, -0.7458F, -3.8106F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(-22.14F, -0.75F, -3.81F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftLeg1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-56.428F, -4.5384F, -8.448F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(-56.428F, -4.5384F, -8.448F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(-56.428F, -4.5384F, -8.448F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(-6.428F, -4.5384F, -8.448F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(-6.43F, -4.54F, -8.45F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 15.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 35.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 35.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(0.0F, 35.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, -7.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(0.0F, -7.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, -32.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.5F, AnimationHelper.createRotationalVector(0.0F, -32.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftHand", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(21.3108F, -53.3055F, -34.9444F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, -32.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, -32.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(0.0F, -32.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(24.7145F, 5.4627F, -2.1206F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(24.7145F, 5.4627F, -2.1206F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 32.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.75F, AnimationHelper.createRotationalVector(14.3609F, 24.3329F, -1.4186F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.5F, AnimationHelper.createRotationalVector(14.3609F, 24.3329F, -1.4186F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("layer", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(-42.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightHand", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(-74.782F, 2.4905F, 0.0047F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-74.782F, 2.4905F, 0.0047F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(-74.782F, 2.4905F, 0.0047F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createRotationalVector(-74.782F, 2.4905F, 0.0047F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(65.218F, 2.4905F, 0.0047F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(65.22F, 2.49F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(87.72F, 2.49F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.75F, AnimationHelper.createRotationalVector(122.718F, 2.4905F, 0.0047F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.8333F, AnimationHelper.createRotationalVector(122.8037F, 4.5924F, 1.3554F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.9167F, AnimationHelper.createRotationalVector(123.11F, 8.5963F, 3.9669F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.0F, AnimationHelper.createRotationalVector(122.8037F, 4.5924F, 1.3554F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.0833F, AnimationHelper.createRotationalVector(123.11F, 8.5963F, 3.9669F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.1667F, AnimationHelper.createRotationalVector(122.8037F, 4.5924F, 1.3554F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.25F, AnimationHelper.createRotationalVector(123.11F, 8.5963F, 3.9669F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.3333F, AnimationHelper.createRotationalVector(122.8037F, 4.5924F, 1.3554F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.4167F, AnimationHelper.createRotationalVector(123.11F, 8.5963F, 3.9669F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(122.718F, 2.4905F, 0.0047F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.6667F, AnimationHelper.createRotationalVector(123.11F, 8.5963F, 3.9669F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.875F, AnimationHelper.createRotationalVector(122.8037F, 4.5924F, 1.3554F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.125F, AnimationHelper.createRotationalVector(123.11F, 8.5963F, 3.9669F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.3333F, AnimationHelper.createRotationalVector(122.718F, 2.4905F, 0.0047F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.5F, AnimationHelper.createRotationalVector(122.718F, 2.4905F, 0.0047F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.75F, AnimationHelper.createRotationalVector(26.2907F, 76.5309F, -69.7482F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.875F, AnimationHelper.createRotationalVector(76.5861F, 25.8176F, -12.6084F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightHand", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.375F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 4.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 4.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 4.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.75F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.8333F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.9167F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.0F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.0833F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.1667F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.25F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.3333F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.4167F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.6667F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.875F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.125F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.3333F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.5F, AnimationHelper.createTranslationalVector(-3.25F, 4.75F, 7.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.75F, AnimationHelper.createTranslationalVector(2.5F, -1.25F, 0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.875F, AnimationHelper.createTranslationalVector(-0.5F, -1.25F, 8.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightHand1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(102.8788F, 12.0674F, 3.2882F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(102.8788F, 12.0674F, 3.2882F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(102.8788F, 12.0674F, 3.2882F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(37.8788F, 12.0674F, 3.2882F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(34.29F, 4.02F, 1.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 20.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 45.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightHand1", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 1.25F, 5.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 1.25F, 5.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 1.25F, 5.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, -1.25F, 2.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createTranslationalVector(0.0F, -1.25F, 2.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final Animation shieldSmash = Animation.Builder.create(4.25F).looping()
		.addBoneAnimation("a", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.25F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightLeg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.25F, AnimationHelper.createRotationalVector(42.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(42.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(-22.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightLeg1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.25F, AnimationHelper.createRotationalVector(-45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(-45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(-45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftLeg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.25F, AnimationHelper.createRotationalVector(-47.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(-47.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftLeg1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(-27.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, -12.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.0F, -12.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(0.0F, 35.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, -42.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(0.0F, -42.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(0.0F, 35.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9167F, AnimationHelper.createRotationalVector(0.0F, 35.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHand", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(22.7206F, -63.2092F, 7.7917F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(22.7206F, -63.2092F, 7.7917F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.875F, AnimationHelper.createRotationalVector(22.0243F, -30.7159F, 8.1084F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(22.7206F, -63.2092F, 7.7917F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(22.7206F, -63.2092F, 7.7917F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(22.1552F, -38.2147F, 8.0776F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(22.7206F, -63.2092F, 7.7917F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(22.7206F, -63.2092F, 7.7917F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(22.1552F, -38.2147F, 8.0776F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHand", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, -3.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, -3.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.875F, AnimationHelper.createTranslationalVector(2.0F, -4.0F, 6.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, -3.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, -3.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createTranslationalVector(2.0F, -4.0F, 6.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, -3.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, -3.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createTranslationalVector(2.0F, -4.0F, 6.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHand1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(-16.3609F, 5.2106F, -79.5574F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(-16.36F, 5.21F, -79.56F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.875F, AnimationHelper.createRotationalVector(1.0762F, -2.0539F, 10.062F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-16.3609F, 5.2106F, -79.5574F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(-6.387F, -2.39F, -59.9064F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(1.1861F, -11.6024F, 51.5471F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(-16.3609F, 5.2106F, -79.5574F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(-16.3609F, 5.2106F, -79.5574F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(1.1861F, -11.6024F, 51.5471F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9167F, AnimationHelper.createRotationalVector(1.1861F, -11.6024F, 51.5471F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHand1", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createTranslationalVector(1.8F, 6.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createTranslationalVector(1.8F, 6.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.875F, AnimationHelper.createTranslationalVector(3.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createTranslationalVector(1.8F, 6.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createTranslationalVector(1.8F, 6.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createTranslationalVector(3.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createTranslationalVector(1.8F, 6.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createTranslationalVector(1.8F, 6.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createTranslationalVector(3.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9167F, AnimationHelper.createTranslationalVector(3.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHandItem", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.875F, AnimationHelper.createRotationalVector(2.9678F, 10.4177F, -48.6166F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.8244F, 0.5624F, -17.4676F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(2.9678F, 10.4177F, -48.6166F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(1.8134F, 2.8059F, -14.5811F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(1.8134F, 2.8059F, -14.5811F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(2.9678F, 10.4177F, -48.6166F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9167F, AnimationHelper.createRotationalVector(2.9678F, 10.4177F, -48.6166F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 12.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.0F, 12.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(0.0F, -40.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 40.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(0.0F, 40.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(0.0F, -42.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9167F, AnimationHelper.createRotationalVector(0.0F, -35.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("layer", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.875F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(-42.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightHand", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(9.9627F, -0.8672F, -0.0755F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(9.9627F, -0.8672F, -0.0755F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.875F, AnimationHelper.createRotationalVector(-24.9192F, 2.1062F, -0.4683F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(9.9627F, -0.8672F, -0.0755F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(9.9627F, -0.8672F, -0.0755F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(-24.9192F, 2.1062F, -0.4683F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(9.9627F, -0.8672F, -0.0755F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(9.9627F, -0.8672F, -0.0755F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5833F, AnimationHelper.createRotationalVector(-24.9192F, 2.1062F, -0.4683F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9167F, AnimationHelper.createRotationalVector(-24.9192F, 2.1062F, -0.4683F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final Animation shieldSlap = Animation.Builder.create(3.0F).looping()
		.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 30.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 30.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5833F, AnimationHelper.createRotationalVector(0.0F, -40.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, -40.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHand", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(20.0F, 42.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(20.0F, 42.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5833F, AnimationHelper.createRotationalVector(24.5636F, -71.2235F, 21.7187F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(24.5636F, -71.2235F, 21.7187F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHand", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createTranslationalVector(10.0F, -5.0F, 8.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createTranslationalVector(10.0F, -5.0F, 8.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5833F, AnimationHelper.createTranslationalVector(2.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createTranslationalVector(2.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHand1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 47.5F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 47.5F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHandItem", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(-87.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-87.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, -30.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, -30.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5833F, AnimationHelper.createRotationalVector(0.0F, 40.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 40.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("layer", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(1.5417F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.625F, AnimationHelper.createRotationalVector(-60.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5417F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightHand", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(-39.5686F, 6.4087F, 7.6926F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-39.5686F, 6.4087F, 7.6926F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final Animation earthshaker = Animation.Builder.create(2.5F).looping()
		.addBoneAnimation("a", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, -15.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(-30.8676F, -12.9526F, 7.6308F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-30.8676F, -12.9526F, 7.6308F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightLeg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(67.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(67.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(72.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightLeg1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(-70.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.625F, AnimationHelper.createRotationalVector(-30.0897F, -7.0041F, -7.2346F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(-30.8096F, -7.5459F, -6.8187F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-35.8096F, -7.5459F, -6.8187F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftLeg1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(21.6273F, -0.0315F, -5.0986F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(21.6273F, -0.0315F, -5.0986F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHand", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(8.9001F, -54.341F, -1.0979F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(18.6994F, -83.438F, -12.5234F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(18.6994F, -83.438F, -12.5234F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(-5.0F, 7.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("layer", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(-35.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-35.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightHand", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(65.5448F, -23.5765F, -16.8662F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(60.6065F, 53.0597F, -6.8515F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(60.6065F, 53.0597F, -6.8515F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightHand1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(3.88F, -22.446F, 38.3451F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(3.88F, -22.45F, 38.35F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(3.88F, -22.45F, 38.35F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final Animation levelTwo = Animation.Builder.create(1.0F).looping()
		.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leftHand", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(5.7325F, -37.0757F, 0.2476F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightHand", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(155.0832F, -2.1113F, -9.5335F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(155.0832F, -2.1113F, -9.5335F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightHand", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightHand1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(102.5954F, 3.7067F, 5.0239F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(102.5954F, 3.7067F, 5.0239F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("rightHand1", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 4.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 4.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();
	public static final Animation summon = Animation.Builder.create(9.0417F).looping()
			.addBoneAnimation("a", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(1.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createTranslationalVector(0.0F, -1.25F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightLeg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createRotationalVector(-45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftLeg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createRotationalVector(30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftLeg1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(-12.148F, -34.3927F, 6.9326F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.5F, AnimationHelper.createRotationalVector(-7.5F, 50.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.9167F, AnimationHelper.createRotationalVector(-7.5F, 50.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftHand", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(165.0635F, -102.6205F, -81.0325F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.7083F, AnimationHelper.createRotationalVector(165.0629F, -102.6203F, -81.032F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(4.8727F, 8.5933F, 52.8118F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(-61.6992F, 11.1472F, 11.952F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(-22.2295F, -20.4906F, 114.0214F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.3333F, AnimationHelper.createRotationalVector(98.6497F, -12.4063F, 122.4874F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createRotationalVector(58.17F, 7.49F, 125.32F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftHand", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(3.75F, -2.75F, 8.75F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createTranslationalVector(7.25F, -7.25F, 8.75F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createTranslationalVector(7.25F, -3.97F, 8.75F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.8333F, AnimationHelper.createTranslationalVector(7.25F, -2.75F, 8.75F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createTranslationalVector(-1.25F, -0.5F, 2.25F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.3333F, AnimationHelper.createTranslationalVector(4.75F, -6.0F, 12.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createTranslationalVector(-0.25F, -6.0F, 5.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftHand1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 50.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 50.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(95.0F, 0.0F, 50.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 50.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.5F, AnimationHelper.createRotationalVector(37.9088F, 62.8077F, 91.6865F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 50.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftHand1", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(1.8333F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftHandItem", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(5.5417F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.7917F, AnimationHelper.createRotationalVector(90.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createRotationalVector(90.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftHandItem", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.5417F, AnimationHelper.createScalingVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.7917F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("waist", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 37.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(-17.5F, 47.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createRotationalVector(-12.56F, 20.88F, 8.55F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-57.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.8333F, AnimationHelper.createRotationalVector(-57.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.25F, AnimationHelper.createRotationalVector(-10.0F, -55.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createRotationalVector(-10.0F, -55.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightHand", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-27.5F, 0.0F, -117.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(15.0F, 0.0F, -117.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, -117.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, -117.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.625F, AnimationHelper.createRotationalVector(32.3586F, -20.7893F, 12.6383F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.3333F, AnimationHelper.createRotationalVector(-65.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightHand", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(5.75F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(5.75F, 0.0F, -8.25F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(5.75F, 0.0F, -7.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createTranslationalVector(5.75F, 0.0F, -7.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.625F, AnimationHelper.createTranslationalVector(1.75F, -6.0F, -1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.3333F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -8.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightHand1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -25.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -25.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 45.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightHand1", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(5.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(6.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(7.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(9.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();
}