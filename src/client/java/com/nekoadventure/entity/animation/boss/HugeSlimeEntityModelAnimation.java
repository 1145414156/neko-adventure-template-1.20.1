package com.nekoadventure.entity.animation.boss;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class HugeSlimeEntityModelAnimation {
	public static final Animation trample = Animation.Builder.create(3.0F).looping()
			.addBoneAnimation("inner", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.125F, AnimationHelper.createTranslationalVector(0.0F, 7.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 7.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.1667F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("inner", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 0.6F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.125F, AnimationHelper.createScalingVector(0.6F, 1.5F, 0.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createScalingVector(0.8F, 1.5F, 0.8F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.1667F, AnimationHelper.createScalingVector(1.0F, 0.7F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("outer", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.125F, AnimationHelper.createTranslationalVector(0.0F, 7.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 7.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.1667F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("outer", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 0.6F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.125F, AnimationHelper.createScalingVector(0.6F, 1.5F, 0.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createScalingVector(0.8F, 1.5F, 0.8F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.1667F, AnimationHelper.createScalingVector(1.0F, 0.7F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("eyes", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.125F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 2.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 2.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("eyes", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 0.7F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.125F, AnimationHelper.createScalingVector(0.8F, 0.9F, 0.8F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createScalingVector(0.8F, 0.9F, 0.8F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final Animation summon = Animation.Builder.create(2.0F)
			.addBoneAnimation("inner", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 4.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(0.0F, -7.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("inner", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1.0F, 1.3F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 0.4F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("outer", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 4.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(0.0F, -7.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("outer", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1.0F, 1.3F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 0.4F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final Animation bullet = Animation.Builder.create(5.0F).looping()
			.addBoneAnimation("inner", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1.2F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.4583F, AnimationHelper.createScalingVector(1.2F, 1.3F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5833F, AnimationHelper.createScalingVector(1.1F, 0.7F, 1.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.5F, AnimationHelper.createScalingVector(1.2F, 0.7F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createScalingVector(1.3F, 0.65F, 1.3F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.7083F, AnimationHelper.createScalingVector(1.0F, 1.2F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("outer", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1.2F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.4583F, AnimationHelper.createScalingVector(1.2F, 1.3F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5833F, AnimationHelper.createScalingVector(1.1F, 0.7F, 1.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.5F, AnimationHelper.createScalingVector(1.2F, 0.7F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.5F, AnimationHelper.createScalingVector(1.3F, 0.65F, 1.3F), Transformation.Interpolations.LINEAR),
					new Keyframe(4.7083F, AnimationHelper.createScalingVector(1.0F, 1.2F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("eyes", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("eyes", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createScalingVector(1.0F, 0.5F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createScalingVector(1.0F, 0.5F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("mouth", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5417F, AnimationHelper.createTranslationalVector(4.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createTranslationalVector(4.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("mouth", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5417F, AnimationHelper.createScalingVector(3.4F, 3.2F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(5.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final Animation dash = Animation.Builder.create(3.0F)
			.addBoneAnimation("inner", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("inner", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("outer", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("outer", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("eyes", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(1.4F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(-0.9F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.4F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.4F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createTranslationalVector(0.4F, 2.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.4F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("eyes", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createScalingVector(1.1F, -0.2F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(3.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("eyes1", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(1.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(-1.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final Animation summoning = Animation.Builder.create(2.0F).looping()
			.addBoneAnimation("inner", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createScalingVector(0.1667F, 0.0833F, 0.1667F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createScalingVector(0.3333F, 0.0667F, 0.3333F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createScalingVector(0.5833F, 0.2917F, 0.5833F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createScalingVector(0.6667F, 0.2334F, 0.6667F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createScalingVector(0.75F, 0.3001F, 0.75F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createScalingVector(0.8333F, 0.2667F, 0.8333F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1.0F, 0.5F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5417F, AnimationHelper.createScalingVector(1.0F, 0.5417F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 0.3834F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7083F, AnimationHelper.createScalingVector(1.0F, 0.7083F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createScalingVector(1.0F, 0.6333F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0417F, AnimationHelper.createScalingVector(1.0F, 0.7937F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.2917F, AnimationHelper.createScalingVector(1.0F, 0.7166F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createScalingVector(1.0F, 0.9F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("outer", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createScalingVector(0.1667F, 0.0833F, 0.1667F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createScalingVector(0.3333F, 0.0667F, 0.3333F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createScalingVector(0.5833F, 0.2917F, 0.5833F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createScalingVector(0.6667F, 0.2334F, 0.6667F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createScalingVector(0.75F, 0.3001F, 0.75F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createScalingVector(0.8333F, 0.2667F, 0.8333F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1.0F, 0.5F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5417F, AnimationHelper.createScalingVector(1.0F, 0.5417F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 0.3834F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7083F, AnimationHelper.createScalingVector(1.0F, 0.7083F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createScalingVector(1.0F, 0.6333F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0417F, AnimationHelper.createScalingVector(1.0F, 0.7937F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.2917F, AnimationHelper.createScalingVector(1.0F, 0.7166F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createScalingVector(1.0F, 0.9F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(2.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.build();
}